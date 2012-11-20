package com.itla.servicios;

import com.itla.data.PerfilUsuarioAccesoDatos;
import com.itla.modelo.PerfilUsuario;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicioPerfilUsuario {

    public ArrayList<PerfilUsuario> seleccionarTodos() throws SQLException {
        return (ArrayList<PerfilUsuario>) PerfilUsuarioAccesoDatos.SeleccionarTodo();
    }
    
    public PerfilUsuario SeleccionarPorId(int id) throws SQLException {
        return PerfilUsuarioAccesoDatos.SeleccionarPorId(id);
    }

    public void insertar(PerfilUsuario e) throws SQLException {
        PerfilUsuarioAccesoDatos.insertar(e);
    }

    public void modificar(PerfilUsuario e) throws SQLException {
        PerfilUsuarioAccesoDatos.modificar(e);
    }
    
    public void eliminar(int id) throws SQLException{
        PerfilUsuarioAccesoDatos.eliminar(id);
    }
}
