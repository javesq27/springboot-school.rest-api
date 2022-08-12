package com.springboot.school.Dto;

import com.springboot.school.entity.Student;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class NoteDto {

    private Long id;

    @Min(1) @Max(4)
    private int period;

    @DecimalMax("10.0") @DecimalMin("1.0")
    private double math;

    @DecimalMax("10.0") @DecimalMin("1.0")
    private double language;

    @DecimalMax("10.0") @DecimalMin("1.0")
    private double socialSciences;

    @DecimalMax("10.0") @DecimalMin("1.0")
    private double biology;

    @DecimalMax("10.0") @DecimalMin("1.0")
    private double english;
}
