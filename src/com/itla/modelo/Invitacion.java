package com.itla.modelo;

import java.util.Date;

public class Invitacion {

    private Date fechaAsistencia;
    private String razonVisita;

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
}
