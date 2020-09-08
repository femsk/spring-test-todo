package com.example.demo.service;

import com.example.demo.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> allNotes(int page, int rowPerPage, String filter);
    int notesCount(String filter);
    void add(Note note);
    void delete(int id);
    void edit(Note note);
    Note getById(String id);
}
