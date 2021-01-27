package datos;

import database.ConnectionPool;
import entidades.ClassAcceso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import negocio.Variables;

public class AccesoDAO {

    private final ConnectionPool CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public AccesoDAO() throws SQLException {
       // CON = Conexion.getInstacia();
        CON = ConnectionPool.getDataSource();
    }

    public List<ClassAcceso> listar() throws SQLException {
        //Creamos un arrayList de tipo List donde guardar los datos de nuestra tabla
        List<ClassAcceso> registros = new ArrayList<>();
        // Creamos nuestra instrucción SQL, donde leemos todos los campos
        String SQL = "SELECT * FROM usuarios";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new ClassAcceso(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            //Para el correcto funcionamiento del Pool de conexiones
            //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
            //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
            //y deberíamos verificar que conexion no es null antes de intentar cerrar
            if (CON != null) {
                CON.desconectar(ps.getConnection());
            }
            if (ps != null) {
                ps.close();
                rs.close();
            }
        }
        return registros;
    }

    public boolean actualizar(ClassAcceso obj) throws SQLException {
        resp = false;
        String SQL = "UPDATE usuarios SET clave=? WHERE usuario=?";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getClave());
            ps.setString(2, obj.getUsuario());
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            //Para el correcto funcionamiento del Pool de conexiones
            //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
            //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
            //y deberíamos verificar que conexion no es null antes de intentar cerrar
            if (CON != null) {
                CON.desconectar(ps.getConnection());
            }
            if (ps != null) {
                ps.close();
            }
        }
        return resp;
    }

    public boolean eliminar(ClassAcceso obj) throws SQLException {
        resp = false;
        String SQL = "DELETE FROM usuarios WHERE usuario=?";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getUsuario());
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            //Para el correcto funcionamiento del Pool de conexiones
            //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
            //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
            //y deberíamos verificar que conexion no es null antes de intentar cerrar
            if (CON != null) {
                CON.desconectar(ps.getConnection());
            }
            if (ps != null) {
                ps.close();
            }
        }
        return resp;
    }

    public boolean insertar(ClassAcceso obj) throws SQLException {
        resp = false;
        String SQL = "INSERT INTO usuarios (usuario,clave) VALUES (?,?)";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getUsuario());
            ps.setString(2, obj.getClave());
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            //Para el correcto funcionamiento del Pool de conexiones
            //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
            //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
            //y deberíamos verificar que conexion no es null antes de intentar cerrar
            if (CON != null) {
                CON.desconectar(ps.getConnection());
            }
            if (ps != null) {
                ps.close();
            }
        }
        return resp;
    }

    public boolean existe(String campo) throws SQLException {
        resp = false;
        String SQL = "SELECT * FROM usuarios WHERE usuario=?";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, campo);
            rs = ps.executeQuery();
            rs.last();
            if (rs.getRow() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            //Para el correcto funcionamiento del Pool de conexiones
            //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
            //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
            //y deberíamos verificar que conexion no es null antes de intentar cerrar
            if (CON != null) {
                CON.desconectar(ps.getConnection());
            }
            if (ps != null) {
                ps.close();
                rs.close();
            }
        }
        return resp;
    }

    public boolean login(String user, String password) throws SQLException {
        resp = false;
        String SQL = "SELECT * FROM usuarios WHERE usuario=? AND clave=?";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, user);
            ps.setString(2, password);
            rs = ps.executeQuery();
            rs.last();
            if (rs.getRow() > 0) {
                resp = true;
                Variables.varLogin = true; //ponemos en true la variable varLogin si el acceso es correcto
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR, no se encuentran los archivos de la BD portable\n" + e.getMessage());
        } finally {
            try {
                //Para el correcto funcionamiento del Pool de conexiones
                //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
                //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
                //y deberíamos verificar que conexion no es null antes de intentar cerrar
                if (CON != null) {
                    CON.desconectar(ps.getConnection());
                }
                if (ps != null) {
                    ps.close();
                    rs.close();
                }
            } catch (NullPointerException e) {
            }
        }
        return resp;
    }

    public int total() throws SQLException {
        int totalResistros = 0;
        String SQL = "SELECT COUNT(usuario) FROM usuarios";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                totalResistros = rs.getInt("COUNT(usuario)");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            //Para el correcto funcionamiento del Pool de conexiones
            //Hemos puesto el conexion.close() en el finally del try-catch, para asegurarnos de que la conexion se cierra
            //independientemente de que todo vaya bien o salten excepciones. No podemos cerrar la conexion tal cual se está haciendo
            //y deberíamos verificar que conexion no es null antes de intentar cerrar
            if (CON != null) {
                CON.desconectar(ps.getConnection());
            }
            if (ps != null) {
                ps.close();
                rs.close();
            }
        }
        return totalResistros;
    }

}
