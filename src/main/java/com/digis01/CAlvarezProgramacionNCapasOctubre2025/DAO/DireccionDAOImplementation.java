package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Direccion;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result UpdateDireccion(Direccion direccion, int idUsuario) {
        return jdbcTemplate.execute("{CALL DireccionUpdate (?,?,?,?,?,?)}",
                (CallableStatementCallback<Result>) callableStatement -> {
                    Result result = new Result();
                    try {
                        callableStatement.setInt(1, direccion.getIdDireccion());
                        callableStatement.setString(2, direccion.getCalle());
                        callableStatement.setString(3, direccion.getNumeroInterior());
                        callableStatement.setString(4, direccion.getNumeroExterior());

                        callableStatement.setInt(5, direccion.getColonia().getIdColonia());

                        callableStatement.setInt(6, idUsuario);

                        int rowAffected = callableStatement.executeUpdate();

                        if (rowAffected > 0) {
                            result.correct = true;
                            result.errorMessage = "Dirección actualizada correctamente";
                        } else {
                            result.correct = false;
                            result.errorMessage = "No se pudo actualizar la dirección";
                        }
                    } catch (Exception ex) {
                        result.correct = false;
                        result.errorMessage = "Error al actualizar dirección: " + ex.getMessage();
                        result.ex = ex;
//                    ex.printStackTrace(); // Para debugging
                    }

                    return result;
                });
    }

    @Override
    public Result AddDireccion(Direccion direccion, int idUsuario) {
        return jdbcTemplate.execute("{CALL DireccionAdd (?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try {
                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroInterior());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setInt(4, direccion.getColonia().getIdColonia());
                callableStatement.setInt(5, idUsuario);

                int rowAffected = callableStatement.executeUpdate();

                if (rowAffected > 0) {
                    result.correct = true;
                    result.errorMessage = "Dirección agregada correctamente";
                } else {
                    result.correct = false;
                    result.errorMessage = "No se pudo agregar la dirección";
                }
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = "Error al agregar dirección: " + ex.getMessage();
                result.ex = ex;
            }
            return result;
        });
    }

    @Override
    public Result DeleteDireccion(int idDireccion) { 
        return jdbcTemplate.execute("{CALL DireccionDelelete(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {
                callableStatement.setInt(1, idDireccion); 

                int rowAffected = callableStatement.executeUpdate();

                if (rowAffected > 0) {
                    result.correct = true;
                    result.errorMessage = "Dirección eliminada correctamente";
                } else {
                    result.correct = false;
                    result.errorMessage = "No se pudo eliminar la dirección. Es posible que el ID no exista.";
                }

            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = "Error al eliminar dirección: " + ex.getMessage();
                result.ex = ex;
            }
            return result;
        });

    }

}
