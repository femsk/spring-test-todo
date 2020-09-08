package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import com.example.demo.service.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class NoteController {

    private NoteService noteService;

    @Autowired
    public void setNoteService(NoteService noteService) { this.noteService = noteService; }

    @GetMapping(value = "/api/all")
    @ResponseBody
    public HashMap allNotes(
            @RequestParam int page, int rowPerPage,
            @RequestParam String filter) {
        List<Note> notes = noteService.allNotes(page, rowPerPage, filter);
        int notesCount = noteService.notesCount(filter);
        int pagesCount = (notesCount + (rowPerPage - 1))/rowPerPage;
        HashMap result = new HashMap();
        result.put("result", notes);
        result.put("pagesCount", pagesCount);
        return result;
    }

    @PutMapping(value = "/api/add")
    public void add(@RequestBody Note note) {
        noteService.add(note);
        return;
    }

    @PostMapping(value = "/api/edit")
    public void edit(@RequestBody Note note) {
        noteService.edit(note);
        return;
    }

    @RequestMapping(value = "/api/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        noteService.delete(id);
    }
}
