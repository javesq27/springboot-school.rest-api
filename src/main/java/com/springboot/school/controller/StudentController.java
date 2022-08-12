package com.springboot.school.controller;

import com.springboot.school.Dto.StudentDto;
import com.springboot.school.service.IStudent;
import com.springboot.school.utils.AppConstants;
import com.springboot.school.utils.StudentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private IStudent iStudent;

    public StudentController(IStudent iStudent) {
        this.iStudent = iStudent;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StudentDto> create(@Valid @RequestBody StudentDto studentDto) {
        return new ResponseEntity<>(iStudent.createStudent(studentDto), HttpStatus.CREATED);
    }

    @GetMapping
    public StudentResponse getAllStudents(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy
    ) {
        return iStudent.getAllStudents(pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(iStudent.getStudentById(id));
    }

    @GetMapping("/gr/{grade}")
    public List<StudentDto> getStudentByGrade(@PathVariable(name = "grade") String grade) {
        return iStudent.getStudentsByGrade(grade);
    }

    @GetMapping("/english")
    public List<StudentDto> getAllStudentsWithEnglishMoreThanSeven() {
        return iStudent.getAllStudentsWithEnglishMoreThanSeven();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@Valid @RequestBody StudentDto studentDto, @PathVariable(name = "id") Long id) {
        StudentDto studentResponse = iStudent.updateStudent(studentDto, id);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable(name = "id") Long id) {
        iStudent.deleteStudentById(id);
        return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
    }
}
