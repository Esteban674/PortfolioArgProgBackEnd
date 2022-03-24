package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Course;
import com.portfolioegjp.Portfolio.repository.CourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService implements ICourseService{

    @Autowired
    private CourseRepository courseRepo;
    
    @Override
    public List<Course> getCourses() {
         List<Course> listaCursos = courseRepo.findAll();
      return listaCursos;
    }

    @Override
    public Course saveCourse(Course curso) {
        return courseRepo.save(curso);
    }

    @Override
    public void deleteCourse(Long id) {
       courseRepo.deleteById(id);
    }

    @Override
    public Course findCourse(Long id) {
        Course curso = courseRepo.findById(id).orElse(null);
        return curso;
    }
    
}
