package com.itla.vista.administrador;

import com.itla.modelo.Usuario;
import com.itla.servicios.ServicioUsuario;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Maria Elena
 */
public class PanelDetalleUsuario extends javax.swing.JPanel {

    private Window padre;
    private ServicioUsuario servicio = new ServicioUsuario();
    private ArrayList<Usuario> usuarios;
    private DefaultTableModel model;
    private Object[] columnasTabla;

    public PanelDetalleUsuario() {
        btnRefrescar();
        columnasTabla = getColumnsNames();
        model = new javax.swing.table.DefaultTableModel(getData(), columnasTabla) {
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Integer.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                true, false,
                false, false, false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        initComponents();
    }

    public PanelDetalleUsuario(Window padre) {
        this();
        this.padre = padre;
        tablaUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JTable target = (JTable) evt.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();
                if (evt.getClickCount() == 1) {
                    if (column == 0) {
                        habilitarModificarEliminar();
                    }
                } else if (evt.getClickCount() == 2) {
                    if (column <= 0) {
                        return;
                    }
                    target.getModel().getValueAt(row, column);
                    try {
                        ArrayList<Usuario> usuarios = new ArrayList<>();
                        usuarios.add(servicio.SeleccionarPorId((int) target.getValueAt(row, 1)));
                        abrirDetalle(usuarios);
                        btnRefrescar();
                        refrescarTabla();
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelDetalleUsuario.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(PanelDetalleUsuario.this.padre, "Error cargando el objeto seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnEliminarTodos.setEnabled(((DefaultTableModel) tablaUsuario.getModel()).getRowCount() > 0);
    }

    private Object[] getColumnsNames() {
        return new Object[]{"Seleccionar", "Id", "Nombre", "Apellido", "Cuenta",  "Perfil Usuario", "Activo"};
    }

    private Object[][] getData() {
        Object[][] obj = new Object[usuarios.size()][columnasTabla.length];
        for (int i = 0; i < usuarios.size(); i++) {
            obj[i][0] = false;
            obj[i][1] = usuarios.get(i).getId();
            obj[i][2] = usuarios.get(i).getNombre();
            obj[i][3] = usuarios.get(i).getApellido();
            obj[i][4] = usuarios.get(i).getCuenta();
            obj[i][5] = usuarios.get(i).getPerfilUsuario().getNombre();
            obj[i][6] = usuarios.get(i).isActivo();
        }
        return obj;
    }

    private void btnRefrescar() {
        try {
            if (usuarios != null) {
                usuarios.clear();
            }
            usuarios = servicio.seleccionarTodos();
        } catch (SQLException ex) {
            Logger.getLogger(PanelDetalleUsuario.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(padre,
                    "Error cargando los datos", "Error cargando los datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarTabla() {
        ((DefaultTableModel) tablaUsuario.getModel()).setDataVector(getData(), columnasTabla);
        btnEliminarTodos.setEnabled(((DefaultTableModel) tablaUsuario.getModel()).getRowCount() > 0);
    }

    private void habilitarModificarEliminar() {
        boolean enable = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((boolean) model.getValueAt(i, 0)) {
                enable = true;
                break;
            }
        }
        btnModificar.setEnabled(enable);
        btnEliminar.setEnabled(enable);
        btnRefrescar.setEnabled(enable);
    }

    private void btnModificar() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        HashSet<Integer> idNoModificadosPorErrores = new HashSet<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((boolean) model.getValueAt(i, 0)) {
                try {
                    usuarios.add(servicio.SeleccionarPorId((Integer) model.getValueAt(i, 1)));
                } catch (SQLException ex) {
                    Logger.getLogger(PanelDetalleUsuario.class
                            .getName()).log(Level.SEVERE, null, ex);
                    idNoModificadosPorErrores.add(i);
                }
            }
        }

        if (idNoModificadosPorErrores.size() > 0) {
            JOptionPane.showMessageDialog(padre, "Los usuarios id: "
                    + idNoModificadosPorErrores + "No podran ser modificados debido a problemas en el servidor", "Error para mdificar",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (!usuarios.isEmpty()) {
            abrirDetalle(usuarios);
            btnRefrescar();
            refrescarTabla();
        }
        habilitarModificarEliminar();
    }

    private void Eliminar() {
        int opcion = JOptionPane.showConfirmDialog(padre, "Realmente desea eliminar este usuario?",
                "Confirmar Eliminacion", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            HashSet<Integer> idsNoEliminados = new HashSet<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                if ((boolean) model.getValueAt(i, 0)) {
                    try {
                        servicio.eliminar((Integer) model.getValueAt(i, 1));
                    } catch (SQLException ex) {
                        idsNoEliminados.add(i);
                        Logger.getLogger(PanelDetalleUsuario.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (idsNoEliminados.size() > 0) {
                JOptionPane.showMessageDialog(padre, "Los usuarios id: "
                        + idsNoEliminados + "No pudieron ser eliminados por problemas en el servidor", "Error al eliminar",
                        JOptionPane.ERROR_MESSAGE);
            }
            btnRefrescar();
            refrescarTabla();
            JOptionPane.showMessageDialog(padre, "Usuarios eliminados exitosamente", "Usuario Eliminado", JOptionPane.INFORMATION_MESSAGE);
            habilitarModificarEliminar();
        }
    }

    public void btnEliminarTodos() {
        int opcion = JOptionPane.showConfirmDialog(padre, "Realmente desea eliminar TODOS los usuarios?",
                "Confirmar Eliminacion", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            HashSet<Integer> idsNoEliminados = new HashSet<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    servicio.eliminar((Integer) model.getValueAt(i, 1));
                } catch (SQLException ex) {
                    idsNoEliminados.add(i);
                    Logger.getLogger(PanelDetalleUsuario.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (idsNoEliminados.size() > 0) {
                JOptionPane.showMessageDialog(padre, "Los usuarios id: "
                        + idsNoEliminados + "No puedieron ser eliminados por problemas en el servidor", "Error al eliminar",
                        JOptionPane.ERROR_MESSAGE);
            }
            btnRefrescar();
            refrescarTabla();
            JOptionPane.showMessageDialog(padre, "Usuarios eliminados exitosamente", "Usuario Eliminado", JOptionPane.INFORMATION_MESSAGE);
            habilitarModificarEliminar();
        }
    }

    private void abrirDetalle() {
        JDialog ventana = new JDialog();
        ventana.add(new PanelUsuario(ventana));
        ventana.setModal(true);
        ventana.pack();
        ventana.setVisible(true);
        btnRefrescar();
        refrescarTabla();
    }

    private void abrirDetalle(List<Usuario> usuarios) {
        if (usuarios == null || usuarios.isEmpty()) {
            abrirDetalle();
        }
        JDialog ventana = new JDialog();
        ventana.add(new PanelUsuario(ventana, usuarios));
        ventana.setModal(true);
        ventana.pack();
        ventana.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrolPaneUsuario = new javax.swing.JScrollPane();
        tablaUsuario = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnEliminarTodos = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();

        tablaUsuario.setModel(model);
        scrolPaneUsuario.setViewportView(tablaUsuario);
        tablaUsuario.getColumnModel().getColumn(0).setResizable(false);

        btnEliminarTodos.setText("Eliminar Todos");
        btnEliminarTodos.setEnabled(false);
        btnEliminarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodosActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminarTodos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRefrescar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarTodos)
                    .addComponent(btnEliminar)
                    .addComponent(btnModificar)
                    .addComponent(btnAgregar)
                    .addComponent(btnRefrescar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrolPaneUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 956, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrolPaneUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTodosActionPerformed
        btnEliminarTodos();
    }//GEN-LAST:event_btnEliminarTodosActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        abrirDetalle();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        btnModificar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        btnRefrescar();
        refrescarTabla();
    }//GEN-LAST:event_btnRefrescarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarTodos;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scrolPaneUsuario;
    private javax.swing.JTable tablaUsuario;
    // End of variables declaration//GEN-END:variables
}
