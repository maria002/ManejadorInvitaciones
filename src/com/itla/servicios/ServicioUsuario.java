package com.itla.servicios;

import com.itla.data.UsuarioAccesoDatos;
import com.itla.modelo.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author Maria Elena, Katerina y Samuel
 */
public class ServicioUsuario {

    public ArrayList<Usuario> seleccionarTodos() throws SQLException {
        return (ArrayList<Usuario>) UsuarioAccesoDatos.seleccionarTodo();
    }
    
    public Usuario seleccionarPorId(int id) throws SQLException {
        return UsuarioAccesoDatos.seleccionarPorId(id);
    }

    public void insertar(Usuario usuario) throws SQLException {
        UsuarioAccesoDatos.insertar(usuario);
    }

    public void modificar(Usuario usuario) throws SQLException {
        UsuarioAccesoDatos.modificar(usuario);
    }
    
    public void eliminar(int id) throws SQLException{
        UsuarioAccesoDatos.eliminar(id);
    }
    public Usuario autenticar(String cuenta, String clave) throws SQLException {
        return UsuarioAccesoDatos.autenticar(cuenta, clave);
    }
 }
