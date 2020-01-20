package com.example.rest.repository;

import com.example.rest.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository
        extends CrudRepository<Course, Integer> {
}
