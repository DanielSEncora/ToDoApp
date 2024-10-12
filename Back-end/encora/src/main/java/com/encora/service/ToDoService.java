package com.encora.service;

import com.encora.model.ToDo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ToDoService {

    private List<ToDo> toDoList;

    public ToDoService(){
        toDoList = new ArrayList<>();

        ToDo todo1 = new ToDo("Cut", ToDo.Priority.LOW);
        ToDo todo2 = new ToDo("Paste", ToDo.Priority.HIGH, "20/10/2024");
        ToDo todo3 = new ToDo("Work", ToDo.Priority.MEDIUM);
        ToDo todo4 = new ToDo("Travel", ToDo.Priority.HIGH, "02/11/2024");
        ToDo todo5 = new ToDo("Play music", ToDo.Priority.MEDIUM);

        toDoList.addAll(Arrays.asList(todo1,todo2,todo3,todo4,todo5));
    }

    public Optional<ToDo> getToDo(int id){
        Optional optional = Optional.empty();
        for (ToDo toDo: toDoList){
            if (id == toDo.getId()){
                optional = Optional.of(toDo);
                return optional;
            }
        }
        return optional;
    }

}
