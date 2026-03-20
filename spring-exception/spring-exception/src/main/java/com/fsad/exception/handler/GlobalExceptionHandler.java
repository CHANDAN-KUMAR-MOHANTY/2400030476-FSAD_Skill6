package com.fsad.exception.handler;

import com.fsad.exception.exception.InvalidInputException;
import com.fsad.exception.exception.StudentNotFoundException;
import com.fsad.exception.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * GlobalExceptionHandler – Task 3
 *
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * Intercepts exceptions thrown by ANY controller in the application
 * and returns a structured, user-friendly JSON error response.
 *
 * Handles:
 *   1. StudentNotFoundException   (Task 4) → 404 Not Found
 *   2. InvalidInputException      (Task 5) → 400 Bad Request
 *   3. MethodArgumentTypeMismatch          → 400 Bad Request (text instead of number)
 *   4. Generic Exception                   → 500 Internal Server Error (safety net)
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── Task 4 – Handle StudentNotFoundException ─────────────────────────────
    /**
     * Triggered when /student/{id} is called with an ID that does not exist.
     * Returns HTTP 404 with a readable JSON message.
     */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(
            StudentNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),       // 404
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // ── Task 5 – Handle InvalidInputException ────────────────────────────────
    /**
     * Triggered when /student/{id} is called with a negative or zero ID.
     * Returns HTTP 400 with details about the invalid field.
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInput(
            InvalidInputException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),     // 400
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // ── Handle MethodArgumentTypeMismatch (text instead of number) ───────────
    /**
     * Triggered when the path variable type does not match.
     * Example: GET /student/abc  (abc is not an integer)
     * Returns HTTP 400 with a clear message.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String message = "Invalid value '" + ex.getValue() +
                         "' for parameter '" + ex.getName() +
                         "'. Expected type: " + ex.getRequiredType().getSimpleName();

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),     // 400
                "Bad Request - Type Mismatch",
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // ── Safety Net – Handle all other unexpected exceptions ──────────────────
    /**
     * Catches any unhandled exception and returns HTTP 500.
     * Prevents raw stack traces from leaking to the client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),   // 500
                "Internal Server Error",
                "An unexpected error occurred. Please contact support.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
