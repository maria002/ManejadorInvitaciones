package com.itla.modelo;

public abstract class Entidad {

    protected int id;
    protected boolean activo;

    public Entidad() {
        activo = true;
    }

    public Entidad(int id, boolean activo) {
        this.id = id;
        this.activo = activo;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
   
}
