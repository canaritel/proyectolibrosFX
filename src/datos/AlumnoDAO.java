package datos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.ConnectionPool;
import datos.interfaces.CrudInterface;
import entidades.ClassAlumno;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import negocio.Variables;

public class AlumnoDAO implements CrudInterface<ClassAlumno> {

    private final ConnectionPool CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public AlumnoDAO() {
        CON = ConnectionPool.getDataSource();
    }

    @Override
    public ObservableList<ClassAlumno> listar(String texto) {
        Variables.registrosMostrados = 0;
        //Creamos un ObservablearrayList de tipo List donde guardar los datos de nuestra tabla
        ObservableList<ClassAlumno> registros = FXCollections.observableArrayList(); //Especial para javaFX
        String filtra = texto.toUpperCase();
        // Creamos nuestra instrucción SQL, donde se pueda mostrar por cualquiera de los campos
        String SQL = "SELECT * FROM alumnos WHERE nombre LIKE '" + filtra + "%'"
                + " OR apellido1 LIKE '%" + filtra + "%' OR apellido2 LIKE '%" + filtra + "%'"
                + " OR registro LIKE  '" + filtra + "%' OR dni LIKE '" + filtra + "%'";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new ClassAlumno(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                Variables.registrosMostrados = Variables.registrosMostrados + 1; //guardamos el total de registros mostrados
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registros;
    }

    @Override
    public boolean insertar(ClassAlumno obj) {
        resp = false;
        String SQL = "INSERT INTO alumnos (dni,nombre,apellido1,apellido2) VALUES (?,?,?,?)";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getDni());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getApellido1());
            ps.setString(4, obj.getApellido2());
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean actualizar(ClassAlumno obj) {
        resp = false;
        String SQL = "UPDATE alumnos SET dni=?,nombre=?,apellido1=?,apellido2=? WHERE registro=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getDni());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getApellido1());
            ps.setString(4, obj.getApellido2());
            ps.setInt(5, obj.getIdRegistro());
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    public ClassAlumno devuelveAlumno(ClassAlumno obj) {
        String SQL = "SELECT * FROM alumnos WHERE registro=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, obj.getIdRegistro());
            rs = ps.executeQuery();
            while (rs.next()) {
                obj.setIdRegistro(rs.getInt(1));
                obj.setDni(rs.getString(2));
                obj.setNombre(rs.getString(3));
                obj.setApellido1(rs.getString(4));
                obj.setApellido2(rs.getString(5));
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj;
    }

    @Override
    public boolean eliminar(ClassAlumno obj) {
        resp = false;
        String SQL = "DELETE FROM alumnos WHERE registro=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, obj.getIdRegistro());
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public int total() {
        int totalResistros = 0;
        String SQL = "SELECT COUNT(registro) FROM alumnos";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                totalResistros = rs.getInt("COUNT(registro)");
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return totalResistros;
    }

    @Override
    public boolean existe(String texto) {
        resp = false;
        String SQL = "SELECT * FROM alumnos WHERE dni=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, texto);
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
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

}
