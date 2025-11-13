package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.RolJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Rol;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRolJPA {
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        
        try {
            
            List<RolJPA> rolesJPA = entityManager
                    .createQuery("SELECT r FROM RolJPA r", RolJPA.class)
                    .getResultList();
            
            
            List<Rol> roles = rolesJPA.stream()
                    .map(rolJPA -> modelMapper.map(rolJPA, Rol.class))
                    .collect(Collectors.toList());
            
            
            result.objects = (List<Object>) (List<?>) roles;
            result.correct = true;
            result.errorMessage = "Roles obtenidos correctamente: " + roles.size();
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al obtener roles: " + ex.getMessage();
            result.ex = ex;
            result.objects = null;
            
            
        }
        
        return result;
    }
}