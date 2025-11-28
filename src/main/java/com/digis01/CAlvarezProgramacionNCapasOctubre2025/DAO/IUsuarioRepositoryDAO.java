package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IUsuarioRepositoryDAO extends JpaRepository<UsuarioJPA, Integer>{
    
    @Query(value = "SELECT * FROM USUARIO WHERE username = ?1", nativeQuery = true)
    UsuarioJPA findByUsername(String username);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE USUARIO SET estatus = :estatus WHERE idusuario = :idUsuario", nativeQuery = true)
    void cambiarEstatus(@Param("idUsuario") int idUsuario, @Param("estatus") int estatus);

}