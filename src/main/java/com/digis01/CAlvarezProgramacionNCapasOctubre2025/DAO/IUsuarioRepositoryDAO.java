package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUsuarioRepositoryDAO extends JpaRepository<UsuarioJPA, Integer>{
    
    @Query(value = "SELECT * FROM USUARIO WHERE username = ?1", nativeQuery = true)
    UsuarioJPA findByUsername(String username);
    
}