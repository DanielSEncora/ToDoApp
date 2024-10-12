package com.encora.controller;

import com.encora.model.ToDo;
import com.encora.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public class ToDoAppController implements ToDoApp{

    private ToDoService toDoService;

    @Autowired
    public ToDoAppController(ToDoService toDoService){
        this.toDoService = toDoService;
    }

    @Override
    public ResponseEntity getString() {
        Optional<ToDo> toDo =  toDoService.getToDo(2);
        if(toDo.isPresent()){
            return ResponseEntity.ok(toDo.get());
        }
        return ResponseEntity.ok().body("Hola Encorians");
    }

    @Override
    public ResponseEntity<String> postString() {
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/toDo")
    public ToDo getToDo(@RequestParam int id){
        Optional<ToDo> toDo =  toDoService.getToDo(id);
        if(toDo.isPresent()){
            return toDo.get();
        }
        return null;
    }
}
