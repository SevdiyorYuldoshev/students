package com.example.students_project.service.impl;

import com.example.students_project.dto.LoginDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.UsersDto;
import com.example.students_project.entity.Users;
import com.example.students_project.repository.UserRepository;
import com.example.students_project.rest.UsersController;
import com.example.students_project.security.JwtService;
import com.example.students_project.service.UsersService;
import com.example.students_project.service.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersMapper usersMapper;
    private final UserRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @Override
    public ResponseDto<UsersDto> addUser(UsersDto usersDto) {
        usersDto.setRole("USER");
        usersDto.setCreatedAt(LocalDateTime.now());
        usersDto.setPassword(encoder.encode(usersDto.getPassword()));
        if(usersRepository.usernameExists(usersDto.getUsername())){
            return ResponseDto.<UsersDto>builder()
                    .data(usersDto)
                    .message("Username already exists")
                    .code(UNEXPECTED_ERROR_CODE)
                    .build();
        }



        Users users = usersMapper.toEntity(usersDto);

        users.setCreatedAt(LocalDateTime.now());
        users.setActivity(0);
        usersRepository.save(users);
        return ResponseDto.<UsersDto>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(usersMapper.toDto(users))
                .build();
    }

    @Override
    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findById(usersDto.getId());

        if (userOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(usersDto)
                    .build();
        }
        Users user = userOptional.get();
        if(user.getActivity() == 0){
            return ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(usersDto)
                    .build();
        }
        if (usersDto.getFirstname() != null){
            user.setFirstname(usersDto.getFirstname());
        }
        if (usersDto.getUsername() != null){
            if(usersRepository.usernameExists(usersDto.getUsername())){
                return ResponseDto.<UsersDto>builder()
                        .data(usersDto)
                        .message("Username already exists")
                        .code(UNEXPECTED_ERROR_CODE)
                        .build();
            } else user.setUsername(usersDto.getUsername());
        }
        if (usersDto.getPhoneNumber() != null){
            user.setPhoneNumber(usersDto.getPhoneNumber());
        }
        if (usersDto.getDescription() != null){
            user.setDescription(usersDto.getDescription());
        }
        if (usersDto.getEmail() != null){
            user.setEmail(usersDto.getEmail());
        }
        if (usersDto.getLastname() != null){
            user.setLastname(usersDto.getLastname());
        }

        try {
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .data(usersMapper.toDto(user))
                    .success(true)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(usersMapper.toDto(user))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> getUserById(Integer id) {
        return usersRepository.findByIdAndActivityEquals(id, 0)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .success(true)
                        .message(OK)
                        .code(OK_CODE)
                        .data(usersMapper.toDto(u))
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND)
                        .build());
    }

    @Override
    public ResponseDto<Page<EntityModel<UsersDto>>> getAllUsers(Integer page, Integer size) {
        Long count = usersRepository.count();
        return ResponseDto.<Page<EntityModel<UsersDto>>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(usersRepository
                        .findAll(PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                        .map(u -> {
                            EntityModel<UsersDto> entityModel = EntityModel.of(usersMapper.toDto(u));
                            try {
                                entityModel.add(linkTo(UsersController.class
                                        .getDeclaredMethod("getUserById", Integer.class))
                                        .withSelfRel()
                                        .expand(u.getId()));
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                            return entityModel;
                        }))
                .build();
    }

    @Override
    public ResponseDto<UsersDto> deleteUser(Integer id) {
        Optional<Users> usersOptional = usersRepository.findById(id);

        Users users = usersOptional.orElse(null);

        if(usersOptional.isEmpty() || users.getActivity() == -2){
            return ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        users.setActivity(-2);
        usersRepository.save(users);
        return ResponseDto.<UsersDto>builder()
                .message("User with ID " + id + " is deleted")
                .code(OK_CODE)
                .data(usersMapper.toDto(users))
                .build();
    }

    @Override
    public ResponseDto<String> loginUser(LoginDto loginDto) {
        UsersDto users = loadUserByUsername(loginDto.getUsername());
        if (!encoder.matches(loginDto.getPassword(),users.getPassword())){
            return ResponseDto.<String>builder()
                    .message("Password is not correct "+users.getPassword())
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        return ResponseDto.<String>builder()
                .success(true)
                .message(OK)
                .data(jwtService.generateToken(users))
                .build();
    }

    public UsersDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findFirstByUsername(username);
        if (users.isEmpty()) throw new UsernameNotFoundException("User with username " + username + " is not found");
        return usersMapper.toDto(users.get());
    }

    @Override
    public ResponseDto<UsersDto> updatePassword(String username, String password, String newPassword) {
        Optional<Users> usersOptional = usersRepository.findFirstByUsername(username);
        if (usersOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        Users firstByUsername = usersOptional.get();
        if (firstByUsername.getActivity() != 0){
            return ResponseDto.<UsersDto>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        if(!firstByUsername.getPassword().equals(password)){
            return ResponseDto.<UsersDto>builder()
                    .code(UNEXPECTED_ERROR_CODE)
                    .message("The old password is not valid")
                    .build();
        }

        firstByUsername.setPassword(newPassword);
        return ResponseDto.<UsersDto>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(usersMapper.toDto(usersRepository.save(firstByUsername)))
                .build();
    }

    @Override
    public ResponseDto<UsersDto> createAdmin(Integer id) {
        Optional<Users> byId = usersRepository.findById(id);
        if(byId.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        Users users = byId.get();
        users.setRole("ADMIN");
        usersRepository.save(users);
        return ResponseDto.<UsersDto>builder()
                .message(OK)
                .success(true)
                .code(OK_CODE)
                .data(usersMapper.toDto(users))
                .build();
    }
}
