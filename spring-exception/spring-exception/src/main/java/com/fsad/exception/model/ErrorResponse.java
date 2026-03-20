package com.fsad.exception.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ErrorResponse – Task 6
 *
 * Structured JSON response returned by the GlobalExceptionHandler.
 * Every error response includes: timestamp, statusCode, error, message, path.
 *
 * Example JSON:
 * {
 *   "timestamp"  : "2024-11-15 10:30:45",
 *   "statusCode" : 404,
 *   "error"      : "Not Found",
 *   "message"    : "Student with ID 99 not found.",
 *   "path"       : "/student/99"
 * }
 */
public class ErrorResponse {

    private String timestamp;
    private int    statusCode;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(int statusCode, String error, String message, String path) {
        this.timestamp  = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.statusCode = statusCode;
        this.error      = error;
        this.message    = message;
        this.path       = path;
    }

    public String getTimestamp()  { return timestamp;  }
    public int    getStatusCode() { return statusCode; }
    public String getError()      { return error;      }
    public String getMessage()    { return message;    }
    public String getPath()       { return path;       }
}
