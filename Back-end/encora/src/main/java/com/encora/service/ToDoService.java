package com.encora.service;

import com.encora.model.ToDo;

import java.util.List;

public interface ToDoService {
    List<ToDo> getAllToDos();
    ToDo getToDoByID(int id);
    ToDo createToDo(ToDo toDo);
    ToDo markToDoAsDone(int id);
    ToDo updateToDo(int id, ToDo toDo);
    ToDo markToDoAsUndone(int id);
    boolean deleteToDoByID(int id);
    List<ToDo> filterToDos(String text, String priority, String state);
}