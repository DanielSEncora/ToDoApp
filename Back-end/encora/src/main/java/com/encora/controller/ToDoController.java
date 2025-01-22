package com.encora.controller;

import com.encora.exception.InvalidInputException;
import com.encora.exception.ResourceNotFoundException;
import com.encora.model.ToDo;
import com.encora.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The ToDoController class is a REST controller that handles HTTP requests related to ToDo operations.
 * It provides endpoints to create, read, update, and delete ToDo items, as well as to filter ToDo items
 * based on certain criteria.
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoService toDoService;

    /**
     * Constructs a ToDoController with the specified ToDoService.
     *
     * @param toDoService the service used to perform ToDo operations
     */
    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    /**
     * Retrieves all ToDo items.
     *
     * @return a ResponseEntity containing the list of all ToDo items
     */
    @GetMapping
    public ResponseEntity<List<ToDo>> getAllToDos() {
        List<ToDo> toDoList = toDoService.getAllToDos();
        return ResponseEntity.ok(toDoList);
    }

    /**
     * Retrieves a ToDo item by its ID.
     *
     * @param id the ID of the ToDo item to retrieve
     * @return a ResponseEntity containing the ToDo item
     * @throws ResourceNotFoundException if the ToDo item with the specified ID is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDo(@PathVariable int id) {
        ToDo toDo = toDoService.getToDoByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("ToDo not found with id " + id));
        return ResponseEntity.ok(toDo);
    }

    /**
     * Filters ToDo items based on text, priority, and state.
     *
     * @param text     the text to filter by
     * @param priority the priority to filter by
     * @param state    the state to filter by
     * @return a ResponseEntity containing the list of filtered ToDo items
     */
    @GetMapping("/filter/{text}-{priority}-{state}")
    public ResponseEntity<List<ToDo>> filterToDos(@PathVariable String text, @PathVariable String priority, @PathVariable String state) {
        List<ToDo> filteredToDos = toDoService.filterToDos(text, priority, state);
        return ResponseEntity.ok(filteredToDos);
    }

    /**
     * Creates a new ToDo item.
     *
     * @param toDo the ToDo item to create
     * @return a ResponseEntity containing the created ToDo item
     * @throws InvalidInputException if the text of the ToDo item is null or empty
     */
    @PostMapping
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        if (toDo.getText() == null || toDo.getText().isEmpty()) {
            throw new InvalidInputException("Text is required");
        }
        ToDo createdToDo = toDoService.createToDo(toDo);
        return ResponseEntity.status(201).body(createdToDo);
    }

    /**
     * Marks a ToDo item as done by its ID.
     *
     * @param id the ID of the ToDo item to mark as done
     * @return a ResponseEntity containing the updated ToDo item
     * @throws ResourceNotFoundException if the ToDo item with the specified ID is not found
     */
    @PostMapping("/{id}/done")
    public ResponseEntity<ToDo> markToDoAsDone(@PathVariable int id) {
        ToDo updatedToDo = toDoService.markToDoAsDone(id)
                .orElseThrow(() -> new ResourceNotFoundException("ToDo not found with id " + id));
        return ResponseEntity.ok(updatedToDo);
    }

    /**
     * Updates a ToDo item by its ID.
     *
     * @param toDo the updated ToDo item
     * @param id   the ID of the ToDo item to update
     * @return a ResponseEntity containing the updated ToDo item
     * @throws InvalidInputException if the text of the ToDo item is null or empty
     * @throws ResourceNotFoundException if the ToDo item with the specified ID is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@RequestBody ToDo toDo, @PathVariable int id) {
        if (toDo.getText() == null || toDo.getText().isEmpty()) {
            throw new InvalidInputException("Text is required");
        }
        ToDo updatedToDo = toDoService.updateToDo(id, toDo)
                .orElseThrow(() -> new ResourceNotFoundException("ToDo not found with id " + id));
        return ResponseEntity.ok(updatedToDo);
    }

    /**
     * Marks a ToDo item as undone by its ID.
     *
     * @param id the ID of the ToDo item to mark as undone
     * @return a ResponseEntity containing the updated ToDo item
     * @throws ResourceNotFoundException if the ToDo item with the specified ID is not found
     */
    @PutMapping("/{id}/undone")
    public ResponseEntity<ToDo> markToDoAsUndone(@PathVariable int id) {
        ToDo updatedToDo = toDoService.markToDoAsUndone(id)
                .orElseThrow(() -> new ResourceNotFoundException("ToDo not found with id " + id));
        return ResponseEntity.ok(updatedToDo);
    }

    /**
     * Deletes a ToDo item by its ID.
     *
     * @param id the ID of the ToDo item to delete
     * @return a ResponseEntity with a status of no content
     * @throws ResourceNotFoundException if the ToDo item with the specified ID is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable int id) {
        toDoService.deleteToDoByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("ToDo not found with id " + id));
        return ResponseEntity.noContent().build();
    }
}