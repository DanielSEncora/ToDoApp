package com.encora.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link GlobalExceptionHandler} class.
 */
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    /**
     * Sets up the test environment by initializing mocks.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the handling of {@link ResourceNotFoundException}.
     * Verifies that the response status is 404 NOT FOUND and the error message is correct.
     */
    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        when(webRequest.getDescription(false)).thenReturn("description");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(ex, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    /**
     * Tests the handling of {@link InvalidInputException}.
     * Verifies that the response status is 400 BAD REQUEST and the error message is correct.
     */
    @Test
    void testHandleInvalidInputException() {
        InvalidInputException ex = new InvalidInputException("Invalid input");
        when(webRequest.getDescription(false)).thenReturn("description");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidInputException(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid input", response.getBody().getMessage());
    }

    /**
     * Tests the handling of {@link MethodArgumentNotValidException}.
     * Verifies that the response status is 400 BAD REQUEST and the error message indicates validation failure.
     *
     * @throws NoSuchMethodException if the dummy method cannot be found
     */
    @Test
    void testHandleMethodArgumentNotValid() throws NoSuchMethodException {
        // Create a real MethodParameter from a dummy method
        Method method = DummyClass.class.getMethod("dummyMethod");
        MethodParameter methodParameter = new MethodParameter(method, -1);

        // Mock BindingResult and FieldError
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "error message");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        // Create MethodArgumentNotValidException with non-null MethodParameter
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);
        when(webRequest.getDescription(false)).thenReturn("description");

        // Call the handler method and verify the response
        ResponseEntity<Object> response = globalExceptionHandler.handleMethodArgumentNotValid(ex, null, HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Validation failed"));
    }

    /**
     * Tests the handling of general {@link Exception}.
     * Verifies that the response status is 500 INTERNAL SERVER ERROR and the error message is correct.
     */
    @Test
    void testHandleGlobalException() {
        Exception ex = new Exception("Global exception");
        when(webRequest.getDescription(false)).thenReturn("description");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGlobalException(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Global exception", response.getBody().getMessage());
    }

    /**
     * A dummy class used to create a real {@link MethodParameter} instance for testing.
     */
    private static class DummyClass {
        public void dummyMethod() {
        }
    }
}