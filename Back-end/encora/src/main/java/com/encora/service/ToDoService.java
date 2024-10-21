package com.encora.service;

import com.encora.model.ToDo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ToDoService {
    private static final DateTimeFormatter DONE_DATE_FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public List<ToDo> toDoList;


    private Date parseDate(String dateString) {
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing error
        }
    }

    public ToDoService(){
        toDoList = new ArrayList<>();
/*
        ToDo todo1 = new ToDo("CutLowUndone", ToDo.Priority.LOW,"21/10/2024 13:40:20","Undone");
        ToDo todo2 = new ToDo("CutHighDone", ToDo.Priority.HIGH, "20/10/2024 15:50:25", "done");
        ToDo todo3 = new ToDo("CutMediumUndone", ToDo.Priority.MEDIUM, "22/10/2024 20:20:35","undone");
        ToDo todo4 = new ToDo("CutLowDone", ToDo.Priority.LOW, "02/11/2024 23:50:55", "Done");
        ToDo todo5 = new ToDo("CutHighUndone", ToDo.Priority.HIGH, "24/10/2024 05:00:10", "undone");
        ToDo todo10 = new ToDo("PasteLowUndone", ToDo.Priority.LOW,"21/10/2024 06:00:13","Undone");
        ToDo todo20 = new ToDo("PasteHighDone", ToDo.Priority.HIGH, "20/10/2024 07:30:16", "done");
        ToDo todo30 = new ToDo("PasteMediumUndone", ToDo.Priority.MEDIUM, "22/10/2024 08:40:20","undone");
        ToDo todo40 = new ToDo("PasteLowDone", ToDo.Priority.LOW, "02/11/2024 08:45:03", "Done");
        ToDo todo50 = new ToDo("PasteHighUndone", ToDo.Priority.HIGH, "24/10/2024 09:00:01", "undone");

        toDoList.addAll(Arrays.asList(todo1,todo2,todo3,todo4,todo5,todo10,todo20,todo30,todo50,todo40));*/
    }

    public ToDo getToDoByID(int id){
        for (ToDo toDo: toDoList){
            if (id == toDo.getId()){
                return toDo;
            }
        }
        return null;
    }

    public ToDo postToDo(@RequestBody ToDo toDo){
                toDoList.add(toDo);
        return toDo;
    }

    public ToDo postToDo(@PathVariable int id){
        for (ToDo toDo: toDoList){
            if (id == toDo.getId()){
                System.out.println("Correct due date " + OffsetDateTime.now().format(DONE_DATE_FORMAT));
                toDo.setDoneDate(OffsetDateTime.now().format(DONE_DATE_FORMAT));
                toDo.setDone(true);
                return toDo;
            }
        }
        return null;
    }

    public ToDo putToDo(@RequestBody ToDo toDo, int id) {
        ToDo existingToDo = getToDoByID(id);

        if (existingToDo != null) {
            // Set the ID of the new ToDo
            toDo.setId(id);

            // Preserve the creation date from the existing ToDo
            toDo.setCreationDate(existingToDo.getCreationDate());

            // Remove the old ToDo and add the updated one
            toDoList.remove(existingToDo);
            toDoList.add(toDo);

            return toDo;
        }
        System.out.println("Null");
        return null;
    }


    public ToDo putToDo(@PathVariable int id){
        for (ToDo toDo: toDoList){
            if (id == toDo.getId()){
                toDo.setDone(false);
                toDo.setDoneDate("");
                return toDo;
            }
        }
        System.out.println("Null");
        return null;
    }

    public ToDo deleteToDoByID(int id){
        for (ToDo toDo: toDoList){
            if (id == toDo.getId()){
                toDoList.remove(toDo);
                return toDo;
            }
        }
        return null;
    }

    public void printList(List<ToDo> todos){
        for (ToDo todo: todos){
            System.out.println("Todo:");
            System.out.print(todo.getId());
            System.out.print(todo.getText());
            System.out.print(todo.getPriority());
            System.out.print(todo.getDueDate());
            System.out.print(todo.getCreationDate());
            System.out.print(todo.getDoneDate());
        }
    }

    public List<ToDo> filter(List<ToDo> toDos, String text, String priority, String state){
        List<ToDo> sortedArray = new ArrayList<>();
        CharSequence charSeq = text.toLowerCase();

        // Filters by name only
        if (!text.isBlank() && priority.isBlank() && state.isBlank()) {
            for (ToDo todo : toDos) {
                if (todo.text.toLowerCase().contains(charSeq)) {
                    sortedArray.add(todo);
                    System.out.println(todo.text);
                }
            }
            return sortedArray;
        }

        // Filters by priority only
        if (text.isBlank() && !priority.isBlank() && state.isBlank()) {
            for (ToDo todo : toDos) {
                if (todo.getPriority().name().equalsIgnoreCase(priority)) {
                    sortedArray.add(todo);
                    System.out.println(todo.text);
                }
            }
            return sortedArray;
        }

        // Filters by state only
        if (text.isBlank() && priority.isBlank() && !state.isBlank()) {
            if (state.equalsIgnoreCase("done")){
                for (ToDo todo : toDos) {
                    if (todo.isDone()) {
                        sortedArray.add(todo);
                        System.out.println(todo.text);
                    }
                }
                return sortedArray;
            }else {
                for (ToDo todo : toDos) {
                    if (!todo.isDone()) {
                        sortedArray.add(todo);
                        System.out.println(todo.text);
                    }
                }
            }
            return sortedArray;
        }

        // Filters by name and priority
        if (!text.isBlank() && !priority.isBlank() && state.isBlank()) {
            for (ToDo todo : toDos) {
                if (todo.text.toLowerCase().contains(charSeq) && todo.getPriority().name().equalsIgnoreCase(priority)) {
                    sortedArray.add(todo);
                    System.out.println(todo.text);
                }
            }
            return sortedArray;
        }

        // Filters by name and state
        if (!text.isBlank() && priority.isBlank() && !state.isBlank()) {
            if (state.equalsIgnoreCase("done")) {
                for (ToDo todo : toDos) {
                    if (todo.isDone() &&  todo.text.toLowerCase().contains(charSeq)) {
                        sortedArray.add(todo);
                        System.out.println(todo.text);
                    }
                }
                return sortedArray;
            }else {
                for (ToDo todo : toDos) {
                    if (!todo.isDone() && todo.text.toLowerCase().contains(charSeq)) {
                        sortedArray.add(todo);
                        System.out.println(todo.text);
                    }
                }
            }
            return sortedArray;
        }

        // Filters by state and priority
        if (text.isBlank() && !priority.isBlank() && !state.isBlank()) {
            if (state.equalsIgnoreCase("done")) {
                for (ToDo todo : toDos) {
                    if (todo.isDone() && todo.text.toLowerCase().contains(charSeq) && todo.getPriority().name().equalsIgnoreCase(priority)) {
                        sortedArray.add(todo);
                        System.out.println(todo.text);
                    }
                }
                return sortedArray;
            }else {
                for (ToDo todo : toDos) {
                    if (!todo.isDone() && todo.text.toLowerCase().contains(charSeq) &&todo.getPriority().name().equalsIgnoreCase(priority)) {
                        sortedArray.add(todo);
                        System.out.println(todo.text);
                    }
                }
            }
            return sortedArray;
        }

        // Filters by name, state and priority
        if (!text.isBlank() && !priority.isBlank() && !state.isBlank()) {
            if (state.equalsIgnoreCase("done")) {
                for (ToDo todo : toDos) {
                    if (todo.isDone() &&  todo.getPriority().name().equalsIgnoreCase(priority)) {
                        sortedArray.add(todo);
                        System.out.println(todo.text);
                    }
                }
                return sortedArray;
            }else {
                for (ToDo todo : toDos) {
                    if (!todo.isDone() && todo.getPriority().name().equalsIgnoreCase(priority)) {
                        sortedArray.add(todo);
                        System.out.println(todo.text);
                    }
                }
            }
            return sortedArray;
        }


        printList(toDos);
        return toDos;
    }
}
