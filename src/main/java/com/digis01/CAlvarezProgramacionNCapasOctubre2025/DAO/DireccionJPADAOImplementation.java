package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.ColoniaJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Direccion;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionJPADAOImplementation {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Result UpdateDireccion(Direccion direccion, int idUsuario) {
        Result result = new Result();
        
        try {
            DireccionJPA direccionJPA = entityManager.find(DireccionJPA.class, direccion.getIdDireccion());
            
            if (direccionJPA == null) {
                result.correct = false;
                result.errorMessage = "Dirección no encontrada";
                return result;
            }
            
            direccionJPA.setCalle(direccion.getCalle());
            direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(direccion.getNumeroExterior());
            
            if (direccion.getColonia() != null && direccion.getColonia().getIdColonia() > 0) {
                ColoniaJPA coloniaJPA = entityManager.find(ColoniaJPA.class, direccion.getColonia().getIdColonia());
                
                if (coloniaJPA != null) {
                    direccionJPA.setColoniaJPA(coloniaJPA);
                } else {
                    result.correct = false;
                    result.errorMessage = "Colonia no encontrada";
                    return result;
                }
            }
            
            entityManager.merge(direccionJPA);
            
            result.correct = true;
            result.errorMessage = "Dirección actualizada correctamente";
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al actualizar dirección: " + ex.getMessage();
            result.ex = ex;
            ex.printStackTrace();
        }
        
        return result;
    }

    @Transactional
    public Result AddDireccion(Direccion direccion, int idUsuario) {
        Result result = new Result();
        
        try {
            DireccionJPA direccionJPA = new DireccionJPA();
            direccionJPA.setCalle(direccion.getCalle());
            direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(direccion.getNumeroExterior());
            
            if (direccion.getColonia() != null && direccion.getColonia().getIdColonia() > 0) {
                ColoniaJPA coloniaJPA = entityManager.find(ColoniaJPA.class, direccion.getColonia().getIdColonia());
                
                if (coloniaJPA != null) {
                    direccionJPA.setColoniaJPA(coloniaJPA);
                } else {
                    result.correct = false;
                    result.errorMessage = "Colonia no encontrada";
                    return result;
                }
            }
            
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, idUsuario);
            if (usuarioJPA != null) {
                direccionJPA.setUsuarioJPA(usuarioJPA);
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                return result;
            }
            
            // Guardar
            entityManager.persist(direccionJPA);
            
            result.correct = true;
            result.errorMessage = "Dirección agregada correctamente";
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al agregar dirección: " + ex.getMessage();
            result.ex = ex;
            ex.printStackTrace();
        }
        
        return result;
    }

    @Transactional
    public Result DeleteDireccion(int idDireccion) {
        Result result = new Result();
        
        try {
            DireccionJPA direccionJPA = entityManager.find(DireccionJPA.class, idDireccion);
            
            if (direccionJPA != null) {
                entityManager.remove(direccionJPA);
                result.correct = true;
                result.errorMessage = "Dirección eliminada correctamente";
            } else {
                result.correct = false;
                result.errorMessage = "No se pudo eliminar la dirección. El ID no existe.";
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al eliminar dirección: " + ex.getMessage();
            result.ex = ex;
            ex.printStackTrace();
        }
        
        return result;
    }
}