package com.encora.service;

import com.encora.model.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ToDoServiceImplTest {

    private ToDoService toDoService;

    @BeforeEach
    void setUp() {
        toDoService = new ToDoServiceImpl();
    }

    @Test
    void testGetAllToDos() {
        List<ToDo> todos = toDoService.getAllToDos();
        assertNotNull(todos);
        assertEquals(2, todos.size());
    }

    @Test
    void testGetToDoByID() {
        int id = toDoService.getAllToDos().get(0).getId(); // Dynamically get ID
        Optional<ToDo> toDo = toDoService.getToDoByID(id);
        assertTrue(toDo.isPresent());
        assertEquals("CutLowUndone", toDo.get().getText());
    }

    @Test
    void testCreateToDo() {
        ToDo newToDo = new ToDo("TestToDo", ToDo.Priority.MEDIUM, "22/01/2025 17:37:33", "Undone");
        ToDo createdToDo = toDoService.createToDo(newToDo);
        assertNotNull(createdToDo);
        assertEquals("TestToDo", createdToDo.getText());
        assertEquals(3, toDoService.getAllToDos().size()); // Now there should be 3 items
    }

    @Test
    void testUpdateToDo() {
        int id = toDoService.getAllToDos().get(0).getId(); // Dynamically get ID

        // Define the expected date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Create a new Date object for the due date
        Date date = new Date();

        // Format the date to the expected format
        String formattedDate = dateFormat.format(date);

        // Create an updated ToDo object with the formatted date
        ToDo updatedToDo = new ToDo("UpdatedText", ToDo.Priority.HIGH, formattedDate, "Undone");

        Optional<ToDo> result = toDoService.updateToDo(id, updatedToDo);
        assertTrue(result.isPresent());
        Optional<ToDo> updated = toDoService.getToDoByID(id);
        assertTrue(updated.isPresent());
        assertEquals("UpdatedText", updated.get().getText());
        assertEquals(ToDo.Priority.HIGH, updated.get().getPriority());
    }

    @Test
    void testMarkToDoAsDone() {
        int id = toDoService.getAllToDos().get(0).getId(); // Dynamically get ID
        Optional<ToDo> toDo = toDoService.markToDoAsDone(id);
        assertTrue(toDo.isPresent());
        assertTrue(toDo.get().isDone());
        assertNotNull(toDo.get().getDoneDate());
    }

    @Test
    void testMarkToDoAsUndone() {
        int id = toDoService.getAllToDos().get(0).getId(); // Dynamically get ID
        toDoService.markToDoAsDone(id); // First mark as done
        Optional<ToDo> toDo = toDoService.markToDoAsUndone(id); // Then mark as undone
        assertTrue(toDo.isPresent());
        assertFalse(toDo.get().isDone());
        assertEquals("", toDo.get().getDoneDate());
    }

    @Test
    void testDeleteToDoByID() {
        int id = toDoService.getAllToDos().get(0).getId(); // Dynamically get ID
        Optional<ToDo> deletedToDo = toDoService.deleteToDoByID(id);
        assertTrue(deletedToDo.isPresent());
        assertEquals("CutLowUndone", deletedToDo.get().getText());
        assertEquals(1, toDoService.getAllToDos().size()); // Now there should be 1 item left
    }

    @Test
    void testFilterToDos() {
        List<ToDo> filteredToDos = toDoService.filterToDos("cut", "LOW", "Undone");
        assertNotNull(filteredToDos);
        assertEquals(1, filteredToDos.size());
        assertEquals("CutLowUndone", filteredToDos.get(0).getText());

        filteredToDos = toDoService.filterToDos("", "HIGH", "Undone");
        assertEquals(1, filteredToDos.size());
        assertEquals("CutHighUndone", filteredToDos.get(0).getText());
    }
}