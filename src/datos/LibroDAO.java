package datos;

import database.ConexionPool;
import datos.interfaces.CrudInterface;
import entidades.ClassLibro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import negocio.Variables;

public class LibroDAO implements CrudInterface<ClassLibro> {

    private final ConexionPool CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public LibroDAO() {
        CON = ConexionPool.getInstancia();
    }

    @Override
    public ObservableList<ClassLibro> listar(String texto) {
        Variables.registrosMostrados = 0;
        //Creamos un ObservablearrayList de tipo List donde guardar los datos de nuestra tabla
        ObservableList<ClassLibro> registros = FXCollections.observableArrayList(); //Especial para javaFX
        String filtra = texto.toUpperCase();
        String SQL = "SELECT * FROM libros WHERE titulo LIKE '%" + filtra
                + "%'" + " OR autor LIKE '%" + filtra + "%' OR editorial LIKE '%" + filtra
                + "%'" + " OR asignatura LIKE  '" + filtra + "%' OR estado LIKE '" + filtra
                + "%'" + " OR codigo LIKE '" + filtra + "%' ORDER BY codigo";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros.add(new ClassLibro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
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
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registros;
    }

    @Override
    public boolean insertar(ClassLibro obj) {
        resp = false;
        String SQL = "INSERT INTO libros (codigo,Titulo,Autor,Editorial,Asignatura,estado) VALUES (?,?,?,?,?,?)";
        int ultimoCodigo = ultimoRegistro();
        int totalRegistros = total();
        System.out.println("total registros encontrados " + totalRegistros);
        if (ultimoCodigo < 0 || totalRegistros < 0) {
            return false;
        }
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, ultimoCodigo + 1);
            ps.setString(2, obj.getTitulo());
            ps.setString(3, obj.getAutor());
            ps.setString(4, obj.getEditorial());
            ps.setString(5, obj.getAsignatura());
            ps.setString(6, obj.getEstado());
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
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public boolean actualizar(ClassLibro obj) {
        resp = false;
        String SQL = "UPDATE libros SET Titulo=?,Autor=?,Editorial=?,Asignatura=?,estado=? WHERE codigo=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setString(1, obj.getTitulo());
            ps.setString(2, obj.getAutor());
            ps.setString(3, obj.getEditorial());
            ps.setString(4, obj.getAsignatura());
            ps.setString(5, obj.getEstado());
            ps.setInt(6, obj.getCodigo());
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
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
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

    @Override
    public boolean eliminar(ClassLibro obj) {
        resp = false;
        String SQL = "DELETE FROM libros WHERE codigo=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, obj.getCodigo());
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
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    @Override
    public int total() {
        int totalRegistros = -1;
        String SQL = "SELECT COUNT(codigo) FROM libros";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                totalRegistros = rs.getInt("COUNT(codigo)");
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
        return totalRegistros;
    }

    @Override
    //Este método no lo usamos ya que no tenemos un índice claro de comparación, usamos el método existeLibro()
    public boolean existe(String idCodigo) {
        resp = false;
        String SQL = "SELECT * FROM libros WHERE codigo=?";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            ps.setInt(1, Integer.parseInt(idCodigo));
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
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    public boolean existeLibro(String titulo, String autor, String editorial, String asignatura) {
        resp = false;
        String SQL = "SELECT * FROM libros WHERE Titulo = " + titulo + " and Autor = " + autor + " and Editorial = " + editorial + " and Asignatura = " + asignatura;
        try {
            ps = CON.conectar().prepareStatement(SQL);
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
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }

    //Este método nos valdrá para conocer el nº máximo del registro de la tabla
    public int ultimoRegistro() {
        int ultimoRegistro = -1;
        String SQL = "SELECT MAX(codigo) FROM libros";
        try {
            ps = CON.conectar().prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                ultimoRegistro = rs.getInt("MAX(codigo)");
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
        return ultimoRegistro;
    }

}
