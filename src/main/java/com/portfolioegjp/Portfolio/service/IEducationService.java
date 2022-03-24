package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Education;
import java.util.List;


public interface IEducationService {
    
    public List<Education> getEducations ();
    
    public Education saveEducation (Education educacion);
    
    public void deleteEducation (Long id);
    
    public Education findEducation (Long id);
}
