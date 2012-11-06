package com.itla.data;

import com.itla.modelo.Evento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Samuel Pena
 */
public class EventoAccesoDatos {
    public static List<Evento> SeleccionarTodo() throws SQLException {
        Conexion.conectar();
        ResultSet rs = Conexion.st.executeQuery("Select * from evento");
        ArrayList<Evento>evento = new ArrayList<>();
        while(rs.next()){
            evento.add(new Evento(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getString("nombre"), rs.getDate("fecha"), rs.getString("ubicacion")));
                    }
        Conexion.desconectar();
        return evento;
    }
    public static void insertar(Evento event) throws SQLException{
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("insert into Evento values (SEC_ID_EVENTO.nextval, ?,?,?,?)");
            ps.setString(1, event.getNombre());
            ps.setDate(2, event.getFecha());
            ps.setString(3, event.getUbicacion());
            ps.setString(4, String.valueOf(Conexion.convertirBooleanAChar(event.isActivo())));
            
            
            
            Conexion.conn.setAutoCommit(false);
            ps.executeUpdate();
            Conexion.conn.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(EventoAccesoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Conexion.conn.rollback();
            Conexion.conn.setAutoCommit(true);
            Conexion.desconectar();
        }
        
    }
}
