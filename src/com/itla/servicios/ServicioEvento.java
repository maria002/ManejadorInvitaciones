package com.itla.servicios;

import com.itla.data.EventoAccesoDatos;
import com.itla.modelo.Evento;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicioEvento {

    public ArrayList<Evento> seleccionarTodos() throws SQLException {
        return (ArrayList<Evento>) EventoAccesoDatos.SeleccionarTodo();
    }
    
    public Evento SeleccionarPorId(int id) throws SQLException {
        return EventoAccesoDatos.SeleccionarPorId(id);
    }

    public void insertar(Evento e) throws SQLException {
        EventoAccesoDatos.insertar(e);
    }

    public void modificar(Evento e) throws SQLException {
        EventoAccesoDatos.modificar(e);
    }
    
    public void eliminar(int id) throws SQLException{
        EventoAccesoDatos.eliminar(id);
    }
}
