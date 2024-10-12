package com.encora.model;


import jakarta.persistence.*;


import java.util.ArrayList;

@Entity
@Table(name= "ToDos")
public class ToDo {
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private static int nextId = 1;


    @Column(name = "To Do")
    private String text;
    @Column(name = "Priority")
    private Priority priority;
    @Column(name = "Due Date")
    private String dueDate;
    @Column(name = "Creation Date")
    private String creationDate;
    @Column(name = "Done Date")
    private String doneDate;
    @Column(name = "Done/Undone")
    private boolean done;



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

    public ToDo(String dueDate, Priority priority, String text, int id, boolean done) {
        this.id = nextId++;
        this.dueDate = dueDate;
        this.priority = priority;
        this.text = text;
        this.done = done;
    }

    public void printArray(ArrayList<ToDo> todos){
        for (ToDo todo: todos){
            System.out.println("Todo:");
            System.out.println(todo.getId());
            System.out.println(todo.getText());
            System.out.println(todo.getPriority());
            System.out.println(todo.getDueDate());
            System.out.println(todo.getCreationDate());
            System.out.println(todo.getDoneDate());
        }
    }

    public ArrayList<ToDo> filter(ArrayList<ToDo> toDos, String text){
        ArrayList<ToDo> sortedArray = new ArrayList<>();
        CharSequence charSeq = text.toLowerCase();

        for (ToDo todo: toDos){
            if (todo.text.toLowerCase().contains(charSeq)){
                sortedArray.add(todo);
                System.out.println(todo.text);
            }
        }

        return sortedArray;
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
