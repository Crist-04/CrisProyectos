package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Colonia;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Direccion;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Estado;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Municipio;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Rol;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML.Usuario;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = jdbcTemplate.execute("{CALL UsuarioDireccionGetAll(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();
            try {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultSP.objects = new ArrayList<>();

                int idUsuario = 0;

                while (resultSet.next()) {
                    idUsuario = resultSet.getInt("IdUsuario");

                    if (!resultSP.objects.isEmpty() && idUsuario == ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1))).getIdUsuario()) {
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                        Usuario usuario = ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1)));
                        usuario.getDirecciones().add(direccion);

                    } else {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setUserName(resultSet.getString("UserName"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.setCelular(resultSet.getString("Celular"));

                        usuario.setImagen(resultSet.getString("Imagen"));

                        Rol rol = new Rol();
                        rol.setIdRol(resultSet.getInt("IdRol"));
                        rol.setNombreRol(resultSet.getString("NombreRol"));
                        usuario.setRol(rol);

                        usuario.setDirecciones(new ArrayList<>());

                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                        usuario.getDirecciones().add(direccion);

                        resultSP.objects.add(usuario);
                    }
                }
                resultSP.correct = true;
            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;
        });

        return result;
    }

    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();
        result.correct = jdbcTemplate.execute("{CALL UsuariosAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                (CallableStatementCallback<Boolean>) callableStatement -> {

                    callableStatement.setString(1, usuario.getNombre());
                    callableStatement.setString(2, usuario.getApellidoPaterno());
                    callableStatement.setString(3, usuario.getApellidoMaterno());
                    callableStatement.setString(4, usuario.getUserName());
                    callableStatement.setString(5, usuario.getEmail());
                    callableStatement.setString(6, usuario.getPassword());

                    java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
                    callableStatement.setDate(7, sqlDate);

                    callableStatement.setString(8, usuario.getSexo());
                    callableStatement.setString(9, usuario.getTelefono());
                    callableStatement.setString(10, usuario.getCelular());
                    callableStatement.setString(11, usuario.getCURP());
                    callableStatement.setInt(12, usuario.getRol().getIdRol());

                    callableStatement.setString(13, usuario.getDirecciones().get(0).getCalle());
                    callableStatement.setString(14, usuario.getDirecciones().get(0).getNumeroInterior());
                    callableStatement.setString(15, usuario.getDirecciones().get(0).getNumeroExterior());
                    callableStatement.setInt(16, usuario.getDirecciones().get(0).getColonia().getIdColonia());

                    callableStatement.setString(17, usuario.getImagen());

                    callableStatement.execute();

                    return true;
                });
        return result;
    }

    @Override

    public Result GetById(int IdUsuario) {
        Result result = jdbcTemplate.execute("{CALL UsuariosGetById(?, ?)}",
                (CallableStatementCallback<Result>) callableStatement -> {

                    Result resultSP = new Result();

                    try {
                        callableStatement.setInt(1, IdUsuario);
                        callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        Usuario usuario = null;

                        while (resultSet.next()) {

                            if (usuario == null) {
                                usuario = new Usuario();
                                usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                                usuario.setUserName(resultSet.getString("UserName"));
                                usuario.setNombre(resultSet.getString("NombreUsuario"));
                                usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuario.setEmail(resultSet.getString("Email"));
                                usuario.setPassword(resultSet.getString("Password"));
                                usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                                usuario.setSexo(resultSet.getString("Sexo"));
                                usuario.setTelefono(resultSet.getString("Telefono"));
                                usuario.setCelular(resultSet.getString("Celular"));
                                usuario.setCURP(resultSet.getString("CURP"));
                                usuario.setImagen(resultSet.getString("Imagen"));

                                Rol rol = new Rol();

                                rol.setIdRol(resultSet.getInt("IdRol"));

                                rol.setNombreRol(resultSet.getString("NombreRol"));

                                usuario.setRol(rol);

                                usuario.setDirecciones(new ArrayList<>());

                            }

                            // Agregar direcciones si existen
                            if (resultSet.getObject("IdDireccion") != null) {
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                                Estado estado = new Estado();
                                estado.setIdEstado(resultSet.getInt("IdEstado"));
                                estado.setNombre(resultSet.getString("NombreEstado"));

                                
                                Municipio municipio = new Municipio();
                                municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                municipio.setEstado(estado);  // Asignar el Estado al Municipio

                                
                                Colonia colonia = new Colonia();
                                colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                colonia.setNombre(resultSet.getString("NombreColonia"));
                                colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                colonia.setMunicipio(municipio);  // Asignar el Municipio a la Colonia

                                
                                direccion.setColonia(colonia);

                                usuario.getDirecciones().add(direccion);

                            }

                        }

                        if (usuario != null) {

                            resultSP.object = usuario;
                            resultSP.correct = true;

                        }

                    } catch (Exception ex) {
                        resultSP.correct = false;
                        resultSP.errorMessage = ex.getLocalizedMessage();
                        resultSP.ex = ex;

                    }

                    return resultSP;

                });

        return result;

    }

    @Override
    public Result Update(Usuario usuario) {
        return jdbcTemplate.execute("{CALL UsuariosUpdate (?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setString(5, usuario.getEmail());
                callableStatement.setString(6, usuario.getPassword());
                java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
                callableStatement.setDate(7, sqlDate);
                callableStatement.setString(8, usuario.getSexo());
                callableStatement.setString(9, usuario.getTelefono());
                callableStatement.setString(10, usuario.getCelular());
                callableStatement.setString(11, usuario.getCURP());

                int rowAffected = callableStatement.executeUpdate();

                result.correct = true;

            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

            return result;
        });

    }

    @Override
    public Result GetAllDinamico(String nombre, String aPaterno, String aMaterno, int idRol) {
        Result result = jdbcTemplate.execute("{CALL UsuarioGetAllDinamico(?,?,?,?,?)}",
                (CallableStatementCallback<Result>) callableStatement -> {

                    Result resultSP = new Result();

                    try {
                        callableStatement.setString(1, nombre);
                        callableStatement.setString(2, aPaterno);
                        callableStatement.setString(3, aMaterno);
                        callableStatement.setInt(4, idRol);
                        callableStatement.registerOutParameter(5, Types.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                        resultSP.objects = new ArrayList<>();

                        int idUsuario = 0;

                        while (resultSet.next()) {
                            idUsuario = resultSet.getInt("IdUsuario");

                            if (!resultSP.objects.isEmpty() && idUsuario == ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1))).getIdUsuario()) {
                                Direccion direccion = new Direccion();
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                                Usuario usuario = ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1)));
                                usuario.getDirecciones().add(direccion);

                            } else {
                                Usuario usuario = new Usuario();
                                usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                                usuario.setNombre(resultSet.getString("NombreUsuario"));
                                usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuario.setUserName(resultSet.getString("UserName"));
                                usuario.setEmail(resultSet.getString("Email"));
                                usuario.setTelefono(resultSet.getString("Telefono"));
                                usuario.setCelular(resultSet.getString("Celular"));
                                usuario.setImagen(resultSet.getString("Imagen"));

                                Rol rol = new Rol();
                                rol.setIdRol(resultSet.getInt("IdRol"));
                                rol.setNombreRol(resultSet.getString("NombreRol"));
                                usuario.setRol(rol);

                                usuario.setDirecciones(new ArrayList<>());

                                Direccion direccion = new Direccion();
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                                usuario.getDirecciones().add(direccion);

                                resultSP.objects.add(usuario);
                            }
                        }
                        resultSP.correct = true;
                    } catch (Exception ex) {
                        resultSP.correct = false;
                        resultSP.errorMessage = ex.getLocalizedMessage();
                        resultSP.ex = ex;
                    }
                    return resultSP;
                });

        return result;
    }

}
