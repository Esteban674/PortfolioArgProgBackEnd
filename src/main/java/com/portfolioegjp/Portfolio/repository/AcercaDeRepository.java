package com.portfolioegjp.Portfolio.repository;

import com.portfolioegjp.Portfolio.model.AcercaDe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcercaDeRepository extends JpaRepository <AcercaDe, Long>{
    
}
