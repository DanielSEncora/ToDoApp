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
    public ToDoController(ToDoService toDoService){
        this.toDoService = toDoService;
    }


    @GetMapping()
    public ResponseEntity<List<ToDo>> getAllToDos(){
            return ResponseEntity.ok(toDoService.toDoList);
    }
    @GetMapping("{id}")
    public ResponseEntity<ToDo> getToDo(@PathVariable String id){

        if(toDoService.getToDoByID(Integer.parseInt(id)) != null){
            return ResponseEntity.ok(toDoService.getToDoByID(Integer.parseInt(id)));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/filter/{text}-{priority}-{state}")
    public List<ToDo> filterToDos(@PathVariable String text,@PathVariable String priority, @PathVariable String state ) {
        List<ToDo> allToDos = toDoService.toDoList; // Fetch all todos
        return toDoService.filter(allToDos, text,priority,state); // Use your existing filter method
    }

    @PostMapping
    public ResponseEntity<ToDo> postToDo(@RequestBody ToDo toDoAux){
        toDoService.postToDo(toDoAux);
        return ResponseEntity.ok(toDoAux);
    }

    @PostMapping("{id}/done")
    public ResponseEntity<ToDo> postToDo(@PathVariable String id){
        return ResponseEntity.ok(toDoService.postToDo(Integer.parseInt(id)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ToDo> putToDo(@RequestBody ToDo toDoAux, @PathVariable String id){
        return ResponseEntity.ok(toDoService.putToDo(toDoAux,Integer.parseInt(id)));
    }

    @PutMapping("{id}/undone")
    public ResponseEntity<ToDo> putToDo(@PathVariable String id){
        return ResponseEntity.ok(toDoService.putToDo(Integer.parseInt(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable String id){

        if(toDoService.getToDoByID(Integer.parseInt(id)) != null){
            return ResponseEntity.ok(toDoService.deleteToDoByID(Integer.parseInt(id)));
        }
        return ResponseEntity.notFound().build();
    }
}
