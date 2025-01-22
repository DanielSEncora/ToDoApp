package com.encora.service;

import com.encora.model.ToDo;
import com.encora.service.ToDoService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The ToDoServiceImpl class provides the implementation of the ToDoService interface.
 * It manages a list of ToDo items, allowing for creating, retrieving, updating, deleting,
 * and filtering ToDo items, as well as marking them as done or undone.
 */
@Service
public class ToDoServiceImpl implements ToDoService {
    private static final DateTimeFormatter DONE_DATE_FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private List<ToDo> toDoList;

    /**
     * Constructs a ToDoServiceImpl with an initial list of sample ToDo items.
     */
    public ToDoServiceImpl() {
        toDoList = new ArrayList<>();
        // Initialize with some sample todos
        ToDo todo1 = new ToDo("CutLowUndone", ToDo.Priority.LOW, "21/10/2024 13:40:20", "Undone");
        ToDo todo2 = new ToDo("CutHighUndone", ToDo.Priority.HIGH, "20/10/2024 15:50:25", "Undone");
        toDoList.addAll(Arrays.asList(todo1, todo2));
    }

    /**
     * Retrieves all ToDo items.
     *
     * @return a list of all ToDo items
     */
    @Override
    public List<ToDo> getAllToDos() {
        return toDoList;
    }

    /**
     * Retrieves a ToDo item by its ID.
     *
     * @param id the ID of the ToDo item to retrieve
     * @return an Optional containing the ToDo item if found, or an empty Optional if not found
     */
    @Override
    public Optional<ToDo> getToDoByID(int id) {
        return toDoList.stream()
                .filter(toDo -> toDo.getId() == id)
                .findFirst();
    }

    /**
     * Creates a new ToDo item.
     *
     * @param toDo the ToDo item to create
     * @return the created ToDo item
     */
    @Override
    public ToDo createToDo(ToDo toDo) {
        toDoList.add(toDo);
        return toDo;
    }

    /**
     * Marks a ToDo item as done by its ID.
     *
     * @param id the ID of the ToDo item to mark as done
     * @return an Optional containing the updated ToDo item if found, or an empty Optional if not found
     */
    @Override
    public Optional<ToDo> markToDoAsDone(int id) {
        Optional<ToDo> toDoOptional = getToDoByID(id);
        toDoOptional.ifPresent(toDo -> {
            toDo.setDoneDate(OffsetDateTime.now().format(DONE_DATE_FORMAT));
            toDo.setDone(true);
        });
        return toDoOptional;
    }

    /**
     * Updates a ToDo item by its ID.
     *
     * @param id the ID of the ToDo item to update
     * @param toDo the updated ToDo item
     * @return an Optional containing the updated ToDo item if found, or an empty Optional if not found
     */
    @Override
    public Optional<ToDo> updateToDo(int id, ToDo toDo) {
        Optional<ToDo> existingToDo = getToDoByID(id);
        existingToDo.ifPresent(existing -> {
            toDo.setId(id);
            toDo.setCreationDate(existing.getCreationDate());
            toDoList.remove(existing);
            toDoList.add(toDo);
        });
        return existingToDo;
    }

    /**
     * Marks a ToDo item as undone by its ID.
     *
     * @param id the ID of the ToDo item to mark as undone
     * @return an Optional containing the updated ToDo item if found, or an empty Optional if not found
     */
    @Override
    public Optional<ToDo> markToDoAsUndone(int id) {
        Optional<ToDo> toDoOptional = getToDoByID(id);
        toDoOptional.ifPresent(toDo -> {
            toDo.setDone(false);
            toDo.setDoneDate("");
        });
        return toDoOptional;
    }

    /**
     * Deletes a ToDo item by its ID.
     *
     * @param id the ID of the ToDo item to delete
     * @return an Optional containing the deleted ToDo item if found, or an empty Optional if not found
     */
    @Override
    public Optional<ToDo> deleteToDoByID(int id) {
        Optional<ToDo> toDoOptional = getToDoByID(id);
        toDoOptional.ifPresent(toDoList::remove);
        return toDoOptional;
    }

    /**
     * Filters ToDo items based on text, priority, and state.
     *
     * @param text the text to filter by
     * @param priority the priority to filter by
     * @param state the state to filter by
     * @return a list of filtered ToDo items
     */
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