package com.itla.vista.administrador;

import com.itla.modelo.PerfilUsuario;
import com.itla.servicios.ServicioPerfilUsuario;
import com.itla.vista.comun.AbstractPanel;
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
public class PanelDetallePerfilUsuario extends AbstractPanel {

    private ServicioPerfilUsuario servicio = new ServicioPerfilUsuario();
    private ArrayList<PerfilUsuario> perfiles;
    private DefaultTableModel model;
    private Object[] columnasTabla;
    
    public PanelDetallePerfilUsuario() {
        refrescar();
        columnasTabla = getColumnsNames();
        model = new javax.swing.table.DefaultTableModel(getData(), columnasTabla) {
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                true, false, false, false
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
    
    public PanelDetallePerfilUsuario(Window padre){
        this();
        this.padre = padre;
        tablaPerfil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();
                if (e.getClickCount() == 1) {
                    if (column == 0) {
                        habilitarModificarEliminar();
                    }
                } else if (e.getClickCount() == 2) {
                    if (column <= 0) {
                        return;
                    }
                    target.getModel().getValueAt(row, column);
                    try {
                        ArrayList<PerfilUsuario> perfil = new ArrayList<>();
                        perfil.add(servicio.seleccionarPorId((int) target.getValueAt(row, 1)));
                        abrirDetalle(perfil);
                        refrescar();
                        refrescarTabla();
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelDetallePerfilUsuario.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(PanelDetallePerfilUsuario.this.padre, "Error cargando el objeto seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnEliminarTodo.setEnabled(((DefaultTableModel) tablaPerfil.getModel()).getRowCount() > 0);
    }
    
    private Object[] getColumnsNames() {
        return new Object[]{"Seleccionar", "Id", "Nombre", "Activo"};
    }
    
    private Object[][] getData() {
        Object[][] obj = new Object[perfiles.size()][columnasTabla.length];
        for (int i = 0; i < perfiles.size(); i++) {
            obj[i][0] = false;
            obj[i][1] = perfiles.get(i).getId();
            obj[i][2] = perfiles.get(i).getNombre();
            obj[i][3] = perfiles.get(i).isActivo();
        }
        return obj;
    }
    
    private void refrescar() {
        try {
            if (perfiles != null) {
                perfiles.clear();
            }
            perfiles = servicio.seleccionarTodos();
        } catch (SQLException ex) {
            Logger.getLogger(PanelDetallePerfilUsuario.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(padre,
                    "Error cargando los datos", "Error cargando los datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refrescarTabla() {
        ((DefaultTableModel) tablaPerfil.getModel()).setDataVector(getData(), columnasTabla);
        btnEliminarTodo.setEnabled(((DefaultTableModel) tablaPerfil.getModel()).getRowCount() > 0);
    }

    private boolean validarSeleccion() {
        for (int fila = 0; fila < tablaPerfil.getModel().getRowCount(); fila++) {
            if ((boolean) tablaPerfil.getModel().getValueAt(fila, 0)) {
                return true;
            }
        }
        return false;
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
    }
    private void modificar() {
        ArrayList<PerfilUsuario> perfiles = new ArrayList<>();
        HashSet<Integer> idNoModificadosPorErroes = new HashSet<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((boolean) model.getValueAt(i, 0)) {
                try {
                    perfiles.add(servicio.seleccionarPorId((Integer) model.getValueAt(i, 1)));
                } catch (SQLException ex) {
                    Logger.getLogger(PanelPerfilUsuario.class
                            .getName()).log(Level.SEVERE, null, ex);
                    idNoModificadosPorErroes.add(i);
                }
            }
        }
        if (idNoModificadosPorErroes.size() > 0) {
            JOptionPane.showMessageDialog(padre, "Los perfiles id: "
                    + idNoModificadosPorErroes + "No podran ser modificados debido a por problemas en el servidor", "Error para modificar",
                    JOptionPane.WARNING_MESSAGE);
        }
        if (!perfiles.isEmpty()) {
            abrirDetalle(perfiles);
            refrescar();
            refrescarTabla();
        }
        habilitarModificarEliminar();
    }
    
     private void eliminar() {
        int opcion = JOptionPane.showConfirmDialog(padre, "Realmente desea eliminar este Perfil de Usuario?",
                "Confirmar Eliminacion", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            HashSet<Integer> idsNoEliminados = new HashSet<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                if ((boolean) model.getValueAt(i, 0)) {
                    try {
                        servicio.eliminar((Integer) model.getValueAt(i, 1));
                    } catch (SQLException ex) {
                        idsNoEliminados.add(i);
                        Logger.getLogger(PanelDetallePerfilUsuario.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (idsNoEliminados.size() > 0) {
                JOptionPane.showMessageDialog(padre, "Los perfiles de usuario id: "
                        + idsNoEliminados + "No puedieron ser eliminados por problemas en el servidor", "Error al eliminar",
                        JOptionPane.ERROR_MESSAGE);
            }
            refrescar();
            refrescarTabla();
            JOptionPane.showMessageDialog(padre, "Perfiles eliminados exitosamente", "Pefiles Eliminados", JOptionPane.INFORMATION_MESSAGE);
            habilitarModificarEliminar();
        }
    }

    private void eliminarTodos() {
        int opcion = JOptionPane.showConfirmDialog(padre, "Realmente desea eliminar TODOS los perfiles?",
                "Confirmar Eliminacion", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            HashSet<Integer> idsNoEliminados = new HashSet<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    servicio.eliminar((Integer) model.getValueAt(i, 1));
                } catch (SQLException ex) {
                    idsNoEliminados.add(i);
                    Logger.getLogger(PanelDetallePerfilUsuario.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (idsNoEliminados.size() > 0) {
                JOptionPane.showMessageDialog(padre, "Los perfiles id: "
                        + idsNoEliminados + "No puedieron ser eliminados por problemas en el servidor", "Error al eliminar",
                        JOptionPane.ERROR_MESSAGE);
            }
            refrescar();
            refrescarTabla();
            JOptionPane.showMessageDialog(padre, "Perfiles eliminados exitosamente", "Perfil Eliminado", JOptionPane.INFORMATION_MESSAGE);
            habilitarModificarEliminar();
        }
    }
     private void abrirDetalle() {
        JDialog ventana = new JDialog();
        ventana.add(new PanelPerfilUsuario(ventana));
        ventana.setModal(true);
        ventana.pack();
        ventana.setVisible(true);
        refrescar();
        refrescarTabla();
    }
    private void abrirDetalle(List<PerfilUsuario> perfiles) {
        if (perfiles == null || perfiles.isEmpty()) {
            abrirDetalle();
        }
        JDialog ventana = new JDialog();
        ventana.add(new PanelPerfilUsuario(ventana, perfiles));
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPerfil = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnModificar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnEliminarTodo = new javax.swing.JButton();

        tablaPerfil.setModel(model);
        jScrollPane1.setViewportView(tablaPerfil);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
        );

        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminarTodo.setText("Eliminar Todo");
        btnEliminarTodo.setEnabled(false);
        btnEliminarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminarTodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefrescar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnRefrescar)
                    .addComponent(btnModificar)
                    .addComponent(btnAgregar)
                    .addComponent(btnEliminarTodo)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        refrescar();
        refrescarTabla();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        abrirDetalle();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEliminarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTodoActionPerformed
        eliminarTodos();
    }//GEN-LAST:event_btnEliminarTodoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarTodo;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaPerfil;
    // End of variables declaration//GEN-END:variables
}
