package datos;

import database.ConexionPool;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datos.interfaces.CrudInterface;
import entidades.ClassAlumno;
import entidades.ClassLibro;
import entidades.ClassPrestamo;
import entidades.ClassPrestamoAlumno;
import entidades.ClassPrestamoLibro;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import negocio.Variables;

public class PrestamoDAO implements CrudInterface<ClassPrestamo> {

    private final ConexionPool CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public PrestamoDAO() {
        CON = ConexionPool.getInstancia();
    }

    @Override
    public ObservableList<ClassPrestamo> listar(String texto) {
        Variables.registrosMostrados = 0;
        //Creamos un ObservablearrayList donde guardar los datos de nuestra tabla
        ObservableList<ClassPrestamo> registros = FXCollections.observableArrayList(); //Especial para javaFX
        String filtra = texto.toUpperCase();
        String SQL = "SELECT * FROM prestamos WHERE codAlumno LIKE '" + filtra + "%'"
                + " OR codLibros LIKE '%" + filtra + "%' OR FechaPrestamo LIKE '%" + filtra + "%'"
                + " OR FechaDevolucion LIKE  '" + filtra + "%' OR estado LIKE '" + filtra + "%'";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros.add(new ClassPrestamo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5), rs.getString(6)));
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registros;
    }

    public ObservableList<ClassPrestamoAlumno> listarAlumno(String texto) {
        Variables.registrosMostrados = 0;
        //Creamos un ObservablearrayList donde guardar los datos de nuestra tabla
        ObservableList<ClassPrestamoAlumno> registros = FXCollections.observableArrayList(); //Especial para javaFX
        String filtra = texto.toUpperCase();
        String SQL = "SELECT prestamos.id, prestamos.FechaPrestamo, prestamos.FechaDevolucion, prestamos.codAlumno,"
                + " alumnos.dni, alumnos.nombre, alumnos.apellido1, alumnos.apellido2, prestamos.codLibros, prestamos.estado"
                + " FROM prestamos INNER JOIN alumnos on prestamos.codAlumno = alumnos.registro WHERE prestamos.id LIKE '%" + filtra + "%'"
                + " OR prestamos.codAlumno LIKE '%" + filtra + "%' OR alumnos.dni LIKE '%" + filtra + "%' OR alumnos.nombre LIKE '%" + filtra + "%'"
                + " OR prestamos.estado LIKE '%" + filtra + "%' OR alumnos.apellido1 LIKE '%" + filtra + "%' OR alumnos.apellido2 LIKE '%" + filtra + "%'"
                + " OR prestamos.FechaPrestamo LIKE '%" + filtra + "%' OR prestamos.FechaDevolucion LIKE '%" + filtra + "%'";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros.add(new ClassPrestamoAlumno(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getString(4), rs.getString(5),
                                                      rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registros;
    }

    public ObservableList<ClassPrestamoLibro> listarLibro(String texto) {
        Variables.registrosMostrados = 0;
        //Creamos un ObservablearrayList donde guardar los datos de nuestra tabla
        ObservableList<ClassPrestamoLibro> registros = FXCollections.observableArrayList(); //Especial para javaFX
        String filtra = texto.toUpperCase();
        String SQL = "SELECT prestamos.id, prestamos.FechaPrestamo, prestamos.FechaDevolucion, prestamos.codLibros,"
                + " libros.Titulo, libros.Autor, libros.Editorial, libros.Asignatura, prestamos.codAlumno, prestamos.estado"
                + " FROM prestamos INNER JOIN libros on prestamos.codLibros = libros.codigo WHERE prestamos.id LIKE '%" + filtra + "%'"
                + " OR prestamos.codLibros LIKE '%" + filtra + "%' OR libros.titulo LIKE '%" + filtra + "%' OR libros.autor LIKE '%" + filtra + "%'"
                + " OR prestamos.estado LIKE '%" + filtra + "%' OR libros.editorial LIKE '%" + filtra + "%' OR libros.asignatura LIKE '%" + filtra + "%'"
                + " OR prestamos.FechaPrestamo LIKE '%" + filtra + "%' OR prestamos.FechaDevolucion LIKE '%" + filtra + "%'";

        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros.add(new ClassPrestamoLibro(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getString(4), rs.getString(5),
                                                     rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registros;
    }

    @Override
    public boolean insertar(ClassPrestamo obj) {
        resp = false;
        String SQL = "INSERT INTO prestamos (codAlumno,codLibros,FechaPrestamo,FechaDevolucion,estado) VALUES (?,?,?,?,?)";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getCodalumno());
            ps.setString(2, obj.getCodlibro());
            ps.setDate(3, (Date) obj.getFechapres());
            ps.setDate(4, (Date) obj.getFechadevo());
            ps.setString(5, obj.getEstado());
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean actualizar(ClassPrestamo obj) {
        resp = false;
        String SQL = "UPDATE prestamos SET codAlumno=?,codLibros=?,FechaPrestamo=?,FechaDevolucion=?,estado=? WHERE id=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getCodalumno());
            ps.setString(2, obj.getCodlibro());
            ps.setDate(3, (Date) obj.getFechapres());
            ps.setDate(4, (Date) obj.getFechadevo());
            ps.setString(5, obj.getEstado());
            ps.setInt(6, obj.getId());
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean eliminar(ClassPrestamo obj) {
        resp = false;
        String SQL = "DELETE FROM prestamos WHERE id=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, obj.getId());
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public int total() {
        int totalResistros = 0;
        String SQL = "SELECT COUNT(id) FROM prestamos";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                totalResistros = rs.getInt("COUNT(id)");
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return totalResistros;
    }

    @Override
    public boolean existe(String texto) {
        resp = false;
        String SQL = "SELECT * FROM prestamos WHERE id=?";
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    public boolean existeAlumno(ClassPrestamo obj) {
        resp = false;
        String SQL = "SELECT * FROM prestamos WHERE codAlumno=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getCodalumno());
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    public boolean existeLibro(ClassPrestamo obj) {
        resp = false;
        String SQL = "SELECT * FROM prestamos WHERE codLibros=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getCodlibro());
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
                Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
     public ClassLibro devuelveLibro(ClassLibro obj) {
        String SQL = "SELECT * FROM libros WHERE codigo=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, obj.getCodigo());
            rs = ps.executeQuery();
            while (rs.next()) {
                obj.setCodigo(rs.getInt(1));
                obj.setTitulo(rs.getString(2));
                obj.setAutor(rs.getString(3));
                obj.setEditorial(rs.getString(4));
                obj.setAsignatura(rs.getString(5));
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
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj;
    }
    
}
