package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.MunicipioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Municipio;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MunicipioDAOJPAImplementation {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;

    public Result GetByIdEstado(int idEstado) {
        Result result = new Result();
        
        try {
            String jpql = "SELECT m FROM MunicipioJPA m WHERE m.estadoJPA.idEstado = :idEstado";
            TypedQuery<MunicipioJPA> query = entityManager.createQuery(jpql, MunicipioJPA.class);
            query.setParameter("idEstado", idEstado);
            
            List<MunicipioJPA> municipiosJPA = query.getResultList();
            
            result.objects = municipiosJPA.stream()
                    .map(municipioJPA -> modelMapper.map(municipioJPA, Municipio.class))
                    .collect(Collectors.toList());
            
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
            result.objects = null;
        }
        
        return result;
    }
}