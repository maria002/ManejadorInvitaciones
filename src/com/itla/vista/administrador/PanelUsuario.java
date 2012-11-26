package com.itla.vista.administrador;

import com.itla.modelo.PerfilUsuario;
import com.itla.modelo.Usuario;
import com.itla.servicios.ServicioPerfilUsuario;
import com.itla.servicios.ServicioUsuario;
import java.awt.Component;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Maria Elena, Katerina y Samuel
 */
public class PanelUsuario extends AbstractPanel {

    private ServicioUsuario servicio = new ServicioUsuario();
    private List<Usuario> listaUsuario;
    private int modificandoIdx = 0;
    private DefaultComboBoxModel modelo;

    public PanelUsuario() {
        super();
        PerfilUsuario[] objs = null;
        try {
            ArrayList<PerfilUsuario> perfiles = new ServicioPerfilUsuario().seleccionarTodos();
            if (perfiles != null && perfiles.size() > 0) {
                objs = new PerfilUsuario[perfiles.size()];
                perfiles.toArray(objs);
                modelo = new DefaultComboBoxModel<>(objs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanelUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(objs == null){
            JOptionPane.showMessageDialog(padre, "No hay perfiles creados. Por favor agregue al menos un perfil.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        initComponents();
    }

    public PanelUsuario(Window padre) {
        this();
        this.padre = padre;
    }

    public PanelUsuario(Window padre, List<Usuario> usuarios) {
        this(padre);
        this.listaUsuario = usuarios;
        if (usuarios != null && usuarios.size() == 1) {
            btnGuardarContinuar.setVisible(false);
            cargarData();
        } else if (usuarios != null && usuarios.size() > 0) {
            cargarData();
        }
        txtCuenta.setEditable(false);
        txtCuenta.setEnabled(false);
    }

    private void cargarData() {
        txtID.setText(String.valueOf(listaUsuario.get(modificandoIdx).getId()));
        txtNombre.setText(listaUsuario.get(modificandoIdx).getNombre());
        txtApellido.setText(listaUsuario.get(modificandoIdx).getApellido());
        txtCuenta.setText(listaUsuario.get(modificandoIdx).getCuenta());
        txtpassword.setText(null);
        lblClave.setText("Clave: ");
        lblCuenta.setText("Cuenta: ");
        chkActivo.setSelected(listaUsuario.get(modificandoIdx).isActivo());
    }

    private boolean validar() {
        boolean valido = true;
        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe Insertar su nombre");
            valido = false;
        } else if (txtApellido.getText().isEmpty()) {
            valido = false;
            JOptionPane.showMessageDialog(null, "Debe Insertar su apellido");
        } else if (txtCuenta.getText().isEmpty()) {
            valido = false;
            JOptionPane.showMessageDialog(null, "Debe insertar su cuenta");
        } else if (modificandoIdx > 1 && txtpassword.getPassword().length == 0) {
            valido = false;
            JOptionPane.showMessageDialog(null, "Ingrese su clave");
        } else if (cmbPerfilUsuario.getSelectedItem().toString().isEmpty()) {
            valido = false;
            JOptionPane.showMessageDialog(null, "Debe insertar su perfil de usuario");
        }
        return valido;
    }

    private boolean guardar() {
        boolean guadado = false;
        try {
            Usuario usuario = new Usuario(0, chkActivo.isSelected(), txtNombre.getText(), txtApellido.getText(), txtCuenta.getText(), String.valueOf(txtpassword.getPassword()), ((PerfilUsuario) cmbPerfilUsuario.getSelectedItem()));
            if (!txtID.getText().isEmpty()) {
                usuario.setId(Integer.parseInt(txtID.getText()));
            }
            servicio.insertar(usuario);
            JOptionPane.showMessageDialog(padre, "Datos insertados correctamente", "Datos insertados", JOptionPane.INFORMATION_MESSAGE);
            txtNombre.requestFocus();
            guadado = true;
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            Logger.getLogger(PanelUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Elija otra cuenta. La digitada ya existe.", "Error guardando", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(PanelUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al guardar el registro en la base de datos", "Error guardando", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(PanelUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error guardando el registro", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return guadado;
    }

    private void limpiarCampos() {
        txtID.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCuenta.setText("");
        chkActivo.setSelected(true);
        txtpassword.setText("");
        //TODO: Cargar el perfil de usuarioprimer P
        //cmbPerfilUsuario.setSelectedIndex();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblId = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblCuenta = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtCuenta = new javax.swing.JTextField();
        lblClave = new javax.swing.JLabel();
        chkActivo = new javax.swing.JCheckBox();
        lblActivo = new javax.swing.JLabel();
        txtpassword = new javax.swing.JPasswordField();
        lblRol = new javax.swing.JLabel();
        cmbPerfilUsuario = new javax.swing.JComboBox();
        panelBotones = new javax.swing.JPanel();
        btnGuardarContinuar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        lblId.setText("ID");

        lblNombre.setText("Nombre:*");

        lblApellido.setText("Apellido:*");

        lblCuenta.setText("Cuenta:*");

        txtID.setEditable(false);
        txtID.setEnabled(false);

        lblClave.setText("Clave:*");

        chkActivo.setSelected(true);
        chkActivo.setText("Activo");

        lblActivo.setText("Activo*");

        lblRol.setText("Rol:*");

        cmbPerfilUsuario.setModel(modelo);
        cmbPerfilUsuario.setRenderer(new DefaultListCellRenderer(){
            private PerfilUsuario perfil;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                perfil = (PerfilUsuario) value;
                setText(perfil.getNombre());
                return c;
            }
        });

        btnGuardarContinuar.setText("Guardar y Continuar");
        btnGuardarContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarContinuarActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuardarContinuar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarContinuar)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblActivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkActivo))
                            .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRol, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCuenta)
                                    .addComponent(lblApellido)
                                    .addComponent(lblClave))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtpassword)
                                    .addComponent(cmbPerfilUsuario, 0, 160, Short.MAX_VALUE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblNombre)
                                        .addComponent(lblId))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                        .addComponent(txtNombre)))
                                .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(102, 102, 102))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellido)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCuenta)
                    .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClave)
                    .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRol)
                    .addComponent(cmbPerfilUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActivo)
                    .addComponent(chkActivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        //metodo valida formulario
        if (validar()) {
            //metodo para guardar
            if (guardar()) {
                //Limpiar los campos
                limpiarCampos();
                //Cerrar la ventana
                padre.dispose();
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        padre.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarContinuarActionPerformed
        //metodo valida formulario
        if (validar()) {
            //metodo para guardar
            if (guardar()) {
                //Limpiar los campos
                limpiarCampos();
            }
        }
        if (listaUsuario != null && listaUsuario.size() > 0 && modificandoIdx < listaUsuario.size() - 1) {
            modificandoIdx++;
            cargarData();
        } else if (listaUsuario != null && modificandoIdx == listaUsuario.size() - 1) {
            padre.dispose();
        }
    }//GEN-LAST:event_btnGuardarContinuarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarContinuar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox cmbPerfilUsuario;
    private javax.swing.JLabel lblActivo;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblCuenta;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRol;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCuenta;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtpassword;
    // End of variables declaration//GEN-END:variables
}
