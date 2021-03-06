package com.itla.data;

import com.itla.modelo.PerfilUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maria, Katerina, Samuel
 */
public class PerfilUsuarioAccesoDatos {

    public static List<PerfilUsuario> SeleccionarTodo() throws SQLException {
        Conexion.conectar();
        ResultSet rs = Conexion.st.executeQuery("SELECT * FROM perfil_usuario");
        ArrayList<PerfilUsuario> perfilUsuario = new ArrayList<>();
        while (rs.next()) {
            perfilUsuario.add(new PerfilUsuario(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getString("nombre")));
        }
        Conexion.desconectar();
        return perfilUsuario;
    }

    public static PerfilUsuario SeleccionarPorId(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("SELECT * FROM perfil_usuario WHERE ID = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        PerfilUsuario perfil = null;
        while (rs.next()) {
            perfil = new PerfilUsuario(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")), rs.getString("nombre"));
        }
        Conexion.desconectar();
        return perfil;
    }

    public static void insertar(PerfilUsuario perfil) throws SQLException {
        if (perfil.getId() > 0) {
            modificar(perfil);
            return;
        }

        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("INSERT INTO perfil_usuario(id, nombre, activo) VALUES (sec_Id_Perfil_Usuario.nextval, ?,?)");
        ps.setString(1, perfil.getNombre());
        ps.setString(2, String.valueOf(Conexion.convertirBooleanAChar(perfil.isActivo())));
        ps.executeUpdate();
        Conexion.desconectar();
    }

    public static void modificar(PerfilUsuario perfil) throws SQLException {
        if (perfil.getId() <= 0) {
            throw new IllegalArgumentException("No se puede modificar un registro con id 0");
        }
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("UPDATE perfil_usuario SET NOMBRE = ?, ACTIVO = ? WHERE ID = ?");
        ps.setString(1, perfil.getNombre());
        ps.setString(2, String.valueOf(Conexion.convertirBooleanAChar(perfil.isActivo())));
        ps.setInt(3, perfil.getId());
        ps.executeUpdate();
        Conexion.desconectar();
    }

    public static void eliminar(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("DELETE FROM perfil_usuario WHERE ID= ?");
        ps.setInt(1, id);
        Conexion.conn.setAutoCommit(false);
        ps.executeUpdate();
        Conexion.desconectar();
    }
}
