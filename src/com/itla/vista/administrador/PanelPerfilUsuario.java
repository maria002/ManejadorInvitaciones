package com.itla.vista.administrador;

import com.itla.modelo.PerfilUsuario;
import com.itla.servicios.ServicioPerfilUsuario;
import java.awt.Window;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Maria Elena
 */
public class PanelPerfilUsuario extends javax.swing.JPanel {

    private Window padre;
    private ServicioPerfilUsuario servicio = new ServicioPerfilUsuario();
    private List<PerfilUsuario> listaPerfil;
    private int modificandoIdx = 0;

    public PanelPerfilUsuario() {
        super();
        initComponents();
    }

    public PanelPerfilUsuario(Window padre) {
        super();
        this.padre = padre;
        initComponents();
    }

    public PanelPerfilUsuario(Window padre, List<PerfilUsuario> perfiles) {
        this(padre);
        this.listaPerfil = perfiles;
        if (perfiles != null && perfiles.size() == 1) {
            btnGuardarContinuar.setVisible(false);
            cargarData();
        } else if (perfiles != null && perfiles.size() > 0) {
            cargarData();
        }
    }

    private void cargarData() {
        txtId.setText(String.valueOf(listaPerfil.get(modificandoIdx).getId()));
        txtNombre.setText(listaPerfil.get(modificandoIdx).getNombre());
        chkActivo.setSelected(listaPerfil.get(modificandoIdx).isActivo());
    }

    public boolean validar() {
        boolean tmp = true;
        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo nombre esta vacio");
            tmp = false;
        }
        return tmp;
    }

    private void guardar() {
        PerfilUsuario perfil = new PerfilUsuario(0, chkActivo.isSelected(), txtNombre.getText());
        if (!txtId.getText().isEmpty()) {
            perfil.setId(Integer.parseInt(txtId.getText()));
        }
        try {
            servicio.insertar(perfil);
        } catch (SQLException ex) {
            Logger.getLogger(PanelPerfilUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(padre, "Datos insertados correctamente", "Datos insertados", JOptionPane.INFORMATION_MESSAGE);
        txtNombre.requestFocus();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblId = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        lblActivo = new javax.swing.JLabel();
        chkActivo = new javax.swing.JCheckBox();
        lblNombre = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardarContinuar = new javax.swing.JButton();

        lblId.setText("Id:");

        txtId.setEditable(false);
        txtId.setEnabled(false);

        lblActivo.setText("Activo*");

        chkActivo.setSelected(true);
        chkActivo.setText("Activo");

        lblNombre.setText("Nombre:*");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardarContinuar.setText("Guadar y continuar");
        btnGuardarContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarContinuarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblId)
                            .addComponent(lblActivo)
                            .addComponent(lblNombre))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre)
                            .addComponent(txtId)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkActivo)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnGuardarContinuar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblId)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActivo)
                    .addComponent(chkActivo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar)
                    .addComponent(btnGuardarContinuar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        padre.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        //metodo valida formulario
        if (validar()) {
            //metodo para guardar
            guardar();
        }
        //Limpiar los campos
        limpiarCampos();
        //Cerrar la ventana
        padre.dispose();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnGuardarContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarContinuarActionPerformed
         if (validar()) {
            //metodo para guardar
            guardar();
        }
        //Limpiar los campos
        limpiarCampos();
        if (listaPerfil != null && listaPerfil.size() > 0 && modificandoIdx < listaPerfil.size() - 1) {
            modificandoIdx++;
            cargarData();
        } else if (listaPerfil != null && modificandoIdx == listaPerfil.size() - 1) {
            padre.dispose();
        }
    }//GEN-LAST:event_btnGuardarContinuarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarContinuar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JLabel lblActivo;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
