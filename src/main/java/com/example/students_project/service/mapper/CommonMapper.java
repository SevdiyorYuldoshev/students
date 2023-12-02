package com.example.students_project.service.mapper;

public interface CommonMapper<D, E>{
    D toDto(E e);
    E toEntity(D d);
}