package com.example.students_project.rest;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.PracticeDto;
import com.example.students_project.service.PracticeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/practice")
@SecurityRequirement(name = "Authorization")
public class PracticeController {
    private final PracticeService practiceService;
    @PostMapping
    public ResponseDto<PracticeDto> addPractice(@Valid @RequestBody PracticeDto practiceDto){
        return practiceService.addPractice(practiceDto);
    }

    @PatchMapping
    public ResponseDto<PracticeDto> updatePractice(@RequestBody PracticeDto practiceDto){
        return practiceService.updatePractice(practiceDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<PracticeDto> getPracticeById(@PathVariable Integer id){
        return practiceService.getPracticeById(id);
    }

    @GetMapping("/all-practice")
    public ResponseDto<Page<EntityModel<PracticeDto>>> getAllPractice(@RequestParam(defaultValue = "0") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size){
        return practiceService.getAllPractice(page, size);
    }

    @DeleteMapping
    public ResponseDto<PracticeDto> deletePracticeByID(@RequestParam Integer id){
        return practiceService.deletePracticeById(id);
    }
    @GetMapping("/get-by-subject-id/{id}")
    public ResponseDto<Page<EntityModel<PracticeDto>>> getBySubjectId(@PathVariable Integer id,
                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size) {
        return practiceService.getBySubjectId(id, page, size);
    }
}
