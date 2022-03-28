package com.portfolioegjp.Portfolio.service;

import com.portfolioegjp.Portfolio.model.Usuario;
import com.portfolioegjp.Portfolio.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService,UserDetailsService{
    
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<Usuario> getUsers() {
      List<Usuario> listaUsuarios = userRepo.findAll();
      return listaUsuarios;
    }

    @Override
    public Usuario saveUser(Usuario usuario) {
       return userRepo.save(usuario);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public Usuario findUser(Long id) {
        Usuario usuario = userRepo.findById(id).orElse(null);
        return usuario;
    }

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Usuario usuario = userRepo.findByUsername(username);
      
      if(usuario == null){
          logger.error("Error en el login: no existe el usuario '" +username+"' en el sistema!");
          throw new UsernameNotFoundException("Error en el login: no existe el usuario '" +username+"' en el sistema!");
      }
      
      List<GrantedAuthority> authorities = usuario.getRoles()
              .stream()
              .map(role -> new SimpleGrantedAuthority(role.getName()))
              .peek(authority -> logger.info("Role: "+ authority.getAuthority()))
              .collect(Collectors.toList());
      
      return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true , authorities);
    }

}
