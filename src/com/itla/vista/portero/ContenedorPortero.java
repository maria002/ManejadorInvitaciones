package com.itla.vista.portero;

import com.itla.modelo.Sesion;
import com.itla.vista.comun.AbstractPanel;
import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author Maria Elena
 */
public class ContenedorPortero extends javax.swing.JFrame {

    private JFrame padre; 
       
    /**
     * Creates new form ContenedorPrincipal
     */
    public ContenedorPortero() {
        initComponents();
        abrirVentana(new PanelEventosActuales(this), "Evento Actual");
    }

    public ContenedorPortero(JFrame padre) {
        this();
        this.padre = padre;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mdiContainer = new javax.swing.JDesktopPane();
        programName = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        mnArchivo = new javax.swing.JMenu();
        mnSalir = new javax.swing.JMenuItem();
        mnCerrarSeccion = new javax.swing.JMenuItem();
        mnAdministrar = new javax.swing.JMenu();
        mnEventosAct = new javax.swing.JMenuItem();
        mnBuscarInv = new javax.swing.JMenuItem();
        mnEventosProx = new javax.swing.JMenuItem();
        mnListadoComp = new javax.swing.JMenuItem();
        mnAcercaDe = new javax.swing.JMenu();
        mnAyuda = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manejador Invitaciones");

        programName.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        programName.setText("Manejador de Visitantes");
        programName.setBounds(150, 130, 382, 50);
        mdiContainer.add(programName, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setBounds(564, 10, 100, 20);
        mdiContainer.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mnArchivo.setText("Archivo");

        mnSalir.setText("Salir");
        mnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSalirActionPerformed(evt);
            }
        });
        mnArchivo.add(mnSalir);

        mnCerrarSeccion.setText("Cerrar Seccion");
        mnCerrarSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCerrarSeccionActionPerformed(evt);
            }
        });
        mnArchivo.add(mnCerrarSeccion);

        menuBar.add(mnArchivo);

        mnAdministrar.setText("Opciones");

        mnEventosAct.setText("Eventos Actuales");
        mnEventosAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEventosActActionPerformed(evt);
            }
        });
        mnAdministrar.add(mnEventosAct);

        mnBuscarInv.setText("Buscar Invitados");
        mnBuscarInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnBuscarInvActionPerformed(evt);
            }
        });
        mnAdministrar.add(mnBuscarInv);

        mnEventosProx.setText("Eventos Proximos");
        mnEventosProx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEventosProxActionPerformed(evt);
            }
        });
        mnAdministrar.add(mnEventosProx);

        mnListadoComp.setText("Listado Completo");
        mnListadoComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnListadoCompActionPerformed(evt);
            }
        });
        mnAdministrar.add(mnListadoComp);

        menuBar.add(mnAdministrar);

        mnAcercaDe.setText("Acerca de");

        mnAyuda.setText("Ayuda");
        mnAcercaDe.add(mnAyuda);

        menuBar.add(mnAcercaDe);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mdiContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mdiContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnSalirActionPerformed

    private void mnBuscarInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnBuscarInvActionPerformed
        abrirVentana(new PanelBuscarInvitados(null), "Eventos");
    }//GEN-LAST:event_mnBuscarInvActionPerformed

    private void mnEventosActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnEventosActActionPerformed
        abrirVentana(new PanelEventosActuales(null), "Usuarios");
    }//GEN-LAST:event_mnEventosActActionPerformed

    private void mnEventosProxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnEventosProxActionPerformed
        abrirVentana(new PanelEventosProximos(null), "Invitacion");
    }//GEN-LAST:event_mnEventosProxActionPerformed

    private void mnListadoCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnListadoCompActionPerformed
        if (Sesion.eventoActual == null) {
            JOptionPane.showMessageDialog(padre, "Debe elejir el evento que esta trabajando", "Elija el evento", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        abrirVentana(new PanelListadoCompleto(null), "Invitados");
    }//GEN-LAST:event_mnListadoCompActionPerformed

    private void mnCerrarSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCerrarSeccionActionPerformed
        Sesion.usuarioLogeado = null;
        setVisible(false);
        padre.setVisible(true);
    }//GEN-LAST:event_mnCerrarSeccionActionPerformed

    public void abrirVentana(AbstractPanel panel, String titulo) {
        JInternalFrame frame = new JInternalFrame(titulo, true, true, true, true);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        try {
            frame.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ContenedorPortero.class.getName()).log(Level.SEVERE, null, ex);
        }
        mdiContainer.add(frame);
        try {
            frame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ContenedorPortero.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ContenedorPortero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ContenedorPortero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ContenedorPortero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ContenedorPortero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ContenedorPortero().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JDesktopPane mdiContainer;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu mnAcercaDe;
    private javax.swing.JMenu mnAdministrar;
    private javax.swing.JMenu mnArchivo;
    private javax.swing.JMenuItem mnAyuda;
    private javax.swing.JMenuItem mnBuscarInv;
    private javax.swing.JMenuItem mnCerrarSeccion;
    private javax.swing.JMenuItem mnEventosAct;
    private javax.swing.JMenuItem mnEventosProx;
    private javax.swing.JMenuItem mnListadoComp;
    private javax.swing.JMenuItem mnSalir;
    private javax.swing.JLabel programName;
    // End of variables declaration//GEN-END:variables
}
