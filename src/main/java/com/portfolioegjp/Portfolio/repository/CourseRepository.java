package com.portfolioegjp.Portfolio.repository;

import com.portfolioegjp.Portfolio.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository <Course, Long> {
    
}
