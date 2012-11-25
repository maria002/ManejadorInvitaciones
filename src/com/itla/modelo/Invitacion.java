package com.itla.modelo;

import java.util.Date;

public class Invitacion extends Entidad {

    private Date fechaAsistencia;
    private String razonVisita;
    private Evento evento;
    private Invitado invitado;
    private Usuario usuario;

    public Invitacion() {
        super();
    }

    public Invitacion(int id, boolean activo, Date fechaAsistencia, String razonVisita, Evento evento, Invitado invitado, Usuario usuario) {
        super(id, activo);
        this.fechaAsistencia = fechaAsistencia;
        this.razonVisita = razonVisita;
        this.evento = evento;
        this.invitado = invitado;
        this.usuario = usuario;
    }
    
    public Date getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public String getRazonVisita() {
        return razonVisita;
    }

    public void setRazonVisita(String razonVisita) {
        this.razonVisita = razonVisita;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Invitado getInvitado() {
        return invitado;
    }

    public void setInvitado(Invitado invitado) {
        this.invitado = invitado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
