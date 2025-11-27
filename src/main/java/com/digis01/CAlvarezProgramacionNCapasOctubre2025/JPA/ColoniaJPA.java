
package com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "COLONIA")
public class ColoniaJPA {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcolonia")
    public int IdColonia;
    
    @Column(name = "nombre")
    public String Nombre;
    
    @Column(name = "codigopostal")
    public String CodigoPostal;
    
    @ManyToOne
    @JoinColumn (name = "idmunicipio")
    public MunicipioJPA MunicipioJPA;

    public int getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(int IdColonia) {
        this.IdColonia = IdColonia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }

    public MunicipioJPA getMunicipioJPA() {
        return MunicipioJPA;
    }

    public void setMunicipioJPA(MunicipioJPA MunicipioJPA) {
        this.MunicipioJPA = MunicipioJPA;
    }
   
    
    
    
}
