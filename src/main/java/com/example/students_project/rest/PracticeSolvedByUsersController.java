package com.example.students_project.rest;

import com.example.students_project.dto.PracticeSolvedByUsersDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.service.PracticeSolvedByUsersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/practice-solved-by-users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class PracticeSolvedByUsersController {
    private final PracticeSolvedByUsersService practiceSolvedByUsersService;
    @PostMapping
    public ResponseDto<PracticeSolvedByUsersDto> addPracticeSolvedByUsers(@Valid @RequestBody PracticeSolvedByUsersDto practiceSolvedByUsersDto){
        return practiceSolvedByUsersService.addPracticeSolvedByUsers(practiceSolvedByUsersDto);
    }

    @PatchMapping
    public ResponseDto<PracticeSolvedByUsersDto> updatePracticeSolvedByUsers(@RequestBody PracticeSolvedByUsersDto practiceSolvedByUsersDto){
        return practiceSolvedByUsersService.updatePracticeSolvedByUsers(practiceSolvedByUsersDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<PracticeSolvedByUsersDto> getPracticeSolvedByUsersById(@PathVariable Integer id){
        return practiceSolvedByUsersService.getPracticeSolvedByUsersById(id);
    }

    @GetMapping("/all-solved-practice")
    public ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getAllPracticeSolvedByUsers(@RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size){
        return practiceSolvedByUsersService.getAllPracticeSolvedByUsers(page, size);
    }

    @DeleteMapping
    public ResponseDto<PracticeSolvedByUsersDto> deletePracticeSolvedByUsersByID(@RequestParam Integer id){
        return practiceSolvedByUsersService.deletePracticeSolvedByUsersByID(id);
    }
}
