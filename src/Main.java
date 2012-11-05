import com.itla.data.EventoAccesoDatos;
import com.itla.data.UsuarioAcessoDatos;
import com.itla.modelo.Evento;
import com.itla.modelo.PerfilUsuario;
import com.itla.modelo.Usuario;
import java.sql.SQLException;

/**
 *
 * @author Maria Elena, Katerina, Samuel.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println(UsuarioAcessoDatos.seleccionarTodo());
        System.out.println(UsuarioAcessoDatos.seleccionarPorId(1).toString());
        //int id, boolean activo, String nombre, String apellido, String cuenta, String clave
        EventoAccesoDatos.insertar(new Evento(0, false, "Cumple Luisa", "01-20-12","Flor de loto" new PerfilUsuario(1, true, "")));
    }
}
