package com.encora.model;


import jakarta.persistence.*;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

    String pattern = "dd/MM/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    public String text;
    private Priority priority;
    private String dueDate;
    private String creationDate;
    private String doneDate;
    private boolean done;

    public ToDo(){
        this.id = nextId++;
        this.done = false;
        this.creationDate = simpleDateFormat.format(new Date());
        this.doneDate = "";
    }

    public ToDo(String text, Priority priority) {
        this.id = nextId++;
        this.text = text;
        this.priority = priority;
        this.done = false;
        this.creationDate = simpleDateFormat.format(new Date());
        this.doneDate = "";
    }
    public ToDo(String text, Priority priority, String dueDate, String done) {
        this.id = nextId++;
        this.text = text;
        this.priority = priority;
        if (done.equalsIgnoreCase("Done")) {
            this.done = true;
        }else{
            this.done = false;
        }
        this.dueDate = dueDate;
        this.creationDate = simpleDateFormat.format(new Date());
        this.doneDate = "";
    }

    public ToDo(String text, Priority priority, String dueDate) {
        this.id = nextId++;
        this.text = text;
        this.priority = priority;
        this.dueDate = dueDate;
        this.done = false;
        this.creationDate = simpleDateFormat.format(new Date());
        this.doneDate = "";
    }

    public ToDo(String text, Priority priority, String dueDate, String creationDate, String doneDate, Boolean done) {
        this.id = nextId++;
        this.dueDate = dueDate;
        this.priority = priority;
        this.text = text;
        this.done = done;
        this.creationDate = creationDate;
        this.doneDate = doneDate;
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
