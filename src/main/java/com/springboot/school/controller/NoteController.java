package com.springboot.school.controller;

import com.springboot.school.Dto.NoteDto;
import com.springboot.school.service.INote;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    private INote iNote;

    public NoteController(INote iNote) {
        this.iNote = iNote;
    }

    @PostMapping("/students/{studentId}/notes")
    public ResponseEntity<NoteDto> createNote(@PathVariable(value = "studentId") Long studentId, @Valid @RequestBody NoteDto noteDto) {

        return new ResponseEntity<>(iNote.createNote(studentId, noteDto), HttpStatus.CREATED);
    }

    @GetMapping("/students/{studentId}/notes")
    public List<NoteDto> getNotesByStudentsId(@PathVariable(value = "studentId") Long studentId) {

        return iNote.getNotesByStudentId(studentId);
    }

    @GetMapping("/students/math_notes_period")
    public List<NoteDto> getMathNoteMoreThanSevenAndPeriodOne(){
        return iNote.findAllWithMathNoteMoreThanSevenAndPeriodOne();
    }

    @GetMapping("/students/{studentId}/notes/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "id") Long noteId){

        NoteDto noteDto = iNote.getNoteById(studentId, noteId);
        return new ResponseEntity<>(noteDto, HttpStatus.OK);
    }

    @PutMapping("/students/{studentId}/notes/{id}")
    public ResponseEntity<NoteDto> updatedNote(@PathVariable(value = "studentId") Long studentId,
                                               @PathVariable(value = "id") Long noteId,
                                               @Valid @RequestBody NoteDto noteDto) {
        NoteDto updatedNote = iNote.updateNote(studentId, noteId, noteDto);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentId}/notes/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "id") Long noteId) {

        iNote.deleteNote(studentId, noteId);
        return new ResponseEntity<>("Note deleted successfully", HttpStatus.OK);
    }
}
