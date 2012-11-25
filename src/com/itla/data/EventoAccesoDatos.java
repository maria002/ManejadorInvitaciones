package com.itla.data;

import com.itla.modelo.Evento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Samuel Pena
 */
public class EventoAccesoDatos {

    public static List<Evento> SeleccionarTodo() throws SQLException {
        Conexion.conectar();
        ResultSet rs = Conexion.st.executeQuery("SELECT * FROM evento");
        ArrayList<Evento> evento = new ArrayList<>();
        while (rs.next()) {
            evento.add(new Evento(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getString("nombre"), rs.getDate("fecha"), rs.getString("ubicacion")));
        }
        Conexion.desconectar();
        return evento;
    }

    public static Evento SeleccionarPorId(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("SELECT * FROM evento WHERE ID_EVENTO = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Evento evento = null;
        while (rs.next()) {
            evento = new Evento(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getString("nombre"), rs.getDate("fecha"), rs.getString("ubicacion"));
        }
        Conexion.desconectar();
        return evento;
    }

    public static void insertar(Evento event) throws SQLException {
        if (event.getId() > 0) {
            modificar(event);
            return;
        }
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("INSERT INTO Evento VALUES (SEC_ID_EVENTO.nextval, ?,?,?,?)");
        ps.setString(1, event.getNombre());
        ps.setDate(2, new java.sql.Date(event.getFecha().getTime()));
        ps.setString(3, String.valueOf(Conexion.convertirBooleanAChar(event.isActivo())));
        ps.setString(4, event.getUbicacion());
        ps.executeUpdate();
        Conexion.desconectar();
    }

    public static void modificar(Evento event) throws SQLException {
        if (event.getId() <= 0) {
            throw new IllegalArgumentException("No se puede modificar un registro con id 0");
        }
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("UPDATE evento SET NOMBRE = ?, FECHA = ?, UBICACION = ?, ACTIVO = ? WHERE ID_EVENTO = ?");
        ps.setString(1, event.getNombre());
        ps.setDate(2, new java.sql.Date(event.getFecha().getTime()));
        ps.setString(3, event.getUbicacion());
        ps.setString(4, String.valueOf(Conexion.convertirBooleanAChar(event.isActivo())));
        ps.setInt(5, event.getId());
        ps.executeUpdate();
        Conexion.desconectar();
    }

    public static void eliminar(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("DELETE FROM evento WHERE ID_EVENTO = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        Conexion.desconectar();
    }

    public static List<Evento> seleccionarEventosDeHoy() throws SQLException {
        Conexion.conectar();
        String query = "SELECT * FROM Evento "
                + "WHERE TO_CHAR(FECHA, 'dd') = TO_CHAR(TO_DATE('" + new SimpleDateFormat("dd/M/yyyy").format(new Date())  + "', 'DD/MM/YY'), 'DD')";
        PreparedStatement ps = Conexion.conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Evento> eventos = new ArrayList<>();
        while (rs.next()) {
            eventos.add(new Evento(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getString("nombre"), rs.getDate("fecha"), rs.getString("ubicacion")));
        }
        Conexion.desconectar();
        return eventos;
    }
}
