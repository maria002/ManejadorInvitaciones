package com.itla.vista.portero;

import com.itla.modelo.Invitacion;
import com.itla.modelo.Invitado;
import com.itla.modelo.Sesion;
import com.itla.servicios.ServicioInvitacion;
import com.itla.servicios.ServicioInvitado;
import com.itla.vista.comun.AbstractPanel;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Maria Elena
 */
public class PanelListadoCompleto extends AbstractPanel {

    private ServicioInvitacion servicioInvitacion = new ServicioInvitacion();
    private ServicioInvitado servicioInvitado = new ServicioInvitado();
    private ArrayList<Invitado> invitados;
    private DefaultTableModel model;
    private Object[] columnasTabla;

    public PanelListadoCompleto() {
        invitados = new ArrayList<>();
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

    public PanelListadoCompleto(Window padre) {
        this();
        this.padre = padre;
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
        if(Sesion.eventoActual == null){
            JOptionPane.showMessageDialog(padre, "Debe elegir un evento antes de ver la lista de invitados", "Eliga el evento que esta trabajando", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            if (invitados != null) {
                invitados.clear();
            }
            ArrayList<Invitacion> listaInvitaciones = (ArrayList<Invitacion>)servicioInvitacion.seleccionarPorEventoId(Sesion.eventoActual.getId());
            if(listaInvitaciones == null){
                JOptionPane.showMessageDialog(padre, "No hay invitaciones para este evento.", "No hay invitados", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Invitado invitado = null;
            for(int i = 0; i < listaInvitaciones.size(); i++){
                invitado  = listaInvitaciones.get(i).getInvitado();
                if(invitado == null) {
                    continue;
                }
                invitados.add(invitado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanelListadoCompleto.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(padre,
                    "Error cargando los datos", "Error cargando los datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarTabla() {
        ((DefaultTableModel) tablaInivtado.getModel()).setDataVector(getData(), columnasTabla);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInivtado = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnRefrescar = new javax.swing.JButton();

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
                        .addGap(346, 346, 346)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefrescar))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        refrescar();
        refrescarTabla();
    }//GEN-LAST:event_btnRefrescarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaInivtado;
    // End of variables declaration//GEN-END:variables
}
