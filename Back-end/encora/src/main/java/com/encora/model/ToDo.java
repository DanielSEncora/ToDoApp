package com.encora.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDo {
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    private int id;
    private static int nextId = 0;

    private String text;
    private Priority priority;
    private Date dueDate;
    private Date creationDate;
    private String doneDate;
    private boolean done;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public ToDo() {
        this.id = nextId++;
        this.done = false;
        this.creationDate = new Date();
        this.doneDate = "";
    }

    private Date parseDate(String dateString) {
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing error
        }
    }

    public ToDo(String text, Priority priority) {
        this();
        this.text = text;
        this.priority = priority;
    }

    public ToDo(String text, Priority priority, String dueDate, String done) {
        this();
        this.text = text;
        this.priority = priority;
        if (done.equalsIgnoreCase("Done")) {
            this.done = true;
        } else {
            this.done = false;
        }
        this.dueDate = parseDate(dueDate);
    }

    public ToDo(String text, Priority priority, String dueDate) {
        this();
        this.text = text;
        this.priority = priority;
        this.dueDate = parseDate(dueDate);
    }

    public ToDo(String text, Priority priority, String dueDate, String creationDate, String doneDate, Boolean done) {
        this();
        this.dueDate = parseDate(dueDate);
        this.priority = priority;
        this.text = text;
        this.done = done;
        this.creationDate = parseDate(creationDate);
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDateString) {
        this.dueDate = parseDate(dueDateString);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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