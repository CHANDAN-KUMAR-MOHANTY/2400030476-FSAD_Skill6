package com.klu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.klu.model.Book;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private List<Book> bookList = new ArrayList<>();

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Online Library System";
    }

    @GetMapping("/count")
    public int getCount() {
        return bookList.size();
    }

    @GetMapping("/price/{id}")
    public double getPrice(@PathVariable int id) {
        for (Book book : bookList) {
            if (book.getId() == id) {
                return book.getPrice();
            }
        }
        return 0.0;
    }

    @GetMapping("/books")
    public List<String> getBooks() {
        List<String> titles = new ArrayList<>();
        for (Book book : bookList) {
            titles.add(book.getTitle());
        }
        return titles;
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable int id) {
        for (Book book : bookList) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    @GetMapping("/search")
    public List<Book> searchBook(@RequestParam String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }
        return result;
    }

    @GetMapping("/author/{name}")
    public List<Book> getAuthor(@PathVariable String name) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getAuthor().equalsIgnoreCase(name)) {
                result.add(book);
            }
        }
        return result;
    }

    @PostMapping("/addbook")
    public String addBook(@RequestBody Book book) {
        bookList.add(book);
        return "Book added successfully";
    }

    @GetMapping("/viewbooks")
    public List<Book> viewBooks() {
        return bookList;
    }
}