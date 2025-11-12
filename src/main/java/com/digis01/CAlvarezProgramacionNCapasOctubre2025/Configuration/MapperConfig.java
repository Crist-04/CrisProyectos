
package com.digis01.CAlvarezProgramacionNCapasOctubre2025.Configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Usuario;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
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
                
            }
        });
        
        return modelMapper;
    }
    
}
