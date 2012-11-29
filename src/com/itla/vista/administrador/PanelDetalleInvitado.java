package com.itla.vista.administrador;

import com.itla.modelo.Invitado;
import com.itla.servicios.ServicioInvitado;
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
public class PanelDetalleInvitado extends AbstractPanel {

    private ServicioInvitado servicio = new ServicioInvitado();
    private ArrayList<Invitado> invitados;
    private DefaultTableModel model;
    private Object[] columnasTabla;

    public PanelDetalleInvitado() {
        refrescar();
        columnasTabla = getColumnsNames();
        model = new javax.swing.table.DefaultTableModel(getData(), columnasTabla) {
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                true, false, false, false, false, false, false, false
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

    public PanelDetalleInvitado(Window padre) {
        this();
        this.padre = padre;
        tablaInivtado.addMouseListener(new MouseAdapter() {
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
                        ArrayList<Invitado> invitados = new ArrayList<>();
                        invitados.add(servicio.SeleccionarPorId((int) target.getValueAt(row, 1)));
                        abrirDetalle(invitados);
                        refrescar();
                        refrescarTabla();
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelDetalleInvitado.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(PanelDetalleInvitado.this.padre, "Error cargando el objeto seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnEliminarTodos.setEnabled(((DefaultTableModel) tablaInivtado.getModel()).getRowCount() > 0);
    }

    private Object[] getColumnsNames() {
        return new Object[]{"Seleccionar", "Id", "Nombre", "Apellido", "Telefono", "Direccion", "Sexo", "Activo"};
    }

    private Object[][] getData() {
        Object[][] obj = new Object[invitados.size()][columnasTabla.length];
        for (int i = 0; i < invitados.size(); i++) {
            obj[i][0] = false;
            obj[i][1] = invitados.get(i).getId();
            obj[i][2] = invitados.get(i).getNombre();
            obj[i][3] = invitados.get(i).getApellido();
            obj[i][4] = invitados.get(i).getTelefono();
            obj[i][5] = invitados.get(i).getDireccion();
            obj[i][6] = invitados.get(i).getSexo();
            obj[i][7] = invitados.get(i).isActivo();

        }
        return obj;
    }

    private void refrescar() {
        try {
            if (invitados != null) {
                invitados.clear();
            }
            invitados = servicio.seleccionarTodos();
        } catch (SQLException ex) {
            Logger.getLogger(PanelDetalleEvento.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(padre,
                    "Error cargando los datos", "Error cargando los datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarTabla() {
        ((DefaultTableModel) tablaInivtado.getModel()).setDataVector(getData(), columnasTabla);
        btnEliminarTodos.setEnabled(((DefaultTableModel) tablaInivtado.getModel()).getRowCount() > 0);
    }

    private boolean validarSeleccion() {
        for (int fila = 0; fila < tablaInivtado.getModel().getRowCount(); fila++) {
            if ((boolean) tablaInivtado.getModel().getValueAt(fila, 0)) {
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
        ArrayList<Invitado> invitados = new ArrayList<>();
        HashSet<Integer> idNoModificadosPorErroes = new HashSet<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((boolean) model.getValueAt(i, 0)) {
                try {
                    invitados.add(servicio.SeleccionarPorId((Integer) model.getValueAt(i, 1)));
                } catch (SQLException ex) {
                    Logger.getLogger(PanelDetalleEvento.class
                            .getName()).log(Level.SEVERE, null, ex);
                    idNoModificadosPorErroes.add(i);
                }
            }
        }
        if (idNoModificadosPorErroes.size() > 0) {
            JOptionPane.showMessageDialog(padre, "Los eventos id: "
                    + idNoModificadosPorErroes + "No podran ser modificados debido a por problemas en el servidor", "Error para modificar",
                    JOptionPane.WARNING_MESSAGE);
        }
        if (!invitados.isEmpty()) {
            abrirDetalle(invitados);

            refrescar();
            refrescarTabla();
        }

        habilitarModificarEliminar();
    }

    private void eliminar() {
        int opcion = JOptionPane.showConfirmDialog(padre, "Realmente desea eliminar este Invitado?",
                "Confirmar Eliminacion", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            HashSet<Integer> idsNoEliminados = new HashSet<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                if ((boolean) model.getValueAt(i, 0)) {
                    try {
                        servicio.eliminar((Integer) model.getValueAt(i, 1));
                    } catch (SQLException ex) {
                        idsNoEliminados.add(i);
                        Logger.getLogger(PanelDetalleInvitado.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (idsNoEliminados.size() > 0) {
                JOptionPane.showMessageDialog(padre, "Los invitados id: "
                        + idsNoEliminados + "No puedieron ser eliminados por problemas en el servidor", "Error al eliminar",
                        JOptionPane.ERROR_MESSAGE);
            }
            refrescar();
            refrescarTabla();
            JOptionPane.showMessageDialog(padre, "Invitados eliminados exitosamente", "Evento Eliminado", JOptionPane.INFORMATION_MESSAGE);
            habilitarModificarEliminar();
        }
    }

    public void eliminarTodos() {
        int opcion = JOptionPane.showConfirmDialog(padre, "Realmente desea eliminar TODOS los invitados?",
                "Confirmar Eliminacion", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            HashSet<Integer> idsNoEliminados = new HashSet<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    servicio.eliminar((Integer) model.getValueAt(i, 1));
                } catch (SQLException ex) {
                    idsNoEliminados.add(i);
                    Logger.getLogger(PanelDetalleEvento.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (idsNoEliminados.size() > 0) {
                JOptionPane.showMessageDialog(padre, "Los invitados id: "
                        + idsNoEliminados + "No puedieron ser eliminados por problemas en el servidor", "Error al eliminar",
                        JOptionPane.ERROR_MESSAGE);
            }
            refrescar();
            refrescarTabla();
            JOptionPane.showMessageDialog(padre, "Invitados eliminados exitosamente", "Invitado Eliminado", JOptionPane.INFORMATION_MESSAGE);
            habilitarModificarEliminar();
        }
    }

    private void abrirDetalle() {
        JDialog ventana = new JDialog();
        ventana.add(new PanelInvitado(ventana));
        ventana.setModal(true);
        ventana.pack();
        ventana.setVisible(true);
        refrescar();
        refrescarTabla();
    }

    private void abrirDetalle(List<Invitado> eventos) {
        if (eventos == null || eventos.isEmpty()) {
            abrirDetalle();
        }
        JDialog ventana = new JDialog();
        //ventana.add(new PanelInvitado(ventana, eventos));
        ventana.add(new PanelInvitado(ventana, invitados));
        ventana.setModal(true);
        ventana.pack();
        ventana.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInivtado = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnRefrescar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnEliminarTodos = new javax.swing.JButton();

        tablaInivtado.setModel(model);
        jScrollPane1.setViewportView(tablaInivtado);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

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

        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnEliminarTodos.setText("Eliminar Todos");
        btnEliminarTodos.setEnabled(false);
        btnEliminarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefrescar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRefrescar)
                        .addComponent(btnAgregar)
                        .addComponent(btnModificar)
                        .addComponent(btnEliminar)
                        .addComponent(btnEliminarTodos)))
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

    private void btnEliminarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTodosActionPerformed
        eliminarTodos();
    }//GEN-LAST:event_btnEliminarTodosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarTodos;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaInivtado;
    // End of variables declaration//GEN-END:variables
}
