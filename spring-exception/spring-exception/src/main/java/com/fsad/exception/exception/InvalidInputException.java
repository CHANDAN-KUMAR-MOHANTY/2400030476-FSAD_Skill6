package com.fsad.exception.exception;

/**
 * InvalidInputException – Task 5
 *
 * Custom exception thrown when the input provided by the user is
 * invalid (e.g., negative ID, text where a number is expected).
 */
public class InvalidInputException extends RuntimeException {

    private final String field;
    private final String rejectedValue;

    public InvalidInputException(String field, String rejectedValue) {
        super("Invalid value '" + rejectedValue + "' for field '" + field +
              "'. Please provide a valid positive number.");
        this.field         = field;
        this.rejectedValue = rejectedValue;
    }

    public String getField()         { return field;         }
    public String getRejectedValue() { return rejectedValue; }
}
