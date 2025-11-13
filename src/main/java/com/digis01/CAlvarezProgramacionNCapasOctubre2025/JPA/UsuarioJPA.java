package com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USUARIO")
public class UsuarioJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    public int IdUsuario;

    @Column(name = "nombre")
    public String Nombre;

    @Column(name = "username")
    public String Username;

    @Column(name = "apellidopaterno")
    public String ApellidoPaterno;

    @Column(name = "apellidomaterno")
    public String ApellidoMaterno;

    @Column(name = "email")
    public String Email;

    @Column(name = "password")
    public String Password;

    @Column(name = "fechanacimiento")
    public Date FechaNacimiento;

    @Column(name = "sexo")
    public String Sexo;

    @Column(name = "telefono")
    public String Telefono;

    @Column(name = "celular")
    public String Celular;

    @Column(name = "curp")
    public String CURP;
    
    @Column(name = "imagen")
    public String Imagen;

    @ManyToOne
    @JoinColumn(name = "idrol")
    private RolJPA rol;

    @OneToMany(mappedBy = "UsuarioJPA", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DireccionJPA> DireccionesJPA = new ArrayList<>();
    
    
    public RolJPA getRol() {
    return rol;
}

public void setRol(RolJPA rol) {
    this.rol = rol;
}

public List<DireccionJPA> getDireccionesJPA() {
    return DireccionesJPA;
}

public void setDireccionesJPA(List<DireccionJPA> direccionesJPA) {
    this.DireccionesJPA = direccionesJPA;
}

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }




}
