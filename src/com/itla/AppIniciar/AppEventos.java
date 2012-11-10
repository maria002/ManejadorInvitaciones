package com.itla.AppIniciar;

import com.itla.data.EventoAccesoDatos;
import com.itla.data.UsuarioAcessoDatos;
import com.itla.modelo.Evento;
import com.itla.modelo.PerfilUsuario;
import com.itla.modelo.Usuario;
import com.itla.vista.AppIniciar;
import com.itla.vista.AppIniciarInvitado;
import com.itla.vista.PanelDetalleEvento;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Maria Elena, Katerina, Samuel.
 */
public class AppEventos {

    public String nombre1;
    public String ubicacion;
    public String fecha2;
    public int activo;
    public Date fechaformat;
    public java.sql.Date fecha;

//    public AppEventos() { 
//        try {
//            Calendar fecha = Calendar.getInstance();
//            fecha.set(2012, 0, 10);
//            //System.out.println(formatofecha.format(fecha.getTime()));
//            
//            //fechasql = 
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

   
        
    

    public static void main(String[] args) throws SQLException {
        //System.out.println(UsuarioAcessoDatos.seleccionarTodo());
        //System.out.println(UsuarioAcessoDatos.seleccionarPorId(1).toString());
        UsuarioAcessoDatos.insertar(new Usuario(0,false,"maria","Elena","Morillo02", "1234",new PerfilUsuario(1, true, "")));
        //int id, boolean activo, String nombre, String apellido, String cuenta, String clave
        //EventoAccesoDatos.insertar(new Evento(1,false, "prueba", new Date(2012, 12, 6),"altabracia #34"));
        //System.out.println("usuario insertado correctamente");
        //InvitadoAcessoDatos.insertar(new Invitado(0,false, "Angel", "Luis Velaque", "809-234-3323","Villa mella Guaricanos", "F"));
        //System.out.println(InvitadoAcessoDatos.seleccionarTodo());
        //AppIniciar in = new AppIniciar();
        //in.setVisible(true);
        //AppIniciarInvitado ap = new AppIniciarInvitado();
        //ap.setVisible(true);
    }
}
