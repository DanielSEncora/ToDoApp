package com.encora.exception;

import java.time.LocalDateTime;

/**
 * The ErrorResponse class represents the structure of an error response
 * that will be sent to the client in case of an exception.
 * It includes a timestamp, an error message, and additional details.
 */
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    /**
     * Constructs an ErrorResponse with the specified timestamp, message, and details.
     *
     * @param timestamp the time when the error occurred
     * @param message the error message
     * @param details additional details about the error
     */
    public ErrorResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    /**
     * Returns the timestamp of the error.
     *
     * @return the timestamp of the error
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the error.
     *
     * @param timestamp the new timestamp of the error
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the new error message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns additional details about the error.
     *
     * @return additional details about the error
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets additional details about the error.
     *
     * @param details the new details about the error
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Returns a string representation of the ErrorResponse object.
     *
     * @return a string representation of the ErrorResponse object
     */
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}