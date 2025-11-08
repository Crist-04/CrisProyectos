
package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Usuario;
import java.util.List;

public interface IUsuarioDAO {
    
    Result GetAll();
    
    Result Add(Usuario usuario);

    Result GetById(int IdUsuario);
    
    Result Update (Usuario usuario);
    
    Result GetAllDinamico(String nombre, String aPaterno, String aMaterno, int idRol);
    
    Result AddAll(List<Usuario> usuarios);
    
    
}
