
package com.itla.AppIniciar;

import com.itla.data.EventoAccesoDatos;
import com.itla.modelo.Evento;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import oracle.sql.DATE;

public class ClassManager {
   public String nombreEvento;
   public String ubicacionEvento;
   public String fechaEvento;
   public java.sql.Date fecha = null;
   public int activoEvento;
    
   public void InsertaEvento() throws SQLException, ParseException{
       //String strfecha = "20/11/2012";
       SimpleDateFormat sd = new java.text.SimpleDateFormat("dd/MM/yyyy");
       fecha = new java.sql.Date(sd.parse(fechaEvento).getTime());
       System.out.println(fecha);
       
      
       EventoAccesoDatos.insertar(new Evento(activoEvento, false, nombreEvento, fecha, ubicacionEvento));
       System.out.println("usuario insertado correctamente");
    }
}
