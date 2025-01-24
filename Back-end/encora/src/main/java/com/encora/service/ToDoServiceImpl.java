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
                todo17, todo18, todo19, todo21, todo22, todo23, todo24, todo25, todo26, todo27));
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
            existing.setText(toDo.getText());
            existing.setPriority(toDo.getPriority());
            // Format the due date to the expected format
            String formattedDueDate = simpleDateFormat.format(toDo.getDueDate());
            existing.setDueDate(formattedDueDate);
            existing.setDone(toDo.isDone());
            existing.setDoneDate(toDo.getDoneDate());
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
        toDoOptional.ifPresent(toDo -> {
            toDoList.remove(toDo);
            System.out.println("Deleted ToDo with ID: " + id);
        });
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