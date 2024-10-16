package com.encora.model;


import jakarta.persistence.*;


import java.util.ArrayList;

@Entity
public class ToDo {
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private static int nextId = 0;

    public String text;
    private Priority priority;
    private String dueDate;
    private String creationDate;
    private String doneDate;
    private boolean done;

    public ToDo(){
        this.id = nextId++;
    }

    public ToDo(String text, Priority priority) {
        this.id = nextId++;
        this.text = text;
        this.priority = priority;
    }

    public ToDo(String text, Priority priority, String dueDate) {
        this.id = nextId++;
        this.text = text;
        this.priority = priority;
    }

    public ToDo(String text, Priority priority, String dueDate, String creationDate, String doneDate, Boolean done) {
        this.id = nextId++;
        this.dueDate = dueDate;
        this.priority = priority;
        this.text = text;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }
}
