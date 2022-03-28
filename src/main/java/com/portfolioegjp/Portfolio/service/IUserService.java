package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.Query;


public interface IUserService {
    
    public List<Usuario> getUsers ();
    
    public Usuario saveUser (Usuario usuario);
    
    public void deleteUser (Long id);
    
    public Usuario findUser (Long id);
}
