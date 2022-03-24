package com.portfolioegjp.Portfolio.repository;

import com.portfolioegjp.Portfolio.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository <Education, Long> {
    
}
