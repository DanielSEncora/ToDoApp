package com.encora;

import com.encora.model.ToDo;
import com.encora.service.ToDoService;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ToDoService service = new ToDoService();
        String priority = "high";
        String state = "done";

        ToDo todo1 = new ToDo("CutLowUndone", ToDo.Priority.LOW,"21/10/2024","Undone");
        ToDo todo2 = new ToDo("CutHighDone", ToDo.Priority.HIGH, "20/10/2024", "done");
        ToDo todo3 = new ToDo("CutMediumUndone", ToDo.Priority.MEDIUM, "22/10/2024","undone");
        ToDo todo4 = new ToDo("CutLowDone", ToDo.Priority.LOW, "02/11/2024", "Done");
        ToDo todo5 = new ToDo("CutHighUndone", ToDo.Priority.HIGH, "24/10/2024", "undone");


        System.out.println(service.filter(service.toDoList, "high",priority,state));


    }
}
