package com.springboot.school.service;

import com.springboot.school.Dto.StudentDto;
import com.springboot.school.utils.StudentResponse;

import java.util.List;

public interface IStudent {

    StudentDto createStudent(StudentDto studentDto);
    StudentResponse getAllStudents(int pageNo, int pageSize, String sortBy);
    StudentDto getStudentById(Long id);
    List<StudentDto> getStudentsByGrade(String grade);
    StudentDto updateStudent(StudentDto studentDto, Long id);
    void deleteStudentById(Long id);
    List<StudentDto> getAllStudentsWithEnglishMoreThanSeven();
}
