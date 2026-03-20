package com.fsad.exception.exception;

/**
 * StudentNotFoundException – Task 1
 *
 * Custom unchecked exception thrown when a student ID does not exist.
 * Extends RuntimeException so it does NOT need to be declared in method signatures.
 */
public class StudentNotFoundException extends RuntimeException {

    private final int studentId;

    public StudentNotFoundException(int studentId) {
        super("Student with ID " + studentId + " not found. Please check the ID and try again.");
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }
}
