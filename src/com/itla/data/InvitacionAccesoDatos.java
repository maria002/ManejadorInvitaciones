package com.itla.data;

import com.itla.modelo.Invitacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Katerina Hernandez
 */
public class InvitacionAccesoDatos {

    public static List<Invitacion> SeleccionarTodo() throws SQLException {
        Conexion.conectar();
        ResultSet rs = Conexion.st.executeQuery("SELECT * FROM evento");
        ArrayList<Invitacion> invitacion = new ArrayList<>();
        while (rs.next()) {
            invitacion.add(new Invitacion(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getDate("fecha_asistencia"), rs.getString("Razon_visita"),
                    EventoAccesoDatos.SeleccionarPorId(rs.getInt("Id_evento")),
                    InvitadoAcessoDatos.SeleccionarPorId(rs.getInt("Id_invitado")),
                    UsuarioAccesoDatos.seleccionarPorId(rs.getInt("Id_usuario"))));
        }
        Conexion.desconectar();
        return invitacion;
    }

    public static Invitacion SeleccionarPorId(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("SELECT * FROM invitado WHERE ID_INVITADO = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Invitacion invitacion = null;
        while (rs.next()) {
            invitacion = new Invitacion(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getDate("fecha_asistencia"), rs.getString("Razon_visita"),
                    EventoAccesoDatos.SeleccionarPorId(rs.getInt("Id_evento")),
                    InvitadoAcessoDatos.SeleccionarPorId(rs.getInt("Id_invitado")),
                    UsuarioAccesoDatos.seleccionarPorId(rs.getInt("Id_usuario")));
        }
        Conexion.desconectar();
        return invitacion;
    }

    public static void insertar(Invitacion inv) throws SQLException {
        if (inv.getId() > 0) {
            modificar(inv);
            return;
        }
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("INSERT INTO Invitacion VALUES (sec_Id_invitacion.nextval, ?,?,?,?)");
        ps.setInt(1, inv.getId());
        ps.setDate(2, new java.sql.Date(inv.getFechaAsistencia().getTime()));
        ps.setString(3, String.valueOf(Conexion.convertirBooleanAChar(inv.isActivo())));
        ps.setString(4, inv.getRazonVisita());
        ps.setInt(5, inv.getEvento().getId());
        ps.setInt(6, inv.getInvitado().getId());
        ps.setInt(7, inv.getUsuario().getId());
        ps.executeUpdate();
        Conexion.desconectar();
    }

    public static void modificar(Invitacion invitacion) throws SQLException {
        if (invitacion.getId() <= 0) {
            throw new IllegalArgumentException("No se puede modificar un registro con id 0");
        }
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("UPDATE invitado SET NOMBRE = ?, FECHA_Asistencia = ?, Razon_visita = ?, ACTIVO = ?, id_evento= ?, id_invitado=?, id_usuario = ? WHERE ID_EVENTO = ?");
        ps.setDate(2, new java.sql.Date(invitacion.getFechaAsistencia().getTime()));
        ps.setString(3, invitacion.getRazonVisita());
        ps.setString(4, String.valueOf(Conexion.convertirBooleanAChar(invitacion.isActivo())));
        ps.setInt(5, invitacion.getEvento().getId());
        ps.setInt(6, invitacion.getInvitado().getId());
        ps.setInt(7, invitacion.getUsuario().getId());
        ps.executeUpdate();
        Conexion.desconectar();
    }

    public static void eliminar(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("DELETE FROM invitacion WHERE ID_INVITACION = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        Conexion.desconectar();
    }
}