package com.portfolioegjp.Portfolio.security.repository;

import com.portfolioegjp.Portfolio.security.entity.Rol;
import com.portfolioegjp.Portfolio.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}

