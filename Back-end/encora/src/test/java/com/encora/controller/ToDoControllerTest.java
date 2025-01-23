package com.encora.controller;

import com.encora.model.ToDo;
import com.encora.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The ToDoControllerTest class provides unit tests for the ToDoController class.
 * It verifies the functionality of creating, retrieving, updating, deleting,
 * and marking ToDo items as done or undone through the controller.
 */
class ToDoControllerTest {

    @Mock
    private ToDoService toDoService;

    @InjectMocks
    private ToDoController toDoController;

    /**
     * Sets up the mocks and initializes the ToDoController instance before each test.
     * This method is executed before each test case to ensure a fresh state.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the retrieval of all ToDo items through the controller.
     * Verifies that the response status is 200 OK and the list contains the expected number of items.
     */
    @Test
    void testGetAllToDos() {
        List<ToDo> toDoList = Arrays.asList(new ToDo(), new ToDo());
        when(toDoService.getAllToDos()).thenReturn(toDoList);

        ResponseEntity<List<ToDo>> response = toDoController.getAllToDos();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    /**
     * Tests the retrieval of a ToDo item by its ID through the controller.
     * Verifies that the response status is 200 OK and the ToDo item has the expected ID.
     */
    @Test
    void testGetToDo() {
        ToDo toDo = new ToDo();
        toDo.setId(1);
        when(toDoService.getToDoByID(1)).thenReturn(Optional.of(toDo));

        ResponseEntity<ToDo> response = toDoController.getToDo(1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getId());
    }

    /**
     * Tests the creation of a new ToDo item through the controller.
     * Verifies that the response status is 201 Created and the ToDo item has the expected text.
     */
    @Test
    void testCreateToDo() {
        ToDo toDo = new ToDo("Test", ToDo.Priority.MEDIUM, "22/01/2025 17:37:33", "Undone");
        when(toDoService.createToDo(toDo)).thenReturn(toDo);

        ResponseEntity<ToDo> response = toDoController.createToDo(toDo);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test", response.getBody().getText());
    }

    /**
     * Tests marking a ToDo item as done through the controller.
     * Verifies that the response status is 200 OK and the ToDo item is marked as done.
     */
    @Test
    void testMarkToDoAsDone() {
        ToDo toDo = new ToDo();
        toDo.setId(1);
        toDo.setDone(true);
        when(toDoService.markToDoAsDone(1)).thenReturn(Optional.of(toDo));

        ResponseEntity<ToDo> response = toDoController.markToDoAsDone(1);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isDone());
    }

    /**
     * Tests the update of an existing ToDo item through the controller.
     * Verifies that the response status is 200 OK and the ToDo item has the expected updated text.
     */
    @Test
    void testUpdateToDo() {
        ToDo toDo = new ToDo();
        toDo.setId(1);
        toDo.setText("Updated");
        when(toDoService.updateToDo(eq(1), any(ToDo.class))).thenReturn(Optional.of(toDo));

        ToDo updatedToDo = new ToDo("Updated", ToDo.Priority.HIGH, "22/01/2025 17:37:33", "Undone");
        ResponseEntity<ToDo> response = toDoController.updateToDo(updatedToDo, 1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated", response.getBody().getText());
    }

    /**
     * Tests marking a ToDo item as undone through the controller.
     * Verifies that the response status is 200 OK and the ToDo item is marked as undone.
     */
    @Test
    void testMarkToDoAsUndone() {
        ToDo toDo = new ToDo();
        toDo.setId(1);
        toDo.setDone(false);
        when(toDoService.markToDoAsUndone(1)).thenReturn(Optional.of(toDo));

        ResponseEntity<ToDo> response = toDoController.markToDoAsUndone(1);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isDone());
    }

    /**
     * Tests the deletion of a ToDo item by its ID through the controller.
     * Verifies that the response status is 204 No Content.
     */
    @Test
    void testDeleteToDo() {
        ToDo toDo = new ToDo();
        toDo.setId(1);
        when(toDoService.deleteToDoByID(1)).thenReturn(Optional.of(toDo));

        ResponseEntity<Void> response = toDoController.deleteToDo(1);
        assertEquals(204, response.getStatusCodeValue());
    }
}