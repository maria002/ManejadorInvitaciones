package com.itla.data;

import com.itla.modelo.Invitado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvitadoAcessoDatos {

    public static List<Invitado> seleccionarTodo() throws SQLException {
        Conexion.conectar();
        ResultSet rs= Conexion.st.executeQuery("select * from Invitado");
        ArrayList<Invitado> invitados =new ArrayList<>();
        while(rs.next()){
            invitados.add(new Invitado(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")), 
                    rs.getString("nombre"), rs.getString("apellido"), rs.getString("telefono"),rs.getString("direccion"),rs.getString("sexo")));
        }
        Conexion.desconectar();
        return invitados;
    }     
    
    public static void insertar(Invitado invitado){
        try {     
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("insert into invitado values(sec_idInvitacion.nextval, ?,?,?,?,?)");
            ps.setString(1,invitado.getNombre());
            ps.setString(2,invitado.getNombre());
            ps.setString(3,invitado.getTelefono());
            ps.setString(4,invitado.getDireccion());
            ps.setString(5,invitado.getSexo());
            ps.setBoolean(6,String.valueOf(Conexion.convertirBooleanAChar(invitado.isActivo())));
            Conexion.conn.setAutoCommit(false);
            ps.executeUpdate();
            Conexion.conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(InvitadoAcessoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
}
