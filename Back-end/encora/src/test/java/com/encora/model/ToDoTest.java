package com.encora.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ToDo} class.
 */
class ToDoTest {

    private ToDo todo;

    /**
     * Sets up the test environment by initializing a {@link ToDo} instance before each test.
     */
    @BeforeEach
    void setUp() {
        todo = new ToDo();
    }

    /**
     * Tests the default constructor of {@link ToDo}.
     * Verifies that the created instance has default values.
     */
    @Test
    void testDefaultConstructor() {
        assertNotNull(todo);
        assertFalse(todo.isDone());
        assertNotNull(todo.getCreationDate());
        assertEquals("", todo.getDoneDate());
    }

    /**
     * Tests the constructor of {@link ToDo} with text and priority.
     * Verifies that the created instance has the correct text and priority.
     */
    @Test
    void testConstructorWithTextAndPriority() {
        ToDo todo = new ToDo("Test Task", ToDo.Priority.HIGH);
        assertEquals("Test Task", todo.getText());
        assertEquals(ToDo.Priority.HIGH, todo.getPriority());
        assertFalse(todo.isDone());
    }

    /**
     * Tests the constructor of {@link ToDo} with text, priority, and due date.
     * Verifies that the created instance has the correct text, priority, and due date.
     */
    @Test
    void testConstructorWithTextPriorityAndDueDate() {
        ToDo todo = new ToDo("Test Task", ToDo.Priority.MEDIUM, "24/01/2025 00:00:00");
        assertEquals("Test Task", todo.getText());
        assertEquals(ToDo.Priority.MEDIUM, todo.getPriority());
        assertNotNull(todo.getDueDate());
        assertFalse(todo.isDone());
    }

    /**
     * Tests the constructor of {@link ToDo} with text, priority, due date, and done status.
     * Verifies that the created instance has the correct text, priority, due date, and done status.
     */
    @Test
    void testConstructorWithTextPriorityDueDateAndDone() {
        ToDo todo = new ToDo("Test Task", ToDo.Priority.LOW, "24/01/2025 00:00:00", "Done");
        assertEquals("Test Task", todo.getText());
        assertEquals(ToDo.Priority.LOW, todo.getPriority());
        assertNotNull(todo.getDueDate());
        assertTrue(todo.isDone());
    }

    /**
     * Tests the full constructor of {@link ToDo}.
     * Verifies that the created instance has all attributes correctly set.
     */
    @Test
    void testFullConstructor() {
        ToDo todo = new ToDo("Test Task", ToDo.Priority.HIGH, "24/01/2025 00:00:00", "23/01/2025 00:00:00", "24/01/2025 12:00:00", true);
        assertEquals("Test Task", todo.getText());
        assertEquals(ToDo.Priority.HIGH, todo.getPriority());
        assertNotNull(todo.getDueDate());
        assertNotNull(todo.getCreationDate());
        assertEquals("24/01/2025 12:00:00", todo.getDoneDate());
        assertTrue(todo.isDone());
    }

    /**
     * Tests the setters and getters of {@link ToDo}.
     * Verifies that the attributes can be correctly set and retrieved.
     */
    @Test
    void testSettersAndGetters() {
        ToDo todo = new ToDo();
        todo.setText("Updated Task");
        todo.setPriority(ToDo.Priority.MEDIUM);
        todo.setDueDate("25/01/2025 00:00:00");
        todo.setDone(true);
        todo.setDoneDate("25/01/2025 12:00:00");

        assertEquals("Updated Task", todo.getText());
        assertEquals(ToDo.Priority.MEDIUM, todo.getPriority());
        assertNotNull(todo.getDueDate());
        assertTrue(todo.isDone());
        assertEquals("25/01/2025 12:00:00", todo.getDoneDate());
    }

    /**
     * Tests the private {@code parseDate} method of {@link ToDo} using reflection.
     * Verifies that the method correctly parses a date string.
     *
     * @throws Exception if the method cannot be accessed or invoked
     */
    @Test
    void testParseDate() throws Exception {
        Method parseDateMethod = todo.getClass().getDeclaredMethod("parseDate", String.class);
        parseDateMethod.setAccessible(true); // Make the private method accessible
        Date date = (Date) parseDateMethod.invoke(todo, "24/01/2025 00:00:00");
        assertNotNull(date);
    }
}