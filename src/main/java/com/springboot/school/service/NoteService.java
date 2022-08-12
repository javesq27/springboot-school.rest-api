package com.springboot.school.service;

import com.springboot.school.Dto.NoteDto;
import com.springboot.school.entity.Note;
import com.springboot.school.entity.Student;
import com.springboot.school.exception.ResourceNotFoundException;
import com.springboot.school.exception.SchoolApiException;
import com.springboot.school.repository.NoteRepository;
import com.springboot.school.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService implements INote {

    private NoteRepository noteRepository;
    private StudentRepository studentRepository;

    public NoteService(NoteRepository noteRepository, StudentRepository studentRepository) {
        this.noteRepository = noteRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public NoteDto createNote(Long studentId, NoteDto noteDto) {

        Note note = mapToNote(noteDto);
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student", "id", studentId));
        note.setStudent(student);
        Note newNote = noteRepository.save(note);
        return mapToDto(newNote);
    }

    @Override
    public List<NoteDto> getNotesByStudentId(Long studentId) {
        List<Note> notes = noteRepository.findByStudentId(studentId);
        return notes.stream().map(note -> mapToDto(note)).collect(Collectors.toList());
    }

    @Override
    public NoteDto getNoteById(Long studentId, Long noteId) {

        NoteDto noteDto = getStudentIdAndNoteId(studentId, noteId);
        return noteDto;
    }

    @Override
    public NoteDto updateNote(Long studentId, Long noteId, NoteDto noteDtoRequest) {

        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student", "id", studentId));
        Note note = noteRepository.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Note", "id", noteId));

        if (!note.getStudent().getId().equals(student.getId())) {
            throw new SchoolApiException(HttpStatus.BAD_REQUEST, "Note does not belong to this student");
        }

        note.setPeriod(noteDtoRequest.getPeriod());
        note.setMath(noteDtoRequest.getMath());
        note.setLanguage(noteDtoRequest.getLanguage());
        note.setSocialSciences(noteDtoRequest.getSocialSciences());
        note.setBiology(noteDtoRequest.getBiology());
        note.setEnglish(noteDtoRequest.getBiology());

        Note updatedNote = noteRepository.save(note);

        return mapToDto(updatedNote);

    }

    @Override
    public void deleteNote(Long studentId, Long noteId) {

        NoteDto noteDto = getStudentIdAndNoteId(studentId, noteId);
        Note note = mapToNote(noteDto);
        noteRepository.delete(note);
    }

    @Override
    public List<NoteDto> findAllWithMathNoteMoreThanSevenAndPeriodOne() {
        List<Note> notes = noteRepository.findAll();
        List<Note> filterNotes =  notes.stream().filter(note -> note.getMath()>= 7.0 && note.getPeriod()==1).collect(Collectors.toList());
        return filterNotes.stream().map(fn -> mapToDto(fn)).collect(Collectors.toList());

    }

    private NoteDto getStudentIdAndNoteId(Long studentId, Long noteId) {

        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student", "id", studentId));
        Note note = noteRepository.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Note", "id", noteId));

        if (!note.getStudent().getId().equals(student.getId())) {
            throw new SchoolApiException(HttpStatus.BAD_REQUEST, "Note does not belong to this student");
        }
        return mapToDto(note);

    }

    public static NoteDto mapToDto(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setPeriod(note.getPeriod());
        noteDto.setMath(note.getMath());
        noteDto.setLanguage(note.getLanguage());
        noteDto.setSocialSciences(note.getSocialSciences());
        noteDto.setBiology(note.getBiology());
        noteDto.setEnglish(note.getEnglish());

        return noteDto;
    }

    public static Note mapToNote(NoteDto noteDto) {
        Note note = new Note();
        note.setId(noteDto.getId());
        note.setPeriod(noteDto.getPeriod());
        note.setMath(noteDto.getMath());
        note.setLanguage(noteDto.getLanguage());
        note.setSocialSciences(noteDto.getSocialSciences());
        note.setBiology(noteDto.getBiology());
        note.setEnglish(noteDto.getEnglish());

        return note;

    }
}
