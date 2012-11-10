package com.itla.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Evento extends Entidad {

    private String nombre;
    private Date fecha;
    private String ubicacion;
    public final static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    
    public Evento(){
        super();
    }
    public Evento(int id,boolean activo, String nombre, Date fecha, String ubicacion) {
        super(id, activo);
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Evento{" + "nombre=" + nombre + ", fecha=" + fecha + ", ubicacion=" + ubicacion + '}';
    }
    
}
