package com.itla.sistema;

import com.itla.data.Conexion;
//import com.itla.vista.administrador.Contenedor;
//import com.itla.vista.administrador.ContenedorPrincipal;

/**
 *
 * @author Maria Elena, Katerina, Samuel.
 */
public class Sistema {
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                //new Contenedor().setVisible(true);
               
            }
        });
    }
}
