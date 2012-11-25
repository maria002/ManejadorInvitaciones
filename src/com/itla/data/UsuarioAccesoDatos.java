package com.itla.data;

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
public class UsuarioAccesoDatos {

    public static List<Usuario> seleccionarTodo() throws SQLException {
        Conexion.conectar();
        ResultSet rs = Conexion.st.executeQuery("SELECT ID, NOMBRE, APELLIDO, CUENTA, ACTIVO, ID_PERFIL_USUARIO FROM Usuario");
        ArrayList<Usuario> usuario = new ArrayList<>();
        while (rs.next()) {
            usuario.add(new Usuario(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")), rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("Cuenta"), null, PerfilUsuarioAccesoDatos.SeleccionarPorId(rs.getInt("Id_Perfil_Usuario"))));
        }
        Conexion.desconectar();
        return usuario;
    }

    public static Usuario seleccionarPorId(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("select ID, NOMBRE, APELLIDO, CUENTA, ACTIVO, ID_PERFIL_USUARIO from Usuario where id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Usuario usuario = null;
        while (rs.next()) {
            usuario = new Usuario(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")), rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("Cuenta"), null, PerfilUsuarioAccesoDatos.SeleccionarPorId(rs.getInt("Id_Perfil_Usuario")));
        }
        Conexion.desconectar();
        return usuario;
    }

    public static void insertar(Usuario usuario) throws SQLException {
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("Insert into Usuario(Id, Nombre, Apellido, Cuenta, Clave, Activo, ID_PERFIL_USUARIO) values(sec_IdUsuario.nextVal, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCuenta());
            ps.setString(4, usuario.getClave());
            ps.setString(5, String.valueOf(Conexion.convertirBooleanAChar(usuario.isActivo())));
            ps.setInt(6, usuario.getPerfilUsuario().getId());
            Conexion.conn.setAutoCommit(false);
            ps.executeUpdate();
            Conexion.conn.commit();
        } catch (SQLException ex) {
            Conexion.conn.rollback();
            Logger.getLogger(UsuarioAccesoDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.conn.setAutoCommit(true);
            Conexion.desconectar();
        }
    }

    public static void modificar(Usuario usuario) throws SQLException {
        try {
            if (usuario.getId() <= 0) {
                throw new IllegalArgumentException("No se puede modificar un registro con id <= 0");
            }
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("UPDATE Usuario SET nombre = ?, Apellido = ?, Cuenta = ?, Clave = ?, Activo = ?, Id_Perfil_Usuario = ? WHERE id = ?");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCuenta());
            ps.setString(4, usuario.getClave());
            ps.setString(5, String.valueOf(Conexion.convertirBooleanAChar(usuario.isActivo())));
            ps.setInt(6, usuario.getPerfilUsuario().getId());
            ps.setInt(7, usuario.getId());
            Conexion.conn.setAutoCommit(false);
            ps.executeUpdate();
            Conexion.conn.commit();
        } catch (SQLException ex) {
            Conexion.conn.rollback();
            Logger.getLogger(UsuarioAccesoDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.conn.setAutoCommit(true);
            Conexion.desconectar();
        }
    }

    public static void eliminar(int id) throws SQLException {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("No se puede eliminar un registro con id <= 0");
            }
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("DELETE FROM Usuario WHERE id = ?");
            ps.setInt(1, id);
            Conexion.conn.setAutoCommit(false);
            ps.executeUpdate();
            Conexion.conn.commit();
        } catch (SQLException ex) {
            Conexion.conn.rollback();
            Logger.getLogger(UsuarioAccesoDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.conn.setAutoCommit(true);
            Conexion.desconectar();
        }

    }
}
