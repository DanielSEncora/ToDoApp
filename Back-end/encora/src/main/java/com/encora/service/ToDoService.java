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
        ToDo todo6 = new ToDo("Fix bug", ToDo.Priority.LOW, "25/10/2024 10:20:30", "undone");
        ToDo todo7 = new ToDo("Write documentation", ToDo.Priority.HIGH, "26/10/2024 12:15:45", "done");
        ToDo todo8 = new ToDo("Deploy feature", ToDo.Priority.MEDIUM, "27/10/2024 14:00:00", "undone");
        ToDo todo9 = new ToDo("Run tests", ToDo.Priority.LOW, "28/10/2024 16:25:10", "done");
        ToDo todo11 = new ToDo("Review code", ToDo.Priority.HIGH, "29/10/2024 18:10:20", "undone");
        ToDo todo12 = new ToDo("Refactor code", ToDo.Priority.LOW, "30/10/2024 08:30:55", "undone");
        ToDo todo13 = new ToDo("Plan sprint", ToDo.Priority.MEDIUM, "31/10/2024 09:45:15", "done");
        ToDo todo14 = new ToDo("Sync repository", ToDo.Priority.HIGH, "01/11/2024 11:10:40", "undone");
        ToDo todo15 = new ToDo("Update dependencies", ToDo.Priority.LOW, "02/11/2024 13:50:25", "done");
        ToDo todo16 = new ToDo("Write unit tests", ToDo.Priority.MEDIUM, "03/11/2024 15:15:30", "undone");
        ToDo todo17 = new ToDo("Create backup", ToDo.Priority.HIGH, "04/11/2024 17:20:50", "done");
        ToDo todo18 = new ToDo("Optimize query", ToDo.Priority.LOW, "05/11/2024 19:40:10", "undone");
        ToDo todo19 = new ToDo("Design mockups", ToDo.Priority.MEDIUM, "06/11/2024 21:10:00", "done");
        ToDo todo21 = new ToDo("Conduct meeting", ToDo.Priority.HIGH, "07/11/2024 07:50:45", "undone");
        ToDo todo22 = new ToDo("Setup server", ToDo.Priority.LOW, "08/11/2024 09:30:20", "done");
        ToDo todo23 = new ToDo("Analyze logs", ToDo.Priority.MEDIUM, "09/11/2024 10:15:35", "undone");
        ToDo todo24 = new ToDo("Monitor performance", ToDo.Priority.HIGH, "10/11/2024 12:05:15", "done");
        ToDo todo25 = new ToDo("Draft proposal", ToDo.Priority.LOW, "11/11/2024 13:45:50", "undone");
        ToDo todo26 = new ToDo("Update README", ToDo.Priority.MEDIUM, "12/11/2024 15:30:00", "done");
        ToDo todo27 = new ToDo("Schedule review", ToDo.Priority.HIGH, "13/11/2024 16:50:25", "undone");

        toDoList.addAll(Arrays.asList(todo6, todo7, todo8, todo9, todo11, todo12, todo13, todo14, todo15, todo16,
                todo17, todo18, todo19, todo21, todo22, todo23, todo24, todo25, todo26, todo27,todo1,todo2,todo3,todo4,todo5,todo10,todo20,todo30,todo50,todo40));

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
