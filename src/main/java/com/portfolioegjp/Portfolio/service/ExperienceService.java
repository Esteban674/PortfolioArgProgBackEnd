package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Experience;
import com.portfolioegjp.Portfolio.repository.ExperienceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceService implements IExperienceService{
    
    @Autowired
    private ExperienceRepository experienceRepo;

    @Override
    public List<Experience> getExperiences() {
      List<Experience> listaExperiencias = experienceRepo.findAll();
      return listaExperiencias;
    }

    @Override
    public Experience saveExperience(Experience experiencia) {
        return experienceRepo.save(experiencia);
    }

    @Override
    public void deleteExperience(Long id) {
       experienceRepo.deleteById(id);
    }

    @Override
    public Experience findExperience(Long id) {
        Experience experiencia = experienceRepo.findById(id).orElse(null);
        return experiencia;
    }
}
