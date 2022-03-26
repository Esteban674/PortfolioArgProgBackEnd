package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Skills;
import com.portfolioegjp.Portfolio.repository.SkillsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillsService implements ISkillsService{

    @Autowired
    private SkillsRepository skillsRepo;
    
    @Override
    public List<Skills> getSkills() {
        List<Skills> listaHabilidades = skillsRepo.findAll();
        return listaHabilidades;
    }

    @Override
    public Skills saveSkill(Skills habilidad) {
        return skillsRepo.save(habilidad);
    }

    @Override
    public void deleteSkill(Long id) {
        skillsRepo.deleteById(id);
    }

    @Override
    public Skills findSkill(Long id) {
       Skills habilidad = skillsRepo.findById(id).orElse(null);
       return habilidad;
    }
    
}
