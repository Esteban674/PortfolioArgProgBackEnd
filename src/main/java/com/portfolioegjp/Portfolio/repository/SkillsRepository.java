package com.portfolioegjp.Portfolio.repository;

import com.portfolioegjp.Portfolio.model.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository <Skills, Long> {
    
}
