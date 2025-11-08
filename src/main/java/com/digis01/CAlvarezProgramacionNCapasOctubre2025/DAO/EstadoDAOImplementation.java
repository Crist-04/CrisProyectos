package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Estado;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetByIdPais(int idPais) {
        return jdbcTemplate.execute("{CALL GetEstadoByIdPais(?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR); 
                callableStatement.setInt(2, idPais);                         
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1); 
                result.objects = new ArrayList<>();
                while (resultSet.next()) {
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IDESTADO"));
                    estado.setNombre(resultSet.getString("NOMBRE"));
                    result.objects.add(estado);
                }
                result.correct = true;
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
                result.objects = null;
            }
            return result;
        });
    }

}
