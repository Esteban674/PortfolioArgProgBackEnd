package com.portfolioegjp.Portfolio.repository;

import com.portfolioegjp.Portfolio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <Usuario, Long>{
    
    public Usuario findByUsername(String username);
    
    @Query("select u from Usuario u where u.username=?1")
    public Usuario findByUsername2(String username);
}
