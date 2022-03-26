package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Skills;
import java.util.List;

public interface ISkillsService {
    
    public List<Skills> getSkills ();
    
    public Skills saveSkill (Skills habilidad);
    
    public void deleteSkill (Long id);
    
    public Skills findSkill (Long id);
}
