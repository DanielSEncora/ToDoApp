package com.encora.service;

import com.encora.model.ToDo;
import java.util.List;
import java.util.Optional;

/**
 * The ToDoService interface provides methods for managing ToDo items.
 * It includes operations for creating, retrieving, updating, and deleting ToDo items,
 * as well as marking them as done or undone, and filtering them based on certain criteria.
 */
public interface ToDoService {

    /**
     * Retrieves all ToDo items.
     *
     * @return a list of all ToDo items
     */
    List<ToDo> getAllToDos();

    /**
     * Retrieves a ToDo item by its ID.
     *
     * @param id the ID of the ToDo item to retrieve
     * @return an Optional containing the ToDo item if found, or an empty Optional if not found
     */
    Optional<ToDo> getToDoByID(int id);

    /**
     * Creates a new ToDo item.
     *
     * @param toDo the ToDo item to create
     * @return the created ToDo item
     */
    ToDo createToDo(ToDo toDo);

    /**
     * Marks a ToDo item as done by its ID.
     *
     * @param id the ID of the ToDo item to mark as done
     * @return an Optional containing the updated ToDo item if found, or an empty Optional if not found
     */
    Optional<ToDo> markToDoAsDone(int id);

    /**
     * Updates a ToDo item by its ID.
     *
     * @param id the ID of the ToDo item to update
     * @param toDo the updated ToDo item
     * @return an Optional containing the updated ToDo item if found, or an empty Optional if not found
     */
    Optional<ToDo> updateToDo(int id, ToDo toDo);

    /**
     * Marks a ToDo item as undone by its ID.
     *
     * @param id the ID of the ToDo item to mark as undone
     * @return an Optional containing the updated ToDo item if found, or an empty Optional if not found
     */
    Optional<ToDo> markToDoAsUndone(int id);

    /**
     * Deletes a ToDo item by its ID.
     *
     * @param id the ID of the ToDo item to delete
     * @return an Optional containing the deleted ToDo item if found, or an empty Optional if not found
     */
    Optional<ToDo> deleteToDoByID(int id);

    /**
     * Filters ToDo items based on text, priority, and state.
     *
     * @param text the text to filter by
     * @param priority the priority to filter by
     * @param state the state to filter by
     * @return a list of filtered ToDo items
     */
    List<ToDo> filterToDos(String text, String priority, String state);
}