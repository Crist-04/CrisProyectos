package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.ColoniaJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Colonia;
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
public class ColoniaJPADAOImplementation {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Result GetByIdMunicipio(int idMunicipio) {
        Result result = new Result();
        
        try {
            
            String jpql = "SELECT c FROM ColoniaJPA c WHERE c.MunicipioJPA.IdMunicipio = :idMunicipio";
            TypedQuery<ColoniaJPA> query = entityManager.createQuery(jpql, ColoniaJPA.class);
            query.setParameter("idMunicipio", idMunicipio);
            
            List<ColoniaJPA> coloniasJPA = query.getResultList();
            
            
            result.objects = coloniasJPA.stream()
                    .map(coloniaJPA -> modelMapper.map(coloniaJPA, Colonia.class))
                    .collect(Collectors.toList());
            
            result.correct = true;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
            result.objects = null;
        }
        
        return result;
    }
    
    public Result GetByIdColonia(int idColonia) {
        Result result = new Result();
        
        try {
            System.out.println("=== Buscando colonia con ID: " + idColonia + " ===");
            
            ColoniaJPA coloniaJPA = entityManager.find(ColoniaJPA.class, idColonia);
            
            if (coloniaJPA != null) {
                result.object = modelMapper.map(coloniaJPA, Colonia.class);
                result.correct = true;
                System.out.println("✅ Colonia encontrada: " + coloniaJPA.Nombre);
            } else {
                result.correct = false;
                result.errorMessage = "Colonia no encontrada";
                System.out.println("❌ Colonia no encontrada");
            }
            
        } catch (Exception ex) {
            System.err.println("❌ Error en GetByIdColonia: " + ex.getMessage());
            ex.printStackTrace();
            
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
            result.object = null;
        }
        
        return result;
    }
}