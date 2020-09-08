package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "note")
    private String note;


    @Column(name = "completed")
    private Boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Note{" + id + ": " + note + ", " + completed + '}';
    }
}
