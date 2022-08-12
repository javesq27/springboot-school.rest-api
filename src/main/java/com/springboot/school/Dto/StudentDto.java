package com.springboot.school.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class StudentDto {

    private Long Id;

    @NotEmpty
    @Size(min = 2, message = "Student name should have at least 2 characters")
    private String name;

    @NotEmpty
    @Size(min = 2, message = "Student lastname should have at least 2 characters")
    private String lastname;

    @NotEmpty(message = "Student grade should not be empty")
    @Size(min = 2, max = 2, message = "Student grade should have one number and one capital letter")
    private String grade;
    private List<NoteDto> notes;
}
