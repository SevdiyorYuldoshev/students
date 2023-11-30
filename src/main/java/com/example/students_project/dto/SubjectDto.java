package com.example.students_project.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {
    private Integer id;
    private String name;
    private Integer creator_id;
    private String lecture_document_url;
    private Boolean activity;
}
