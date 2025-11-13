package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

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
}
