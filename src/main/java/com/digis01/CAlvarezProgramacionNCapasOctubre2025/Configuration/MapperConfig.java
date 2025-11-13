package com.digis01.CAlvarezProgramacionNCapasOctubre2025.Configuration;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.ColoniaJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.EstadoJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.MunicipioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.PaisJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.RolJPA;
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
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(new PropertyMap<UsuarioJPA, Usuario>() {
            @Override
            protected void configure() {
                map().setIdUsuario(source.IdUsuario);
                map().setNombre(source.Nombre);
                map().setUserName(source.Username);
                map().setApellidoPaterno(source.ApellidoPaterno);
                map().setApellidoMaterno(source.ApellidoMaterno);
                map().setEmail(source.Email);
                map().setPassword(source.Password);
                map().setFechaNacimiento(source.FechaNacimiento);
                map().setSexo(source.Sexo);
                map().setTelefono(source.Telefono);
                map().setCelular(source.Celular);
                map().setCURP(source.CURP);

//                map().setRol((Rol)source.getRol());
                using(ctx -> modelMapper.map(((UsuarioJPA) ctx.getSource()).getDireccionesJPA(), Direccion.class))
                        .map(source, destination.getDireccion());

            }
        });///Instancia objetos

        modelMapper.addMappings(new PropertyMap<RolJPA, Rol>() {
            @Override
            protected void configure() {
                map().setIdRol(source.getIdRol());
                map().setNombreRol(source.getNombre());
            }
        });

        modelMapper.addMappings(new PropertyMap<DireccionJPA, Direccion>() {
            @Override
            protected void configure() {
                map().setIdDireccion(source.getIdDireccion());
                map().setCalle(source.getCalle());
                map().setNumeroInterior(source.getNumeroInterior());
                map().setNumeroExterior(source.getNumeroExterior());
                using(ctx -> modelMapper.map(((DireccionJPA) ctx.getSource()).getColoniaJPA(), Colonia.class))
                        .map(source, destination.getColonia());

            }
        });

        modelMapper.addMappings(new PropertyMap<ColoniaJPA, Colonia>() {
            @Override
            protected void configure() {
                map().setIdColonia(source.getIdColonia());
                map().setNombre(source.getNombre());
                map().setCodigoPostal(source.getCodigoPostal());
                using(ctx -> modelMapper.map(((ColoniaJPA) ctx.getSource()).getMunicipioJPA(), Municipio.class))
                        .map(source, destination.getMunicipio());
            }
        });

        modelMapper.addMappings(new PropertyMap<MunicipioJPA, Municipio>() {
            @Override
            protected void configure() {
                map().setIdMunicipio(source.getIdMunicipio());
                map().setNombre(source.getNombre());
                using(ctx -> modelMapper.map(((MunicipioJPA) ctx.getSource()).getEstadoJPA(), Estado.class))
                        .map(source, destination.getEstado());
            }
        });

        modelMapper.addMappings(new PropertyMap<EstadoJPA, Estado>() {
            @Override
            protected void configure() {
                map().setIdEstado(source.getIdEstado());
                map().setNombre(source.getNombre());
                using(ctx -> modelMapper.map(((EstadoJPA) ctx.getSource()).getPaisJPA(), Pais.class))
                        .map(source, destination.getPais());
            }
        });

        modelMapper.addMappings(new PropertyMap<PaisJPA, Pais>() {
            @Override
            protected void configure() {
                map().setIdPais(source.getIdPais());
                map().setNombre(source.getNombre());
            }
        });

        return modelMapper;
    }

}
