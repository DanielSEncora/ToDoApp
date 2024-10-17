package com.encora.controller;

import com.encora.model.ToDo;
import com.encora.service.ToDoService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class ToDoAppController{

    private final ToDoService toDoService;

    @Autowired
    public ToDoAppController(ToDoService toDoService){
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

    @PostMapping
    public ResponseEntity<ToDo> postToDo(@RequestBody ToDo toDoAux){
        toDoService.postToDo(toDoAux);
        return ResponseEntity.ok(toDoAux);
    }

    @PutMapping("{id}")
    public ResponseEntity<ToDo> putToDo(@RequestBody ToDo toDoAux, @PathVariable String id){
        return ResponseEntity.ok(toDoService.putToDo(toDoAux,Integer.parseInt(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable String id){

        if(toDoService.getToDoByID(Integer.parseInt(id)) != null){
            return ResponseEntity.ok(toDoService.deleteToDoByID(Integer.parseInt(id)));
        }
        return ResponseEntity.notFound().build();
    }
}
