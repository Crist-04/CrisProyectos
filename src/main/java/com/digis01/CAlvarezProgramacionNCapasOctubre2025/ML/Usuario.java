package com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {

    private int idUsuario;
    //"^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(\\s[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$"
    @NotNull(message = "El nombre no puede ser nulo ")
    @NotEmpty(message = "El nombre no puede estar vacio")
    @NotBlank(message = "Tampoco puedes dejar espacios ")
    @Size(min = 2, max = 17, message = "entre 2 y 17 caracteres")
    private String nombre;

    @NotNull(message = "El apellido paterno No puede ser nulo bro")
    @NotEmpty(message = "El apellido paterno no puede estar vacio")
    @NotBlank(message = "Tampoco puedes dejar espacios ")
    @Size(min = 2, max = 17, message = "entre 2 y 17 caracteres")
    private String apellidoPaterno;

    @NotNull(message = "El apellido materno No puede ser nulo bro")
    @NotEmpty(message = "El apellido materno no puede estar vacio")
    @NotBlank(message = "Tampoco puedes dejar espacios ")
    @Size(min = 2, max = 17, message = "entre 2 y 17 caracteres")
    private String apellidoMaterno;

    @NotBlank(message = "El username no puede estar vacío")
    @Size(min = 3, max = 8, message = "El username debe tener entre 3 y 20 caracteres")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[_-])[a-zA-Z0-9_-]+$", message = "El username debe contener al menos: 1 letra, 1 número y 1 guion (3-20 caracteres)")
    private String userName;

    @NotNull(message = "El correo no debe ser nulo")
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El formato del correo no esta bien")
    private String Email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$", message = "Debe tener al menos una mayúscula, 1 número, 1 carácter especial")
    private String Password;

    @NotNull(message = "el celular no debe ser nulo")
    @NotBlank(message = "el celular no debe estar vacio")
    @Pattern(regexp = "^\\+?([0-9]{2,3})?[0-9]{10}$", message = "Ingresa correctamente tu numero de celular")
    private String Celular;

    @NotNull(message = "el telefono no debe ser nulo")
    @NotBlank(message = "el telefono no debe estar vacio")
    @Pattern(regexp = "^\\+?([0-9]{2,3})?[0-9]{10}$", message = "Ingresa correctamente tu numero de telefono")
    private String telefono;

    @NotNull(message = "El CURP no puede ser nulo")
    @NotBlank(message = "El CURP no puede estar vacío")
    @Size(min = 18, max = 18, message = "El CURP debe tener exactamente 18 caracteres")
    @Pattern(regexp = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]$", message = "El CURP no tiene el formato válido")
    private String CURP;

    private String Sexo;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento no puede ser mayor a la fecha actual")
    private Date fechaNacimiento;

    private ArrayList<Direccion> direcciones;
    private Direccion direccion;
    public Rol rol;
    private String Imagen;

    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(ArrayList<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

}
