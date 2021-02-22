package negocio;

import datos.AlumnoDAO;
import entidades.ClassAlumno;
import java.sql.SQLException;

public class AlumnoNegocio {

    private final AlumnoDAO DATOS;
    private ClassAlumno objeto;

    public AlumnoNegocio() {
        this.DATOS = new AlumnoDAO();
        this.objeto = new ClassAlumno();
        Variables.registrosMostrados = 0;
    }

    public String insertar(String dni, String nombre, String apellido1, String apellido2) throws SQLException {
        //comprobamos si existe el DNI a insertar
        if (DATOS.existe(dni)) {
            return "El registro con ese DNI ya existe";
        } else {
            objeto.setDni(dni);
            objeto.setNombre(nombre);
            objeto.setApellido1(apellido1);
            objeto.setApellido2(apellido2);
            if (DATOS.insertar(objeto)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }
    }

    public String actualizar(int registro, String dni, String dniAnterior, String nombre, String apellido1, String apellido2) throws SQLException {
        //si el nuevo DNI es igual al anterior permitimos la actualización
        if (dni.equals(dniAnterior)) {
            objeto.setIdRegistro(registro);
            objeto.setDni(dni);
            objeto.setNombre(nombre);
            objeto.setApellido1(apellido1);
            objeto.setApellido2(apellido2);
            if (DATOS.actualizar(objeto)) {
                return "OK";
            } else {
                return "Error en la actualización";
            }
        } else {
            if (DATOS.existe(dni)) {
                return "El registro con ese DNI ya existe";
            } else {
                objeto.setIdRegistro(registro);
                objeto.setDni(dni);
                objeto.setNombre(nombre);
                objeto.setApellido1(apellido1);
                objeto.setApellido2(apellido2);
                if (DATOS.actualizar(objeto)) {
                    return "OK";
                } else {
                    return "Error en la actualización";
                }
            }
        }
    }

    public String eliminar(int registro) throws SQLException {
        objeto.setIdRegistro(registro);
        if (DATOS.eliminar(objeto)) {
            return "OK";
        } else {
            return "Error al eliminar registro";
        }
    }

    public ClassAlumno devolverAlumno(int registro) throws SQLException {
        objeto = new ClassAlumno();
        objeto.setIdRegistro(registro);
        return DATOS.devuelveAlumno(objeto);
    }

    public int total() throws SQLException {
        return DATOS.total();
    }

    public int totalMostrados() {
        return Variables.registrosMostrados;
    }
}
