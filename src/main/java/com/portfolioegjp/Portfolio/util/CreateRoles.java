package com.portfolioegjp.Portfolio.util;

import com.portfolioegjp.Portfolio.security.entity.Rol;
import com.portfolioegjp.Portfolio.security.enums.RolNombre;
import com.portfolioegjp.Portfolio.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;
    
    /**
 * MUY IMPORTANTE: ESTA CLASE SÓLO SE EJECUTARÁ UNA VEZ PARA CREAR LOS ROLES.
 */

    @Override
    public void run(String... args) throws Exception {
//        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
//        Rol rolUser = new Rol(RolNombre.ROLE_USER);
//        rolService.save(rolAdmin);
//        rolService.save(rolUser);
        
    }
}

