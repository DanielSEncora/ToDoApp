package com.encora.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@code ToDo} class represents a task with various attributes such as text,
 * priority, due date, creation date, and completion status.
 */
public class ToDo {

    /**
     * Enum representing the priority of the task.
     */
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

    /**
     * Default constructor. Initializes the task with default values.
     */
    public ToDo() {
        this.id = nextId++;
        this.done = false;
        this.creationDate = new Date();
        this.doneDate = "";
    }

    /**
     * Helper method to parse a date string into a {@code Date} object.
     *
     * @param dateString the date string to parse
     * @return the parsed {@code Date} object, or {@code null} if parsing fails
     */
    private Date parseDate(String dateString) {
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing error
        }
    }

    /**
     * Constructor to initialize a task with text and priority.
     *
     * @param text the text description of the task
     * @param priority the priority of the task
     */
    public ToDo(String text, Priority priority) {
        this();
        this.text = text;
        this.priority = priority;
    }

    /**
     * Constructor to initialize a task with text, priority, due date, and done status.
     *
     * @param text the text description of the task
     * @param priority the priority of the task
     * @param dueDate the due date of the task as a string
     * @param done the done status of the task as a string ("Done" or not)
     */
    public ToDo(String text, Priority priority, String dueDate, String done) {
        this();
        this.text = text;
        this.priority = priority;
        this.done = "Done".equalsIgnoreCase(done);
        this.dueDate = parseDate(dueDate);
    }

    /**
     * Constructor to initialize a task with text, priority, and due date.
     *
     * @param text the text description of the task
     * @param priority the priority of the task
     * @param dueDate the due date of the task as a string
     */
    public ToDo(String text, Priority priority, String dueDate) {
        this();
        this.text = text;
        this.priority = priority;
        this.dueDate = parseDate(dueDate);
    }

    /**
     * Full constructor to initialize a task with all attributes.
     *
     * @param text the text description of the task
     * @param priority the priority of the task
     * @param dueDate the due date of the task as a string
     * @param creationDate the creation date of the task as a string
     * @param doneDate the done date of the task as a string
     * @param done the done status of the task
     */
    public ToDo(String text, Priority priority, String dueDate, String creationDate, String doneDate, Boolean done) {
        this();
        this.text = text;
        this.priority = priority;
        this.dueDate = parseDate(dueDate);
        this.creationDate = parseDate(creationDate);
        this.doneDate = doneDate;
        this.done = done;
    }

    /**
     * Gets the ID of the task.
     *
     * @return the ID of the task
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the task.
     *
     * @param id the new ID of the task
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the text description of the task.
     *
     * @return the text description of the task
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text description of the task.
     *
     * @param text the new text description of the task
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the priority of the task.
     *
     * @return the priority of the task
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority the new priority of the task
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Gets the due date of the task.
     *
     * @return the due date of the task
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the task.
     *
     * @param dueDateString the new due date of the task as a string
     */
    public void setDueDate(String dueDateString) {
        this.dueDate = parseDate(dueDateString);
    }

    /**
     * Gets the creation date of the task.
     *
     * @return the creation date of the task
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the task.
     *
     * @param creationDate the new creation date of the task
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Checks if the task is done.
     *
     * @return {@code true} if the task is done, {@code false} otherwise
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Sets the done status of the task.
     *
     * @param done the new done status of the task
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Gets the done date of the task.
     *
     * @return the done date of the task as a string
     */
    public String getDoneDate() {
        return doneDate;
    }

    /**
     * Sets the done date of the task.
     *
     * @param doneDate the new done date of the task
     */
    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }
}