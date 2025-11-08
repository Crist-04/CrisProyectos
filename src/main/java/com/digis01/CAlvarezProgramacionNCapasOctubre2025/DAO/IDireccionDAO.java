
package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Direccion;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;

public interface IDireccionDAO {
    
    public Result UpdateDireccion(Direccion direccion, int idUsuario);
    
    public Result AddDireccion(Direccion direccion, int idUsuario);
    
    public Result DeleteDireccion(int idDireccion);
    
    
}
