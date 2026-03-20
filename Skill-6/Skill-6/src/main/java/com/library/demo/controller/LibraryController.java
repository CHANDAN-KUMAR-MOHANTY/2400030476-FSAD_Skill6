package com.library.demo.controller;

import com.library.demo.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class LibraryController {

    private final List<String> bookTitles = Arrays.asList(
            "Clean Code",
            "Effective Java",
            "Spring in Action",
            "Design Patterns"
    );

    private final List<Book> addedBooks = new CopyOnWriteArrayList<>();

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Online Library System";
    }

    @GetMapping("/count")
    public int count() {
        return bookTitles.size() + addedBooks.size();
    }

    @GetMapping("/price")
    public double price() {
        return 499.99;
    }

    @GetMapping("/books")
    public List<String> getBooks() {
        return bookTitles;
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable int id) {
        List<Book> sampleBooks = new ArrayList<>();
        sampleBooks.add(new Book(1L, "Clean Code", "Robert C. Martin", 499.99));
        sampleBooks.add(new Book(2L, "Effective Java", "Joshua Bloch", 599.99));
        sampleBooks.add(new Book(3L, "Spring in Action", "Craig Walls", 699.99));

        return sampleBooks.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(new Book((long) id, "Book Not Found", "Unknown", 0.0));
    }

    @GetMapping("/search")
    public String searchByTitle(@RequestParam("title") String title) {
        return "Search completed for title: " + title;
    }

    @GetMapping("/author/{name}")
    public String author(@PathVariable String name) {
        return "Showing books by author: " + name;
    }

    @PostMapping("/addbook")
    public String addBook(@RequestBody Book book) {
        addedBooks.add(book);
        return "Book added successfully: " + book.getTitle();
    }

    @GetMapping("/viewbooks")
    public List<Book> viewBooks() {
        return addedBooks;
    }
}
