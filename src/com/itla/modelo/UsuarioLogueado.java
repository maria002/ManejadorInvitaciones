package com.itla.modelo;

/**
 *
 * @author
 */
public class UsuarioLogueado extends Entidad {

    private String nombre;
    private String cuenta;
    private PerfilUsuario perfilUsuario;

    public UsuarioLogueado() {
        super();
    }

    public UsuarioLogueado(int id, boolean activo, String nombre, String apellido, String cuenta, String clave, PerfilUsuario perfilUsuario) {
        super(id, activo);
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.perfilUsuario = perfilUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    public PerfilUsuario getPerfilUsuario() {
        return perfilUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", cuenta=" + cuenta + '}';
    }
}
