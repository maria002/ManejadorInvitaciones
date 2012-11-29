package com.itla.data;
//cambios samuel
import com.itla.modelo.Invitado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvitadoAcessoDatos {

    public static List<Invitado> seleccionarTodo() throws SQLException {
        Conexion.conectar();
        ResultSet rs = Conexion.st.executeQuery("select * from Invitado");
        ArrayList<Invitado> invitados = new ArrayList<>();
        while (rs.next()) {
            invitados.add(new Invitado(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")),
                    rs.getString("nombre"), rs.getString("apellido"), rs.getString("telefono"), rs.getString("direccion"), rs.getString("sexo")));
        }
        Conexion.desconectar();
        return invitados;
    }

    public static Invitado seleccionarPorId(int id) throws SQLException {
        Conexion.conectar();
        PreparedStatement ps = Conexion.conn.prepareStatement("SELECT * FROM invitado WHERE id_invitado = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Invitado invitado = null;
        while (rs.next()) {
            invitado = new Invitado(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")), rs.getString("nombre"), rs.getString("apellido"), rs.getString("telefono"), rs.getString("Direccion"), rs.getString("sexo"));
        }
        Conexion.desconectar();
        return invitado;
    }

    public static void insertar(Invitado invitado) throws SQLException {
        if (invitado.getId() > 0) {
            modificar(invitado);
            return;
        }
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("insert into invitado values(SEC_ID_INVITADO.nextval, ?,?,?,?,?,?)");
            ps.setString(1, invitado.getNombre());
            ps.setString(2, invitado.getApellido());
            ps.setString(3, invitado.getTelefono());
            ps.setString(4, invitado.getDireccion());
            ps.setString(5, String.valueOf(Conexion.convertirBooleanAChar(invitado.isActivo())));
            ps.setString(6, invitado.getSexo());
            ps.executeUpdate();
        } finally {
            Conexion.desconectar();
        }
    }

    public static void modificar(Invitado invt) throws SQLException {
        if (invt.getId() <= 0) {
            throw new IllegalArgumentException("No se puede modificar un registro con id <= 0");
        }
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("UPDATE invitado SET NOMBRE = ?, FECHA = ?, UBICACION = ?, ACTIVO = ? WHERE id_invitado = ?");
            ps.setString(1, invt.getNombre());
            ps.setString(2, invt.getApellido());
            ps.setString(3, invt.getDireccion());
            ps.setString(4, invt.getTelefono());
            ps.setString(5, invt.getSexo());
            ps.setString(6, String.valueOf(Conexion.convertirBooleanAChar(invt.isActivo())));
            ps.setInt(7, invt.getId());
            ps.executeUpdate();
        } finally {
            Conexion.desconectar();
        }
    }

    public static void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("No se puede Eliminar un registro con id <= 0");
        }
        try {
            Conexion.conectar();
            PreparedStatement ps = Conexion.conn.prepareStatement("DELETE FROM invitado WHERE Id_Invitado = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            Conexion.desconectar();
        }
    }

    public static List<Invitado> SeleccionarPorNombreApellido(String nombre, String apellido) throws SQLException {
        Conexion.conectar();
        String query = "SELECT * FROM invitado WHERE ";
        PreparedStatement ps = null;
        boolean ejecutar = false;
        ArrayList<Invitado> invitados = null;

        if (nombre != null && !nombre.isEmpty() && apellido != null && !apellido.isEmpty()) {
            query = "nombre like ? OR apellido ?";
            ps = Conexion.conn.prepareStatement(query);
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + apellido + "%");
            ejecutar = true;
        } else if (nombre != null && !nombre.isEmpty()) {
            query += "nombre like ? ";
            ps = Conexion.conn.prepareStatement(query);
            ps.setString(1, "%" + nombre + "%");
            ejecutar = true;
        } else if (apellido != null && !apellido.isEmpty()) {
            query += "apellido like ?";
            ps = Conexion.conn.prepareStatement(query);
            ps.setString(1, "%" + apellido + "%");
            ejecutar = true;
        }

        if (ejecutar) {
            ResultSet rs = ps.executeQuery();
            invitados = new ArrayList<>();
            while (rs.next()) {
                invitados.add(new Invitado(rs.getInt(1), Conexion.convertirBoolean(rs.getString("activo")), rs.getString("nombre"), rs.getString("apellido"), rs.getString("telefono"), rs.getString("Direccion"), rs.getString("sexo")));
            }
            Conexion.desconectar();
        }

        return invitados;
    }
}
