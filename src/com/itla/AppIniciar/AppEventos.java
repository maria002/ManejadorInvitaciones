import com.itla.data.EventoAccesoDatos;
import com.itla.data.UsuarioAcessoDatos;
import com.itla.data.InvitadoAcessoDatos;
import com.itla.modelo.Evento;
import com.itla.modelo.Invitado;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author Maria Elena, Katerina, Samuel.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        //System.out.println(UsuarioAcessoDatos.seleccionarTodo());
        //System.out.println(UsuarioAcessoDatos.seleccionarPorId(1).toString());
        //int id, boolean activo, String nombre, String apellido, String cuenta, String clave
        //EventoAccesoDatos.insertar(new Evento(0, false, "Cumple Luisa", new Date(2012, 11, 8),"Flor de loto"));
        //InvitadoAcessoDatos.insertar(new Invitado(0,false, "Angel", "Luis Velaque", "809-234-3323","Villa mella Guaricanos", "F"));
        System.out.println(InvitadoAcessoDatos.seleccionarTodo());
    }
}
