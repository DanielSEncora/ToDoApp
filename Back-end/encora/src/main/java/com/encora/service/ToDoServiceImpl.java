package com.encora.service;

import com.encora.model.ToDo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ToDoServiceImpl implements ToDoService {
    private static final DateTimeFormatter DONE_DATE_FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private List<ToDo> toDoList;

    public ToDoServiceImpl() {
        toDoList = new ArrayList<>();
        // Initialize with some sample todos
        ToDo todo1 = new ToDo("CutLowUndone", ToDo.Priority.LOW, "21/10/2024 13:40:20", "Undone");
        ToDo todo2 = new ToDo("CutHighUndone", ToDo.Priority.HIGH, "20/10/2024 15:50:25", "Undone");
        toDoList.addAll(Arrays.asList(todo1, todo2));
    }

    private Date parseDate(String dateString) {
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing error
        }
    }

    @Override
    public List<ToDo> getAllToDos() {
        return toDoList;
    }

    @Override
    public Optional<ToDo> getToDoByID(int id) {
        return toDoList.stream()
                .filter(toDo -> toDo.getId() == id)
                .findFirst();
    }

    @Override
    public ToDo createToDo(ToDo toDo) {
        toDoList.add(toDo);
        return toDo;
    }

    @Override
    public Optional<ToDo> markToDoAsDone(int id) {
        Optional<ToDo> toDoOptional = getToDoByID(id);
        toDoOptional.ifPresent(toDo -> {
            toDo.setDoneDate(OffsetDateTime.now().format(DONE_DATE_FORMAT));
            toDo.setDone(true);
        });
        return toDoOptional;
    }

    @Override
    public ToDo updateToDo(int id, ToDo toDo) {
        ToDo existingToDo = getToDoByID(id).orElse(null);

        if (existingToDo != null) {
            toDo.setId(id);
            toDo.setCreationDate(existingToDo.getCreationDate());
            toDoList.remove(existingToDo);
            toDoList.add(toDo);
            return toDo;
        }
        return null;
    }

    @Override
    public Optional<ToDo> markToDoAsUndone(int id) {
        Optional<ToDo> toDoOptional = getToDoByID(id);
        toDoOptional.ifPresent(toDo -> {
            toDo.setDone(false);
            toDo.setDoneDate("");
        });
        return toDoOptional;
    }

    @Override
    public boolean deleteToDoByID(int id) {
        Optional<ToDo> toDoOptional = getToDoByID(id);
        toDoOptional.ifPresent(toDoList::remove);
        return toDoOptional.isPresent();
    }

    @Override
    public List<ToDo> filterToDos(String text, String priority, String state) {
        List<ToDo> filteredToDos = new ArrayList<>();
        CharSequence charSeq = text.toLowerCase();

        for (ToDo toDo : toDoList) {
            boolean matchesText = text.isBlank() || toDo.getText().toLowerCase().contains(charSeq);
            boolean matchesPriority = priority.isBlank() || toDo.getPriority().name().equalsIgnoreCase(priority);
            boolean matchesState = state.isBlank() || (state.equalsIgnoreCase("done") ? toDo.isDone() : !toDo.isDone());

            if (matchesText && matchesPriority && matchesState) {
                filteredToDos.add(toDo);
            }
        }

        return filteredToDos;
    }
}