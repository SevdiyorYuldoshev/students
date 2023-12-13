package com.example.students_project.config;

import com.example.students_project.dto.ErrorDto;
import com.example.students_project.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.LazyInitializationException;
import org.hibernate.PersistentObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.security.SignatureException;
import java.sql.SQLException;
import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.List;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.NOT_FOUND;
import static com.example.students_project.service.validation.StatusMessage.VALIDATION_ERROR;
import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ExceptionHandlerResources {

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(OK_CODE)
                        .message(VALIDATION_ERROR)
                        .errors(e.getBindingResult().getFieldErrors().stream()
                                .map(f-> new ErrorDto(f.getField(),f.getDefaultMessage()))
                                .collect(toList()))
                        .build());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseDto> handleAuthenticationException(
            SignatureException ex, HttpServletRequest request, HttpServletResponse response) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, UNEXPECTED_ERROR_CODE);
    }

    // Kerak bo'lsa, boshqa undirishlar uchun metodlar qo'shing

    private ResponseEntity<ResponseDto> buildErrorResponse(Exception ex, HttpStatus status, int statusCode) {
        ResponseDto responseDto = ResponseDto.builder()
                .code(statusCode)
                .message(ex.getMessage() + (ex.getCause() != null ? ex.getCause().getMessage() : ""))
                .build();
        return new ResponseEntity<>(responseDto, status);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResponseDto<Void>> fileNotFoundException(FileNotFoundException e){
        List<ErrorDto> errorDtos = new ArrayList<>();
        errorDtos.add(ErrorDto.builder()
                        .field("url")
                        .error(e.getMessage())
                .build());
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND)
                        .errors(errorDtos)
                        .build());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDto<Void>> fileNullPointerException(NullPointerException e){
        List<ErrorDto> errorDtos = new ArrayList<>();
        errorDtos.add(ErrorDto.builder()
                        .field("")
                        .error(e.getMessage())
                .build());
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND)
                        .errors(errorDtos)
                        .build());
    }

    @ExceptionHandler(PersistentObjectException.class)
    public ResponseEntity<ResponseDto<Void>> filePersistentObjectException(PersistentObjectException e){
        List<ErrorDto> errorDtos = new ArrayList<>();
        errorDtos.add(ErrorDto.builder()
                        .field("")
                        .error(e.getMessage())
                .build());
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND)
                        .errors(errorDtos)
                        .build());
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseDto<Void>> fileSQLException(SQLException e){
        List<ErrorDto> errorDtos = new ArrayList<>();
        errorDtos.add(ErrorDto.builder()
                        .field("")
                        .error(e.getMessage())
                .build());
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND)
                        .errors(errorDtos)
                        .build());
    }
    @ExceptionHandler(LazyInitializationException.class)
    public ResponseEntity<ResponseDto<Void>> fileLazyInitializationException(LazyInitializationException e){
        List<ErrorDto> errorDtos = new ArrayList<>();
        errorDtos.add(ErrorDto.builder()
                        .field("")
                        .error(e.getMessage())
                .build());
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND)
                        .errors(errorDtos)
                        .build());
    }
}
