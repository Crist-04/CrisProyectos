
package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Usuario;

public interface IUsuarioJPA {
    
    Result GetAll();
    
    Result Add(Usuario usuario);
    
    Result GetById(int IdUsuario);
    
}
