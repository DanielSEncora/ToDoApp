package com.encora.service;

import com.encora.model.ToDo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public ToDo getToDoByID(int id){
        for (ToDo toDo: toDoList){
            if (id == toDo.getId()){
                return toDo;
            }
        }
        return null;
    }

    public ToDo postToDo(@RequestBody ToDo toDo){
                toDoList.add(toDo);
        return toDo;
    }

    public ToDo putToDo(@RequestBody ToDo toDo, int id){
        if (getToDoByID(id) != null){
            toDoList.remove(getToDoByID(id));
            toDo.setId(id);
            toDoList.add(toDo);
            return toDo;
        }
        return null;
    }

    public ToDo deleteToDoByID(int id){
        for (ToDo toDo: toDoList){
            if (id == toDo.getId()){
                toDoList.remove(toDo);
                return toDo;
            }
        }
        return null;
    }

    public void printArray(ArrayList<ToDo> todos){
        for (ToDo todo: todos){
            System.out.println("Todo:");
            System.out.println(todo.getId());
            System.out.println(todo.getText());
            System.out.println(todo.getPriority());
            System.out.println(todo.getDueDate());
            System.out.println(todo.getCreationDate());
            System.out.println(todo.getDoneDate());
        }
    }

    public ArrayList<ToDo> filter(ArrayList<ToDo> toDos, String text){
        ArrayList<ToDo> sortedArray = new ArrayList<>();
        CharSequence charSeq = text.toLowerCase();

        for (ToDo todo: toDos){
            if (todo.text.toLowerCase().contains(charSeq)){
                sortedArray.add(todo);
                System.out.println(todo.text);
            }
        }

        return sortedArray;
    }
}
