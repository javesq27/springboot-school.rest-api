package com.springboot.school.service;

import com.springboot.school.Dto.StudentDto;
import com.springboot.school.entity.Student;
import com.springboot.school.exception.ResourceNotFoundException;
import com.springboot.school.repository.StudentRepository;
import com.springboot.school.utils.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService implements IStudent{

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = mapToStudent(studentDto);
        Student newStudent = studentRepository.save(student);
        StudentDto studentResponse = mapToDto(newStudent);

        return studentResponse;

    }

    @Override
    public StudentResponse getAllStudents(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Student> students = studentRepository.findAll(pageable);
        List<Student> listOfStudents = students.getContent();
        List<StudentDto> content = listOfStudents.stream().map(student -> mapToDto(student)).collect(Collectors.toList());

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setContent(content);
        studentResponse.setPageNo(students.getNumber());
        studentResponse.setPageSize(students.getSize());
        studentResponse.setTotalElements(students.getTotalElements());
        studentResponse.setTotalPages(students.getTotalPages());
        studentResponse.setLast(students.isLast());

        return studentResponse;

    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student", "id", id));
        return mapToDto(student);
    }

    @Override
    public List<StudentDto> getStudentsByGrade(String grade) {
        List <Student> students = studentRepository.findStudentsByGrade(grade);
        return students.stream().map(student -> mapToDto(student)).collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto, Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student", "id", id));
        student.setName(studentDto.getName());
        student.setLastname(studentDto.getLastname());
        student.setGrade(studentDto.getGrade());

        Student updatedStudent = studentRepository.save(student);
        return mapToDto(updatedStudent);
    }

    @Override
    public void deleteStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student", "id", id));
        studentRepository.delete(student);

    }

    @Override
    public List<StudentDto> getAllStudentsWithEnglishMoreThanSeven() {
        List<Student>students = studentRepository.findAll();
        Stream<Student> studentEnglish = students.stream().filter(student -> student.getNotes().get(0).getEnglish() >=7);
        return studentEnglish.map(se -> mapToDto(se)).collect(Collectors.toList());
    }

    public static StudentDto mapToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setLastname(student.getLastname());
        studentDto.setGrade(student.getGrade());
        studentDto.setNotes(student.getNotes().stream().map(NoteService::mapToDto).collect(Collectors.toList()));
        return studentDto;
    }

    public static Student mapToStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setLastname(studentDto.getLastname());
        student.setGrade(studentDto.getGrade());
        return student;
    }
}
