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

}
