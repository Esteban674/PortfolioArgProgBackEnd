package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Course;
import java.util.List;

public interface ICourseService {
    
    public List<Course> getCourses ();
    
    public Course saveCourse (Course curso);
    
    public void deleteCourse (Long id);
    
    public Course findCourse (Long id);
}
