package com.encora.service;

import com.encora.model.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ToDoServiceImplTest class provides unit tests for the ToDoServiceImpl class.
 * It verifies the functionality of creating, retrieving, updating, deleting,
 * and filtering ToDo items, as well as marking them as done or undone.
 */
class ToDoServiceImplTest {

    private ToDoService toDoService;

    /**
     * Sets up the ToDoServiceImpl instance before each test.
     * This method is executed before each test case to ensure a fresh state.
     */
    @BeforeEach
    void setUp() {
        toDoService = new ToDoServiceImpl();
    }

    /**
     * Tests the retrieval of all ToDo items.
     * Verifies that the list of ToDo items is not null and contains the expected number of items.
     */
    @Test
    void testGetAllToDos() {
        List<ToDo> todos = toDoService.getAllToDos();
        assertNotNull(todos, "The list of ToDo items should not be null");
        assertEquals(2, todos.size(), "There should be 2 ToDo items initially");
    }

    /**
     * Tests the retrieval of a ToDo item by its ID.
     * Verifies that the ToDo item with the specified ID is present and has the expected text.
     */
    @Test
    void testGetToDoByID() {
        int id = toDoService.getAllToDos().get(0).getId(); // Dynamically get ID
        Optional<ToDo> toDo = toDoService.getToDoByID(id);
        assertTrue(toDo.isPresent(), "The ToDo item should be present");
        assertEquals("CutLowUndone", toDo.get().getText(), "The ToDo item text should match");
    }

    /**
     * Tests the creation of a new ToDo item.
     * Verifies that the new ToDo item is created successfully and the list size increases as expected.
     */
    @Test
    void testCreateToDo() {
        ToDo newToDo = new ToDo("TestToDo", ToDo.Priority.MEDIUM, "22/01/2025 17:37:33", "Undone");
        ToDo createdToDo = toDoService.createToDo(newToDo);
        assertNotNull(createdToDo, "The created ToDo item should not be null");
        assertEquals("TestToDo", createdToDo.getText(), "The created ToDo item text should match");
        assertEquals(3, toDoService.getAllToDos().size(), "The list size should be 3 after adding a new ToDo item");
    }

    /**
     * Tests the update of an existing ToDo item.
     * Verifies that the ToDo item is updated successfully with new values.
     */
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
        assertTrue(result.isPresent(), "The updated ToDo item should be present");
        Optional<ToDo> updated = toDoService.getToDoByID(id);
        assertTrue(updated.isPresent(), "The ToDo item should be present after update");
        assertEquals("UpdatedText", updated.get().getText(), "The updated ToDo item text should match");
        assertEquals(ToDo.Priority.HIGH, updated.get().getPriority(), "The updated ToDo item priority should match");
    }

    /**
     * Tests marking a ToDo item as done.
     * Verifies that the ToDo item is marked as done and the done date is set.
     */
    @Test
    void testMarkToDoAsDone() {
        int id = toDoService.getAllToDos().get(0).getId(); // Dynamically get ID
        Optional<ToDo> toDo = toDoService.markToDoAsDone(id);
        assertTrue(toDo.isPresent(), "The ToDo item should be present");
        assertTrue(toDo.get().isDone(), "The ToDo item should be marked as done");
        assertNotNull(toDo.get().getDoneDate(), "The done date should be set");
    }

    /**
     * Tests marking a ToDo item as undone.
     * Verifies that the ToDo item is marked as undone and the done date is cleared.
     */
    @Test
    void testMarkToDoAsUndone() {
        int id = toDoService.getAllToDos().get(0).getId(); // Dynamically get ID
        toDoService.markToDoAsDone(id); // First mark as done
        Optional<ToDo> toDo = toDoService.markToDoAsUndone(id); // Then mark as undone
        assertTrue(toDo.isPresent(), "The ToDo item should be present");
        assertFalse(toDo.get().isDone(), "The ToDo item should be marked as undone");
        assertEquals("", toDo.get().getDoneDate(), "The done date should be cleared");
    }

    /**
     * Tests the deletion of a ToDo item by its ID.
     * Verifies that the ToDo item is deleted successfully and the list size decreases as expected.
     */
    @Test
    void testDeleteToDoByID() {
        int id = toDoService.getAllToDos().get(0).getId(); // Dynamically get ID
        Optional<ToDo> deletedToDo = toDoService.deleteToDoByID(id);
        assertTrue(deletedToDo.isPresent(), "The deleted ToDo item should be present");
        assertEquals("CutLowUndone", deletedToDo.get().getText(), "The deleted ToDo item text should match");
        assertEquals(1, toDoService.getAllToDos().size(), "The list size should be 1 after deleting a ToDo item");
    }

    /**
     * Tests filtering ToDo items based on text, priority, and state.
     * Verifies that the filtered list contains the expected ToDo items.
     */
    @Test
    void testFilterToDos() {
        List<ToDo> filteredToDos = toDoService.filterToDos("cut", "LOW", "Undone");
        assertNotNull(filteredToDos, "The filtered list should not be null");
        assertEquals(1, filteredToDos.size(), "The filtered list should contain 1 ToDo item");
        assertEquals("CutLowUndone", filteredToDos.get(0).getText(), "The filtered ToDo item text should match");

        filteredToDos = toDoService.filterToDos("", "HIGH", "Undone");
        assertEquals(1, filteredToDos.size(), "The filtered list should contain 1 ToDo item");
        assertEquals("CutHighUndone", filteredToDos.get(0).getText(), "The filtered ToDo item text should match");
    }
}