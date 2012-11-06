package com.itla.data;
//cambios samuel
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
    public static void insertar(Invitado invitado) throws SQLException{
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("insert into invitado values(SEC_ID_INVITADO.nextval, ?,?,?,?,?,?)");
        ps.setString(1, invitado.getNombre());
        ps.setString(2, invitado.getApellido());
        ps.setString(3, invitado.getTelefono());
        ps.setString(4, invitado.getDireccion());
        ps.setString(5, String.valueOf(Conexion.convertirBooleanAChar(invitado.isActivo())));
        ps.setString(6, invitado.getSexo());
        Conexion.conn.setAutoCommit(false);
        ps.executeUpdate();
        Conexion.conn.commit();
        
        
    }
    
}
