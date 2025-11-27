package com.digis01.CAlvarezProgramacionNCapasOctubre2025.Service;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.IUsuarioRepositoryDAO;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsJPAService implements UserDetailsService {
    
    private final IUsuarioRepositoryDAO iUsuarioRepositoryDAO;
    
    public UserDetailsJPAService(IUsuarioRepositoryDAO iUsuarioRepositoryDAO){
        this.iUsuarioRepositoryDAO = iUsuarioRepositoryDAO;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioJPA usuario = iUsuarioRepositoryDAO.findByUsername(username);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        
        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol().getNombre()) 
                .build();
    }
    
}