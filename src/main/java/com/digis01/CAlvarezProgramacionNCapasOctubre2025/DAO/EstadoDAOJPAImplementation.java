package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.EstadoJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Estado;
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
public class EstadoDAOJPAImplementation {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public Result GetByIdPais(int idPais) {
        Result result = new Result();
        
        try {
            System.out.println("=== Buscando estados para país: " + idPais + " ===");
            
            // ⭐ CAMBIO: Usar el nombre correcto de la propiedad (PaisJPA con mayúscula)
            String jpql = "SELECT e FROM EstadoJPA e WHERE e.PaisJPA.IdPais = :idPais";
            TypedQuery<EstadoJPA> query = entityManager.createQuery(jpql, EstadoJPA.class);
            query.setParameter("idPais", idPais);
            
            List<EstadoJPA> estadosJPA = query.getResultList();
            
            System.out.println("Estados encontrados: " + estadosJPA.size());
            
            // Convertir a ML
            result.objects = estadosJPA.stream()
                    .map(estadoJPA -> modelMapper.map(estadoJPA, Estado.class))
                    .collect(Collectors.toList());
            
            result.correct = true;
            
            System.out.println("✅ Estados convertidos correctamente");
            
        } catch (Exception ex) {
            System.err.println("❌ Error en GetByIdPais: " + ex.getMessage());
            ex.printStackTrace();
            
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
            result.objects = null;
        }
        
        return result;
    }
}