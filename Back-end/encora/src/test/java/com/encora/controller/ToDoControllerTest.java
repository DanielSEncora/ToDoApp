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

class ToDoControllerTest {

    @Mock
    private ToDoService toDoService;

    @InjectMocks
    private ToDoController toDoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllToDos() {
        List<ToDo> toDoList = Arrays.asList(new ToDo(), new ToDo());
        when(toDoService.getAllToDos()).thenReturn(toDoList);

        ResponseEntity<List<ToDo>> response = toDoController.getAllToDos();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetToDo() {
        ToDo toDo = new ToDo();
        toDo.setId(1);
        when(toDoService.getToDoByID(1)).thenReturn(Optional.of(toDo));

        ResponseEntity<ToDo> response = toDoController.getToDo(1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testCreateToDo() {
        ToDo toDo = new ToDo("Test", ToDo.Priority.MEDIUM, "22/01/2025 17:37:33", "Undone");
        when(toDoService.createToDo(toDo)).thenReturn(toDo);

        ResponseEntity<ToDo> response = toDoController.createToDo(toDo);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test", response.getBody().getText());
    }

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

    @Test
    void testDeleteToDo() {
        ToDo toDo = new ToDo();
        toDo.setId(1);
        when(toDoService.deleteToDoByID(1)).thenReturn(Optional.of(toDo));

        ResponseEntity<Void> response = toDoController.deleteToDo(1);
        assertEquals(204, response.getStatusCodeValue());
    }
}