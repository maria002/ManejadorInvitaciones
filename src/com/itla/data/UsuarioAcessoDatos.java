package com.itla.data;

import com.itla.modelo.PerfilUsuario;
import com.itla.modelo.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maria Elena, Katerina, samuel.
 */
public class UsuarioAcessoDatos {

    public static List<Usuario> seleccionarTodo() throws SQLException {
        Conexion.conectar();
        ResultSet rs = Conexion.st.executeQuery("SELECT * FROM Usuario");
        ArrayList<Usuario> usuario = new ArrayList<>();
        while (rs.next()) {
            usuario.add(new Usuario(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getString("nombre"), rs.getString("apellido"), rs.getString("cuenta"), "",
                    new PerfilUsuario(rs.getInt("Id_Perfil_Usuario"), true, "")));
        }
        Conexion.desconectar();
        return usuario;
    }

    public static Usuario seleccionarPorId(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("select * from Usuario where id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Usuario u = null;
        while (rs.next()) {
            u = new Usuario(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getString("nombre"), rs.getString("apellido"), rs.getString("cuenta"), "", 
                    new PerfilUsuario(rs.getInt("Id_Perfil_Usuario"), true, ""));
        }
        Conexion.desconectar();
        return u;
    }

    public static void insertar(Usuario usuario) throws SQLException  {
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("Insert into Usuario(Id, Nombre, Apellido, Cuenta, Clave, Activo, ID_PERFIL_USUARIO) values(sec_IdUsuarios, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCuenta());
            ps.setString(4, usuario.getClave());
            ps.setString(5, String.valueOf(Conexion.convertirBooleanAChar(usuario.isActivo())));
            ps.setInt(6, usuario.getPerfil().getId());
            Conexion.conn.setAutoCommit(false);
            ps.executeUpdate();
            Conexion.conn.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioAcessoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Conexion.conn.rollback();
            Conexion.conn.setAutoCommit(true);
            Conexion.desconectar();
        }
    }

    public static void modificar() throws SQLException {
        Conexion.conectar();
        Conexion.desconectar();
    }

    public static void eliminar() throws SQLException {
        Conexion.conectar();
        Conexion.desconectar();
    }
}
