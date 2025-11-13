package com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML;

public class Estado {
    
    private int IdEstado;
    private String Nombre;
    public Pais Pais;
    
    public Estado(){}
    
    // Getters y Setters
    public int getIdEstado() {
        return IdEstado;
    }
    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Pais getPais() {
        return Pais;
    }

    public void setPais(Pais Pais) {
        this.Pais = Pais;
    }
    
    
}
