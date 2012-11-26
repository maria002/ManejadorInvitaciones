package com.itla.data;

import com.itla.modelo.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        if (usuario.getId() > 0) {
            modificar(usuario);
            return;
        }
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("Insert into Usuario(Id, Nombre, Apellido, Cuenta, Clave, Activo, ID_PERFIL_USUARIO) values(sec_IdUsuario.nextVal, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCuenta());
            ps.setString(4, usuario.getClave());
            ps.setString(5, String.valueOf(Conexion.convertirBooleanAChar(usuario.isActivo())));
            ps.setInt(6, usuario.getPerfilUsuario().getId());
            ps.executeUpdate();
        } finally {
            Conexion.desconectar();
        }
    }

    public static void modificar(Usuario usuario) throws SQLException {
        if (usuario.getId() <= 0) {
            throw new IllegalArgumentException("No se puede modificar un registro con id <= 0");
        }
        try {
            Conexion.conectar();
            String query = "UPDATE Usuario SET nombre = ?, Apellido = ?,  Activo = ?, Id_Perfil_Usuario = ? WHERE id = ?";
            if(usuario.getClave() != null && !usuario.getClave().isEmpty()){
                query = "UPDATE Usuario SET nombre = ?, Apellido = ?, Clave = ?, Activo = ?, Id_Perfil_Usuario = ? WHERE id = ?";
            }
            int idx = 0;
            PreparedStatement ps = Conexion.conn.prepareStatement(query);
            ps.setString(++idx, usuario.getNombre());
            ps.setString(++idx, usuario.getApellido());
            if(usuario.getClave() != null && !usuario.getClave().isEmpty()){
                ps.setString(++idx, usuario.getClave());
            }
            ps.setString(++idx, String.valueOf(Conexion.convertirBooleanAChar(usuario.isActivo())));
            ps.setInt(++idx, usuario.getPerfilUsuario().getId());
            ps.setInt(++idx, usuario.getId());
            ps.executeUpdate();
        } finally {
            Conexion.desconectar();
        }
    }

    public static void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("No se puede eliminar un registro con id <= 0");
        }
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("DELETE FROM Usuario WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            Conexion.desconectar();
        }
    }

    public static Usuario autenticar(String cuenta, String clave) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("select ID, NOMBRE, APELLIDO, CUENTA, ACTIVO, ID_PERFIL_USUARIO from Usuario where cuenta = ? AND clave = ?");
        ps.setString(1, cuenta);
        ps.setString(2, clave);
        ResultSet rs = ps.executeQuery();
        Usuario usuario = null;
        while (rs.next()) {
            usuario = new Usuario(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")), rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("Cuenta"), null, PerfilUsuarioAccesoDatos.SeleccionarPorId(rs.getInt("Id_Perfil_Usuario")));
        }
        Conexion.desconectar();
        return usuario;
    }
}
