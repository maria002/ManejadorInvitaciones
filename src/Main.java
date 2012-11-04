
import com.itla.data.UsuarioAcessoDatos;
import java.sql.SQLException;

/**
 *
 * @author Maria Elena
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println(UsuarioAcessoDatos.seleccionarTodo());
    }
}
