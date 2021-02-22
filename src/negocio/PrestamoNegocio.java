package negocio;

import datos.PrestamoDAO;
import entidades.ClassAlumno;
import entidades.ClassLibro;
import entidades.ClassPrestamo;
import java.sql.Date; //creamos las variables Date de tipo SQL
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class PrestamoNegocio {

    private final PrestamoDAO DATOS;
    private final ClassPrestamo objeto;
    private ClassAlumno objetoAlumno;
    private ClassLibro objetoLibro;
    private DefaultTableModel modeloTabla;

    public PrestamoNegocio() {
        this.DATOS = new PrestamoDAO();
        this.objeto = new ClassPrestamo();
        Variables.registrosMostrados = 0;
    }

    public String insertar(String codigoAlumno, String codigoLibro, Date fechaPrestamo, Date fechaDevolucion, String estado) throws SQLException {
        objeto.setCodalumno(codigoAlumno);
        objeto.setCodlibro(codigoLibro);
        objeto.setFechapres(fechaPrestamo);
        objeto.setFechadevo(fechaDevolucion);
        objeto.setEstado(estado);
        if (DATOS.insertar(objeto)) {
            return "OK";
        } else {
            return "Error en el registro";
        }
    }

    public String actualizar(int id, String codigoAlumno, String codigoLibro, Date fechaPrestamo, Date fechaDevolucion, String estado) throws SQLException {
        objeto.setId(id);
        objeto.setCodalumno(codigoAlumno);
        objeto.setCodlibro(codigoLibro);
        objeto.setFechapres(fechaPrestamo);
        objeto.setFechadevo(fechaDevolucion);
        objeto.setEstado(estado);
        if (DATOS.actualizar(objeto)) {
            return "OK";
        } else {
            return "Error en la actualización";
        }
    }

    public String eliminar(int id) throws SQLException {
        objeto.setId(id);
        if (DATOS.eliminar(objeto)) {
            return "OK";
        } else {
            return "Error al eliminar registro";
        }
    }

    public String existeAlumno(String id) throws SQLException {
        objeto.setCodalumno(id);
        if (DATOS.existeAlumno(objeto)) {
            return "OK";
        } else {
            return "NO";
        }
    }

    public String existeLibro(String id) throws SQLException {
        objeto.setCodlibro(id);
        if (DATOS.existeLibro(objeto)) {
            return "OK";
        } else {
            return "NO";
        }
    }

    public int total() throws SQLException {
        return DATOS.total();
    }

    public int totalMostrados() {
        return Variables.registrosMostrados;
    }

    public ClassAlumno devolverAlumno(int registro) throws SQLException {
        objetoAlumno = new ClassAlumno();
        objetoAlumno.setIdRegistro(registro);
        return DATOS.devuelveAlumno(objetoAlumno);
    }
    
     public ClassLibro devolverLibro(int registro) throws SQLException {
        objetoLibro = new ClassLibro();
        objetoLibro.setCodigo(registro);
        return DATOS.devuelveLibro(objetoLibro);
    }
}
