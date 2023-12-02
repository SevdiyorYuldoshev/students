package com.example.students_project.service.mapper;

import com.example.students_project.dto.ImageDto;
import com.example.students_project.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper extends CommonMapper<ImageDto, Image> {
}
