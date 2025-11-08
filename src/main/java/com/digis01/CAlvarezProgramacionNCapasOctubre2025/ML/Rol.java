package com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML;

public class Rol {
    private int IdRol;
    private String NombreRol;
    
    public Rol() {
    }
    
    public Rol(int IdRol, String NombreRol) {
        this.IdRol = IdRol;
        this.NombreRol = NombreRol;
    }
    
    public int getIdRol() {
        return IdRol;
    }
    
    public void setIdRol(int IdRol) {
        this.IdRol = IdRol;
    }
    
    public String getNombreRol() {
        return NombreRol;
    }
    
    public void setNombreRol(String NombreRol) {
        this.NombreRol = NombreRol;
    }
}