package com.itla.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author Maria Elena
 */
public class Conexion {

    private static Connection conn; // variable de tipo Connetion ella permite la conexion
    public static Statement st; // variable de tipo Stament para insertar querys y hacer consultas insert delete update y select

    private Conexion() {
    }

    public static void conectar() throws SQLException {
        //aqui creamos un datasource para iniciar la conexion 
        OracleDataSource ods = new OracleDataSource();
        //aqui creamos un url de conexion 
        ods.setURL("jdbc:oracle:thin:JEHOVA/1234@MININT-OAII0R6:1521:XE");
        conn = ods.getConnection();
        st = conn.createStatement();
    }

    public static void desconectar() throws SQLException {
        if (conn != null) {
            conn.close();            
        }
    }
    
    public static boolean convertirBoolean(String activo){
        return activo.equalsIgnoreCase("1");
    }
}
