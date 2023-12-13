package com.example.students_project.service.mapper;

import com.example.students_project.dto.ImageDto;
import com.example.students_project.entity.Image;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-13T15:09:39+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageDto toDto(Image e) {
        if ( e == null ) {
            return null;
        }

        ImageDto.ImageDtoBuilder imageDto = ImageDto.builder();

        imageDto.id( e.getId() );
        imageDto.url( e.getUrl() );
        imageDto.createdAt( e.getCreatedAt() );

        return imageDto.build();
    }

    @Override
    public Image toEntity(ImageDto d) {
        if ( d == null ) {
            return null;
        }

        Image image = new Image();

        image.setId( d.getId() );
        image.setUrl( d.getUrl() );
        image.setCreatedAt( d.getCreatedAt() );

        return image;
    }
}
