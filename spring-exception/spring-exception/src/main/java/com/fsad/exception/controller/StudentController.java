package com.fsad.exception.controller;

import com.fsad.exception.entity.Student;
import com.fsad.exception.exception.InvalidInputException;
import com.fsad.exception.exception.StudentNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StudentController – Task 2 & 7 & 8
 *
 * REST endpoints:
 *   GET  /student/{id}        → fetch student by ID (throws custom exceptions)
 *   GET  /student/all         → fetch all students (happy path)
 *   GET  /student/search      → search by name (triggers InvalidInputException)
 *
 * In-memory student store (no DB needed for this skill).
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    // ── In-memory student data store ──────────────────────────────────────────
    private static final Map<Integer, Student> STUDENTS = new HashMap<>();

    static {
        STUDENTS.put(1, new Student(1, "Megha",  "CSE",    3));
        STUDENTS.put(2, new Student(2, "Revanth",    "AI&DS",  2));
        STUDENTS.put(3, new Student(3, "Ravi Kumar",    "CS&IT",  4));
        STUDENTS.put(4, new Student(4, "Divya Reddy",   "CSE",    1));
        STUDENTS.put(5, new Student(5, "Kiran Babu",    "AI&DS",  3));
    }

    // ── Task 2 – GET /student/{id} ────────────────────────────────────────────
    /**
     * Happy path  : GET /student/1   → returns student JSON
     * Invalid ID  : GET /student/99  → throws StudentNotFoundException → 404
     * Negative ID : GET /student/-1  → throws InvalidInputException    → 400
     * Text input  : GET /student/abc → Spring throws TypeMismatch       → 400
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {

        // Task 5 – validate input: reject zero or negative IDs
        if (id <= 0) {
            throw new InvalidInputException("id", String.valueOf(id));
        }

        // Task 2 – fetch from store; throw custom exception if not found
        Student student = STUDENTS.get(id);
        if (student == null) {
            throw new StudentNotFoundException(id);
        }

        return ResponseEntity.ok(student);
    }

    // ── GET /student/all – returns all students (no exception) ───────────────
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(List.copyOf(STUDENTS.values()));
    }

    // ── GET /student/search?name= – triggers InvalidInputException if blank ───
    /**
     * Task 8 – test InvalidInputException with empty/blank name input.
     * Example: GET /student/search?name=   → 400 Bad Request
     */
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchByName(@RequestParam String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("name", name == null ? "null" : "\"\"");
        }

        List<Student> result = STUDENTS.values().stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        if (result.isEmpty()) {
            throw new StudentNotFoundException(-1); // reuse for "no match" case
        }

        return ResponseEntity.ok(result);
    }
}
