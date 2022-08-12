package com.springboot.school.service;

import com.springboot.school.Dto.NoteDto;

import java.util.List;

public interface INote {
    NoteDto createNote(Long studentId, NoteDto noteDto);
    List<NoteDto> getNotesByStudentId(Long studentId);
    NoteDto getNoteById(Long studentId, Long noteId);
    NoteDto updateNote(Long studentId, Long noteId, NoteDto noteDtoRequest);
    void deleteNote(Long studentId, Long noteId);
    List<NoteDto> findAllWithMathNoteMoreThanSevenAndPeriodOne();
}
