package com.encora.service;

import com.encora.model.ToDo;
import java.util.List;
import java.util.Optional;

public interface ToDoService {
    List<ToDo> getAllToDos();
    Optional<ToDo> getToDoByID(int id);
    ToDo createToDo(ToDo toDo);
    Optional<ToDo> markToDoAsDone(int id);
    ToDo updateToDo(int id, ToDo toDo);
    Optional<ToDo> markToDoAsUndone(int id);
    boolean deleteToDoByID(int id);
    List<ToDo> filterToDos(String text, String priority, String state);
}