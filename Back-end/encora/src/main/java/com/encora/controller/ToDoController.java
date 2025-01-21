package com.encora.controller;

import com.encora.model.ToDo;
import com.encora.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public ResponseEntity<List<ToDo>> getAllToDos() {
        List<ToDo> toDoList = toDoService.getAllToDos();
        return ResponseEntity.ok(toDoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDo(@PathVariable int id) {
        ToDo toDo = toDoService.getToDoByID(id);
        if (toDo != null) {
            return ResponseEntity.ok(toDo);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/filter/{text}-{priority}-{state}")
    public ResponseEntity<List<ToDo>> filterToDos(@PathVariable String text, @PathVariable String priority, @PathVariable String state) {
        List<ToDo> filteredToDos = toDoService.filterToDos(text, priority, state);
        return ResponseEntity.ok(filteredToDos);
    }

    @PostMapping
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        ToDo createdToDo = toDoService.createToDo(toDo);
        return ResponseEntity.ok(createdToDo);
    }

    @PostMapping("/{id}/done")
    public ResponseEntity<ToDo> markToDoAsDone(@PathVariable int id) {
        ToDo updatedToDo = toDoService.markToDoAsDone(id);
        return ResponseEntity.ok(updatedToDo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@RequestBody ToDo toDo, @PathVariable int id) {
        ToDo updatedToDo = toDoService.updateToDo(id, toDo);
        return ResponseEntity.ok(updatedToDo);
    }

    @PutMapping("/{id}/undone")
    public ResponseEntity<ToDo> markToDoAsUndone(@PathVariable int id) {
        ToDo updatedToDo = toDoService.markToDoAsUndone(id);
        return ResponseEntity.ok(updatedToDo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable int id) {
        boolean isDeleted = toDoService.deleteToDoByID(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}