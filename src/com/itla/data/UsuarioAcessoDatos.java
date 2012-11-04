package com.itla.data;

import com.itla.modelo.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maria Elena
 */
public class UsuarioAcessoDatos {
    
    public static List<Usuario> seleccionarTodo() throws SQLException{
        Conexion.conectar();
        ResultSet rs = Conexion.st.executeQuery("SELECT * FROM Usuario");
        ArrayList<Usuario> usuarios = new ArrayList<>();
        while(rs.next()){
            usuarios.add(new Usuario(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")), 
                    rs.getString("nombre"), rs.getString("apellido"), rs.getString("cuenta"), ""));
        }
        Conexion.desconectar();
        return usuarios;
    }
    
}
