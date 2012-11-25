package com.itla.modelo;

public class Usuario extends Entidad{

    private String nombre;
    private String apellido;
    private String cuenta;
    private String clave;
    private PerfilUsuario perfilUsuario;
    
    public Usuario() {
        super();
    }

    public Usuario(int id, boolean activo, String nombre, String apellido, String cuenta, String clave, PerfilUsuario perfilUsuario) {
        super(id, activo);
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuenta = cuenta;
        this.clave = clave;
        this.perfilUsuario = perfilUsuario;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }
    
    public PerfilUsuario getPerfilUsuario() {
        return perfilUsuario;
    }
    
    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", apellido=" + apellido + ", cuenta=" + cuenta + ", clave=" + clave + perfilUsuario.toString() + '}';
    }
    
}
