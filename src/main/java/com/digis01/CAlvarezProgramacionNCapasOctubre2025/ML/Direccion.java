package com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML;

public class Direccion {
    private int idDireccion;
    private String calle;
    private String numeroInterior;
    private String numeroExterior;
    public Colonia Colonia; // Relación con Colonia
    
    // Constructor vacío
    public Direccion() {
    }
    
    // Getters y Setters
    public int getIdDireccion() {
        return idDireccion;
    }
    
    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }
    
    public String getCalle() {
        return calle;
    }
    
    public void setCalle(String calle) {
        this.calle = calle;
    }
    
    public String getNumeroInterior() {
        return numeroInterior;
    }
    
    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }
    
    public String getNumeroExterior() {
        return numeroExterior;
    }
    
    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }
    
    
}