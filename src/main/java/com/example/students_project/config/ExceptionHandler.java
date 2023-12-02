package com.example.students_project.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler {

//    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        // Xatoliklar ro'yxatini olish
//        List<String> errors = ex.getBindingResult().getAllErrors()
//                .stream()
//                .map(error -> error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        // Foydalanuvchiga xatolik xabarlarini yuborish
//        return ResponseEntity.badRequest().body(String.join(", ", errors));
//    }
}
