package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Experience;
import java.util.List;

public interface IExperienceService {
    
    public List<Experience> getExperiences ();
    
    public Experience saveExperience (Experience experiencia);
    
    public void deleteExperience (Long id);
    
    public Experience findExperience (Long id);
}
