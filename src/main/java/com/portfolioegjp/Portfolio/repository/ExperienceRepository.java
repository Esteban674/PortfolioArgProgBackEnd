package com.portfolioegjp.Portfolio.repository;

import com.portfolioegjp.Portfolio.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository <Experience, Long>{
    
}
