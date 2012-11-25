package com.itla.servicios;

import com.itla.data.InvitadoAcessoDatos;
import com.itla.modelo.Invitado;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Dawlin
 */
public class ServicioInvitado {
    public ArrayList<Invitado> seleccionarTodos() throws SQLException {
        return (ArrayList<Invitado>) InvitadoAcessoDatos.seleccionarTodo();
    }
    
    public Invitado SeleccionarPorId(int id) throws SQLException {
        return InvitadoAcessoDatos.SeleccionarPorId(id);
    }

    public void insertar(Invitado e) throws SQLException {
        InvitadoAcessoDatos.insertar(e);
    }

    public void modificar(Invitado e) throws SQLException {
        InvitadoAcessoDatos.modificar(e);
    }
    
    public void eliminar(int id) throws SQLException{
        InvitadoAcessoDatos.eliminar(id);
    }
    public ArrayList<Invitado> buscarInvitdados (String nombre, String apellido) throws SQLException{
        return (ArrayList<Invitado>) InvitadoAcessoDatos.SeleccionarPorNombreApellido(nombre, apellido);
    }
}
