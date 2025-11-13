package com.digis01.CAlvarezProgramacionNCapasOctubre2025.Configuration;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.ColoniaJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.EstadoJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.MunicipioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.PaisJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.RolJPA;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Colonia;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Direccion;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Estado;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Municipio;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Pais;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Rol;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        Converter<PaisJPA, Pais> paisConverter = context -> {
            PaisJPA source = context.getSource();
            if (source == null) {
                return null;
            }

            Pais pais = new Pais();
            pais.setIdPais(source.getIdPais());
            pais.setNombre(source.getNombre());
            return pais;
        };

        
        Converter<EstadoJPA, Estado> estadoConverter = context -> {
            EstadoJPA source = context.getSource();
            if (source == null) {
                return null;
            }

            Estado estado = new Estado();
            estado.setIdEstado(source.getIdEstado());
            estado.setNombre(source.getNombre());
            if (source.getPaisJPA() != null) {
                estado.setPais(paisConverter.convert(context.create(source.getPaisJPA(), Pais.class)));
            }
            return estado;
        };

        Converter<MunicipioJPA, Municipio> municipioConverter = context -> {
            MunicipioJPA source = context.getSource();
            if (source == null) {
                return null;
            }

            Municipio municipio = new Municipio();
            municipio.setIdMunicipio(source.getIdMunicipio());
            municipio.setNombre(source.getNombre());
            if (source.getEstadoJPA() != null) {
                municipio.setEstado(estadoConverter.convert(context.create(source.getEstadoJPA(), Estado.class)));
            }
            return municipio;
        };

        Converter<ColoniaJPA, Colonia> coloniaConverter = context -> {
            ColoniaJPA source = context.getSource();
            if (source == null) {
                return null;
            }

            Colonia colonia = new Colonia();
            colonia.setIdColonia(source.getIdColonia());
            colonia.setNombre(source.getNombre());
            colonia.setCodigoPostal(source.getCodigoPostal());
            if (source.getMunicipioJPA() != null) {
                colonia.setMunicipio(municipioConverter.convert(context.create(source.getMunicipioJPA(), Municipio.class)));
            }
            return colonia;
        };

        Converter<DireccionJPA, Direccion> direccionConverter = context -> {
            DireccionJPA source = context.getSource();
            if (source == null) {
                return null;
            }

            Direccion direccion = new Direccion();
            direccion.setIdDireccion(source.getIdDireccion());
            direccion.setCalle(source.getCalle());
            direccion.setNumeroInterior(source.getNumeroInterior());
            direccion.setNumeroExterior(source.getNumeroExterior());
            if (source.getColoniaJPA() != null) {
                direccion.setColonia(coloniaConverter.convert(context.create(source.getColoniaJPA(), Colonia.class)));
            }
            return direccion;
        };

        Converter<RolJPA, Rol> rolConverter = context -> {
            RolJPA source = context.getSource();
            if (source == null) {
                return null;
            }

            Rol rol = new Rol();
            rol.setIdRol(source.getIdRol());
            rol.setNombreRol(source.getNombre());
            return rol;
        };

        Converter<UsuarioJPA, Usuario> usuarioConverter = context -> {
            UsuarioJPA source = context.getSource();
            if (source == null) {
                return null;
            }

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(source.IdUsuario);
            usuario.setNombre(source.Nombre);
            usuario.setUserName(source.Username);
            usuario.setApellidoPaterno(source.ApellidoPaterno);
            usuario.setApellidoMaterno(source.ApellidoMaterno);
            usuario.setEmail(source.Email);
            usuario.setPassword(source.Password);
            usuario.setFechaNacimiento(source.FechaNacimiento);
            usuario.setSexo(source.Sexo);
            usuario.setTelefono(source.Telefono);
            usuario.setCelular(source.Celular);
            usuario.setCURP(source.CURP);

            if (source.getRol() != null) {
                usuario.setRol(rolConverter.convert(context.create(source.getRol(), Rol.class)));
            }

            if (source.getDireccionesJPA() != null && !source.getDireccionesJPA().isEmpty()) {
                ArrayList<Direccion> direcciones = source.getDireccionesJPA().stream()
                        .map(direccionJPA -> direccionConverter.convert(context.create(direccionJPA, Direccion.class)))
                        .collect(Collectors.toCollection(ArrayList::new));
                usuario.setDirecciones(direcciones);
            }

            return usuario;
        };

        modelMapper.addConverter(paisConverter, PaisJPA.class, Pais.class);
        modelMapper.addConverter(estadoConverter, EstadoJPA.class, Estado.class);
        modelMapper.addConverter(municipioConverter, MunicipioJPA.class, Municipio.class);
        modelMapper.addConverter(coloniaConverter, ColoniaJPA.class, Colonia.class);
        modelMapper.addConverter(direccionConverter, DireccionJPA.class, Direccion.class);
        modelMapper.addConverter(rolConverter, RolJPA.class, Rol.class);
        modelMapper.addConverter(usuarioConverter, UsuarioJPA.class, Usuario.class);

        return modelMapper;
    }
}
