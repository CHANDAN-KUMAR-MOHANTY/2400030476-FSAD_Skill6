package com.klu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klu.model.Course;
import com.klu.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService service;

    @PostMapping("/add")
    public Course createCourse(@RequestBody Course course) {
        if (course.getTitle() == null || course.getFee() == null) {
            throw new RuntimeException("Invalid Course Data");
        }
        return service.addCourse(course);
    }

    @GetMapping("/getall")
    public List<Course> getAllCourses() {
        return service.getAllCourses();
    }

    @GetMapping("/getid/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return service.getCourseById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return service.updateCourse(id, course);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        if (service.deleteCourse(id)) {
            return "Course Deleted Successfully";
        }
        return "Course Not Found";
    }

    @GetMapping("/search")
    public List<Course> searchCourse(@RequestParam String title) {
        return service.searchByTitle(title);
    }
}