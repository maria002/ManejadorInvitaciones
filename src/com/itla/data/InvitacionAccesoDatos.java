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

    public static List<Invitacion> seleccionarTodo() throws SQLException {
        Conexion.conectar();
        ResultSet rs = Conexion.st.executeQuery("SELECT * FROM invitacion");
        ArrayList<Invitacion> invitacion = new ArrayList<>();
        while (rs.next()) {
            invitacion.add(new Invitacion(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getDate("fecha_asistencia"), rs.getString("Razon_visita"),
                    EventoAccesoDatos.seleccionarPorId(rs.getInt("Id_evento")),
                    InvitadoAcessoDatos.seleccionarPorId(rs.getInt("Id_invitado")),
                    UsuarioAccesoDatos.seleccionarPorId(rs.getInt("Id_usuario"))));
        }
        Conexion.desconectar();
        return invitacion;
    }

    public static Invitacion seleccionarPorId(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("SELECT * FROM invitacion WHERE ID_INVITADO = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Invitacion invitacion = null;
        while (rs.next()) {
            invitacion = new Invitacion(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getDate("fecha_asistencia"), rs.getString("Razon_visita"),
                    EventoAccesoDatos.seleccionarPorId(rs.getInt("Id_evento")),
                    InvitadoAcessoDatos.seleccionarPorId(rs.getInt("Id_invitado")),
                    UsuarioAccesoDatos.seleccionarPorId(rs.getInt("Id_usuario")));
        }
        Conexion.desconectar();
        return invitacion;
    }

    public static List<Invitacion> seleccionarPorEventoId(int id) throws SQLException{
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("SELECT * FROM invitacion WHERE ID_EVENTO = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        ArrayList<Invitacion> invitaciones = new ArrayList<>();
        while (rs.next()) {
            invitaciones.add(new Invitacion(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getDate("fecha_asistencia"), rs.getString("Razon_visita"),
                    EventoAccesoDatos.seleccionarPorId(rs.getInt("Id_evento")),
                    InvitadoAcessoDatos.seleccionarPorId(rs.getInt("Id_invitado")),
                    UsuarioAccesoDatos.seleccionarPorId(rs.getInt("Id_usuario"))));
        }
        Conexion.desconectar();
        return invitaciones;
    }
    
    public static void insertar(Invitacion inv) throws SQLException {
        if (inv.getId() > 0) {
            modificar(inv);
            return;
        }
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("INSERT INTO Invitacion(ID_INVITACION, FECHA_ASISTENCIA, RAZON_VISITA, ID_EVENTO, ID_INVITADO, ACTIVO, ID_USUARIO) VALUES (sec_Id_invitacion.nextval, ?,?,?,?,?,?)");
            java.sql.Date fecha = null;
            if (inv.getFechaAsistencia() != null) {
                long tmp = inv.getFechaAsistencia().getTime();
                fecha = new java.sql.Date(tmp);
            }
            ps.setDate(1, fecha);
            ps.setString(2, inv.getRazonVisita());
            ps.setInt(3, inv.getEvento().getId());
            ps.setInt(4, inv.getInvitado().getId());
            ps.setString(5, String.valueOf(Conexion.convertirBooleanAChar(inv.isActivo())));
            ps.setInt(6, inv.getUsuario().getId());
            ps.executeUpdate();
        } finally {
            Conexion.desconectar();
        }
    }

    public static void modificar(Invitacion invitacion) throws SQLException {
        if (invitacion.getId() <= 0) {
            throw new IllegalArgumentException("No se puede modificar un registro con id 0");
        }
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("UPDATE invitacion SET NOMBRE = ?, FECHA_Asistencia = ?, Razon_visita = ?, ACTIVO = ?, id_evento= ?, id_invitado=?, id_usuario = ? WHERE ID_EVENTO = ?");
            ps.setDate(2, new java.sql.Date(invitacion.getFechaAsistencia().getTime()));
            ps.setString(3, invitacion.getRazonVisita());
            ps.setString(4, String.valueOf(Conexion.convertirBooleanAChar(invitacion.isActivo())));
            ps.setInt(5, invitacion.getEvento().getId());
            ps.setInt(6, invitacion.getInvitado().getId());
            ps.setInt(7, invitacion.getUsuario().getId());
            ps.executeUpdate();
        } finally {
            Conexion.desconectar();
        }
    }

    public static void eliminar(int id) throws SQLException {
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("DELETE FROM invitacion WHERE ID_INVITACION = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            Conexion.desconectar();
        }
    }
    
}