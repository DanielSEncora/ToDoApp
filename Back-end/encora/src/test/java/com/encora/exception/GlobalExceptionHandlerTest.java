package com.encora.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        when(webRequest.getDescription(false)).thenReturn("description");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(ex, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    @Test
    void testHandleInvalidInputException() {
        InvalidInputException ex = new InvalidInputException("Invalid input");
        when(webRequest.getDescription(false)).thenReturn("description");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidInputException(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid input", response.getBody().getMessage());
    }

    @Test
    void testHandleMethodArgumentNotValid() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "error message");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        when(webRequest.getDescription(false)).thenReturn("description");

        ResponseEntity<Object> response = globalExceptionHandler.handleMethodArgumentNotValid(ex, null, HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Validation failed"));
    }

    @Test
    void testHandleGlobalException() {
        Exception ex = new Exception("Global exception");
        when(webRequest.getDescription(false)).thenReturn("description");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGlobalException(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Global exception", response.getBody().getMessage());
    }
}