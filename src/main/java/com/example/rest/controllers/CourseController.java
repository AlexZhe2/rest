package com.example.rest.controllers;

import com.example.rest.entity.Course;
import com.example.rest.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
//@RequestMapping(value = "")
public class CourseController {

    private CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // данные по всем курсам
    @GetMapping(value = "/courses")
    public Iterable<Course> findAll(){
        return courseRepository.findAll();
    }

    // данные по id
    @GetMapping(value = "/courses/{id}")
    public Optional<Course> findById(@PathVariable int id){
        return courseRepository.findById(id);
    }
}
