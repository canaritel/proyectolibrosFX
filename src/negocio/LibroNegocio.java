package negocio;

import datos.LibroDAO;
import entidades.ClassLibro;
import java.sql.SQLException;

public class LibroNegocio {

    private final LibroDAO DATOS;
    private ClassLibro objeto;

    public LibroNegocio() {
        this.DATOS = new LibroDAO();
        this.objeto = new ClassLibro();
        Variables.registrosMostrados = 0;
    }

    public String insertar(String titulo, String autor, String editorial, String asignatura, String estado) throws SQLException {
        if (DATOS.existeLibro(titulo, autor, editorial, asignatura)) {
            return "El registro con ese Libro ya existe";
        } else {
            objeto.setTitulo(titulo);
            objeto.setAutor(autor);
            objeto.setEditorial(editorial);
            objeto.setAsignatura(asignatura);
            objeto.setEstado(estado);
            if (DATOS.insertar(objeto)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }
    }

    public String actualizar(int codigo, String titulo, String autor, String editorial, String asignatura, String estado, ClassLibro objLibro) throws SQLException {
        //en el primer IF permito que se edite manteniendo los mismos datos salvo el campo estado que puede variar
        if (objLibro.getTitulo().equals(titulo) && objLibro.getAutor().equals(autor) && objLibro.getEditorial().equals(editorial) && objLibro.getAsignatura().equals(asignatura)) {
            objeto.setCodigo(codigo);
            objeto.setTitulo(titulo);
            objeto.setAutor(autor);
            objeto.setEditorial(editorial);
            objeto.setAsignatura(asignatura);
            objeto.setEstado(estado);
            if (DATOS.actualizar(objeto)) {
                return "OK";
            } else {
                return "Error en la actualización";
            }
        } else {
            //en el segundo IF comprobará que los campos no existan en otro registro
            if (DATOS.existeLibro(titulo, autor, editorial, asignatura)) {
                return "El registro con ese Libro ya existe";
            } else {
                objeto.setCodigo(codigo);
                objeto.setTitulo(titulo);
                objeto.setAutor(autor);
                objeto.setEditorial(editorial);
                objeto.setAsignatura(asignatura);
                objeto.setEstado(estado);
                if (DATOS.actualizar(objeto)) {
                    return "OK";
                } else {
                    return "Error en la actualización";
                }
            }
        }
    }

    public String eliminar(int codigo) throws SQLException {
        objeto.setCodigo(codigo);
        if (DATOS.eliminar(objeto)) {
            return "OK";
        } else {
            return "Error al eliminar registro";
        }
    }

    public ClassLibro devolverLibro(int registro) throws SQLException {
        objeto = new ClassLibro();
        objeto.setCodigo(registro);
        return DATOS.devuelveLibro(objeto);
    }

    public int total() throws SQLException {
        return DATOS.total();
    }

    public int totalMostrados() throws SQLException {
        return Variables.registrosMostrados;
    }

}
