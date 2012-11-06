package com.itla.modelo;

public class PerfilUsuario extends Entidad{

    private String nombre;

    public PerfilUsuario() {
        super();
    }

    public PerfilUsuario(int id, boolean activo, String nombre) {
        super(id, activo);
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "PerfilUsuario{" + "nombre=" + nombre + '}';
    }
    
}
