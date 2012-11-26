package com.itla.servicios;

import com.itla.data.EventoAccesoDatos;
import com.itla.modelo.Evento;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicioEvento {

    public ArrayList<Evento> seleccionarTodos() throws SQLException {
        return (ArrayList<Evento>) EventoAccesoDatos.seleccionarTodo();
    }

    public Evento SeleccionarPorId(int id) throws SQLException {
        return EventoAccesoDatos.seleccionarPorId(id);
    }

    public void insertar(Evento e) throws SQLException {
        EventoAccesoDatos.insertar(e);
    }

    public void modificar(Evento e) throws SQLException {
        EventoAccesoDatos.modificar(e);
    }

    public void eliminar(int id) throws SQLException {
        EventoAccesoDatos.eliminar(id);
    }

    public ArrayList<Evento> seleccionarEventosDeHoy() throws SQLException {
        return (ArrayList<Evento>) EventoAccesoDatos.seleccionarEventosDeHoy();
    }

    public ArrayList<Evento> seleccionarEventosProximos() throws SQLException {
        return (ArrayList<Evento>) EventoAccesoDatos.seleccionarEventosProximos();
    }
}
