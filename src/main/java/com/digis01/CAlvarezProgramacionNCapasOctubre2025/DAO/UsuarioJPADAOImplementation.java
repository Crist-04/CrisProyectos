package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.ColoniaJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.RolJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {

            TypedQuery<UsuarioJPA> query = entityManager.createQuery(
                    "SELECT DISTINCT u FROM UsuarioJPA u "
                    + "LEFT JOIN FETCH u.DireccionesJPA d "
                    + "LEFT JOIN FETCH d.ColoniaJPA c "
                    + "LEFT JOIN FETCH c.MunicipioJPA m "
                    + "LEFT JOIN FETCH m.EstadoJPA e "
                    + "LEFT JOIN FETCH e.PaisJPA",
                    UsuarioJPA.class
            );
            List<UsuarioJPA> usuariosJPA = query.getResultList();
// JPA ML , para llenar lista de usuarios
            List<Usuario> usuariosML = usuariosJPA.stream()
                    .map(jpa -> modelMapper.map(jpa, Usuario.class))
                    .collect(Collectors.toList());

            result.objects = (List<Object>) (List<?>) usuariosML;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        return result;
    }

    @Override
    @Transactional
    public Result Add(Usuario usuario) {
        Result result = new Result();

        try {
            UsuarioJPA usuarioJPA = modelMapper.map(usuario, UsuarioJPA.class);

            if (usuarioJPA.getRol() != null && usuarioJPA.getRol().getIdRol() > 0) {
                RolJPA rolReference = entityManager.getReference(RolJPA.class, usuarioJPA.getRol().getIdRol());
                usuarioJPA.setRol(rolReference);
            }

            if (usuarioJPA.getDireccionesJPA() != null && !usuarioJPA.getDireccionesJPA().isEmpty()) {
                for (DireccionJPA direccion : usuarioJPA.getDireccionesJPA()) {
                    if (direccion.getColoniaJPA() != null && direccion.getColoniaJPA().getIdColonia() > 0) {
                        ColoniaJPA coloniaReference = entityManager.getReference(
                                ColoniaJPA.class,
                                direccion.getColoniaJPA().getIdColonia()
                        );
                        direccion.setColoniaJPA(coloniaReference);
                    }
                    direccion.setUsuarioJPA(usuarioJPA);
                }
            }

            entityManager.persist(usuarioJPA);

            
            entityManager.flush();

            
            result.object = modelMapper.map(usuarioJPA, Usuario.class);
            result.correct = true;
            result.errorMessage = "Usuario agregado correctamente con ID: " + usuarioJPA.getIdUsuario();

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al agregar usuario: " + ex.getMessage();
            result.ex = ex;
            ex.printStackTrace(); // Para debugging
        }

        return result;
    }
}
