/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itla.data;

import com.itla.modelo.Evento;
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
            //invitacion.add(new Invitacion(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
               //      rs.getDate("fecha"),rs.getInt("id_eventos"),rs.getInt("id_invitados"),rs.getInt("id_usuarios")));
        }
        Conexion.desconectar();
        return invitacion;
    }
      public static Evento SeleccionarPorId(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("SELECT * FROM invitado WHERE ID_INVITADO = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Evento evento = null;
        while (rs.next()) {
            //evento = new Evento(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                     //rs.getDate("fecha"),rs.getString("Razon_visita"), rs.getString("ubicacion"));
        }
        Conexion.desconectar();
        return evento;
    }

    
}
