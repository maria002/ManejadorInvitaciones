package com.itla.servicios;

import com.itla.data.InvitacionAccesoDatos;
import com.itla.modelo.Invitacion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Katerina Hernandez
 */
public class ServicioInvitacion {

    public ArrayList<Invitacion> seleccionarTodos() throws SQLException {
        return (ArrayList<Invitacion>) InvitacionAccesoDatos.seleccionarTodo();
    }

    public Invitacion seleccionarPorId(int id) throws SQLException {
        return InvitacionAccesoDatos.seleccionarPorId(id);
    }

    public void insertar(Invitacion invitacion) throws SQLException {
        InvitacionAccesoDatos.insertar(invitacion);
    }

    public void modificar(Invitacion invitacion) throws SQLException {
        InvitacionAccesoDatos.modificar(invitacion);
    }

    public void eliminar(int id) throws SQLException {
        InvitacionAccesoDatos.eliminar(id);
    }
    
    public List<Invitacion> seleccionarPorEventoId(int id) throws SQLException{
        return InvitacionAccesoDatos.seleccionarPorEventoId(id);
    }
}
