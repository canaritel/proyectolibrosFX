package datos;

import database.ConnectionPool;
import datos.interfaces.CrudInterface;
import entidades.ClassAcceso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import negocio.Variables;

public class AccesoDAO implements CrudInterface<ClassAcceso> {

    private final ConnectionPool CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public AccesoDAO() throws SQLException {
        CON = ConnectionPool.getDataSource();
    }

    @Override
    public ObservableList<ClassAcceso> listar(String texto) {
        //Creamos un ObservablearrayList de tipo List donde guardar los datos de nuestra tabla
        ObservableList<ClassAcceso> registros = FXCollections.observableArrayList(); //Especia para javaFX
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
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registros;

    }

    @Override
    public boolean actualizar(ClassAcceso obj) {
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
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean eliminar(ClassAcceso obj) {
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
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean insertar(ClassAcceso obj) {
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
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean existe(String campo) {
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
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }
   
    @Override
    public int total() {
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
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return totalResistros;
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
    
}
