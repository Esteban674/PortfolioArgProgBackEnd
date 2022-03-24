package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Education;
import com.portfolioegjp.Portfolio.repository.EducationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationService implements IEducationService {

    @Autowired
    private EducationRepository educationRepo;
    
    @Override
    public List<Education> getEducations() {
        List<Education> listaEducaciones = educationRepo.findAll();
      return listaEducaciones;
    }

    @Override
    public Education saveEducation(Education educacion) {
       return educationRepo.save(educacion);
    }

    @Override
    public void deleteEducation(Long id) {
        educationRepo.deleteById(id);
    }

    @Override
    public Education findEducation(Long id) {
        Education educacion = educationRepo.findById(id).orElse(null);
        return educacion;
    }
    
}
