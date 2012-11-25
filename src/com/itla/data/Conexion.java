package com.itla.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author Maria Elena, Katerina, Samuel.
 */
public class Conexion {

    private static OracleDataSource ods;
    public static Connection conn; // variable de tipo Connetion ella permite la conexion
    public static Statement st; // variable de tipo Stament para insertar querys y hacer consultas insert delete update y select
    private static HashMap<String, String> datosConexion;

    static {
        try {
            LeerConexion();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Conexion() {
    }

    public static void LeerConexion() throws FileNotFoundException, IOException {
        BufferedReader bf = new BufferedReader(new FileReader("config.cfg"));
        String linea = null;
        datosConexion = new HashMap<>();
        while ((linea = bf.readLine()) != null) {
            datosConexion.put(linea.split("=")[0], linea.split("=")[1]);
        }
        bf.close();
    }

    public static void conectar() throws SQLException {

        //aqui creamos un datasource para iniciar la conexion 
        OracleDataSource ods = new OracleDataSource();
        //aqui creamos un url de conexion 
        ods.setURL("jdbc:oracle:thin:JEHOVA/1234@MININT-OAII0R6:1521:XE");

//      aqui creamos un datasource para iniciar la conexion 
        //ods = new OracleDataSource();
        // aqui creamos un url de conexion 
        //ods.setURL("jdbc:oracle:thin:" + datosConexion.get("BD") + "/" + datosConexion.get("CLAVE") + "@" + datosConexion.get("SERVIDOR") + ":" + datosConexion.get("PUERTO") + ":XE");

        conn = ods.getConnection();
        st = conn.createStatement();
    }

    public static void desconectar() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static boolean convertirBoolean(String activo) {
        return activo.equalsIgnoreCase("1");
    }

    public static char convertirBooleanAChar(boolean conv) {
        return conv == true ? '1' : '0';
    }
}
