package com.itla.vista.portero;

import com.itla.vista.administrador.*;
import com.itla.modelo.Evento;
import com.itla.servicios.ServicioEvento;
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
 * @author Samuel
 */
public class PanelEventosActuales extends javax.swing.JPanel {

    private Window padre;
    private ServicioEvento servicio = new ServicioEvento();
    private ArrayList<Evento> eventos;
    DefaultTableModel model;
    Object[] columnasTabla;

    public PanelEventosActuales() {
        refrescar();
        columnasTabla = getColumnsNames();
        model = new javax.swing.table.DefaultTableModel(getData(), columnasTabla) {
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                true, false, false, false, false, false
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

    public PanelEventosActuales(Window padre) {
        this();
        this.padre = padre;
        tablaEvento.addMouseListener(new MouseAdapter() {
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
                        ArrayList<Evento> eventos = new ArrayList<>();
                        eventos.add(servicio.SeleccionarPorId((int) target.getValueAt(row, 1)));
                        abrirDetalle(eventos);
                        refrescar();
                        refrescarTabla();
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelEventosActuales.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(PanelEventosActuales.this.padre, "Error cargando el objeto seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnRefrescar.setEnabled(((DefaultTableModel) tablaEvento.getModel()).getRowCount() > 0);
    }

    private Object[] getColumnsNames() {
        return new Object[]{"Seleccionar", "Id", "Nombre", "Fecha", "Ubicacion", "Activo"};
    }

    private Object[][] getData() {
        Object[][] obj = new Object[eventos.size()][columnasTabla.length];
        for (int i = 0; i < eventos.size(); i++) {
            obj[i][0] = false;
            obj[i][1] = eventos.get(i).getId();
            obj[i][2] = eventos.get(i).getNombre();
            obj[i][3] = Evento.formato.format(eventos.get(i).getFecha());
            obj[i][4] = eventos.get(i).getUbicacion();
            obj[i][5] = eventos.get(i).isActivo();
        }
        return obj;
    }

    private void refrescar() {
        try {
            if (eventos != null) {
                eventos.clear();
            }
            eventos = servicio.seleccionarTodos();
        } catch (SQLException ex) {
            Logger.getLogger(PanelEventosActuales.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(padre,
                    "Error cargando los datos", "Error cargando los datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarTabla() {
        ((DefaultTableModel) tablaEvento.getModel()).setDataVector(getData(), columnasTabla);
    }

    private boolean validarSeleccion() {
        for (int fila = 0; fila < tablaEvento.getModel().getRowCount(); fila++) {
            System.out.println(tablaEvento.getModel().getValueAt(fila, 0));
            if ((boolean) tablaEvento.getModel().getValueAt(fila, 0)) {
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
        btnRefrescar.setEnabled(((DefaultTableModel) tablaEvento.getModel()).getRowCount() > 0);
    }

    
    private void abrirDetalle() {
        JDialog ventana = new JDialog();
        ventana.add(new PanelEvento(ventana));
        ventana.setModal(true);
        ventana.pack();
        ventana.setVisible(true);
        refrescar();
        refrescarTabla();
        btnRefrescar.setEnabled(((DefaultTableModel) tablaEvento.getModel()).getRowCount() > 0);
    }

    private void abrirDetalle(List<Evento> eventos) {
        if (eventos == null || eventos.isEmpty()) {
            abrirDetalle();
        }
        JDialog ventana = new JDialog();
        ventana.add(new PanelEvento(ventana, eventos));
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

        scrollPaneEvento = new javax.swing.JScrollPane();
        tablaEvento = new javax.swing.JTable();
        btnRefrescar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();

        tablaEvento.setModel(model);
        scrollPaneEvento.setViewportView(tablaEvento);

        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        btnAgregar.setText("Elegir Evento");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefrescar))
                    .addComponent(scrollPaneEvento, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneEvento, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnRefrescar))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        abrirDetalle();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        refrescar();
        refrescarTabla();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JScrollPane scrollPaneEvento;
    private javax.swing.JTable tablaEvento;
    // End of variables declaration//GEN-END:variables
}
