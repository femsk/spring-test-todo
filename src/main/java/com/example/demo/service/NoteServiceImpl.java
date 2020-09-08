package com.example.demo.service;

import com.example.demo.dao.NoteDAO;
import com.example.demo.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteDAO noteDAO;

    @Autowired
    public void setNoteDAO(NoteDAO noteDAO) { this.noteDAO = noteDAO; }

    @Override
    @Transactional
    public List<Note> allNotes(int page, int rowPerPage, String filter) {
        return noteDAO.allNotes(page, rowPerPage, filter);
    }

    @Override
    @Transactional
    public int notesCount(String filter) {
        return noteDAO.notesCount(filter);
    }

    @Override
    @Transactional
    public void add(Note note) {
        noteDAO.add(note);
    }

    @Override
    @Transactional
    public void delete(int id) {
        noteDAO.delete(id);
    }

    @Override
    @Transactional
    public void edit(Note note) {
        noteDAO.edit(note);
    }

    @Override
    @Transactional
    public Note getById(String id) {
        return noteDAO.getById(id);
    }
}
