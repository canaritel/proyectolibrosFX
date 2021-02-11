package entidades;

import datos.interfaces.ClonarClase;
import java.util.Objects;

//implementamos la clase de tipo ClonarClase, que es la interface creada para copiar clases
//la opción de clonar es muy interesante cuado deseemos copiar clases
public class ClassAlumno implements ClonarClase {

    private int idRegistro;
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;

    @Override
    //este método se encarga de copiar la clase. Patrón Prototype
    public ClonarClase clonar() {
        ClassAlumno claseAlumno = null;
        try {
            claseAlumno = (ClassAlumno) clone();
        } catch (CloneNotSupportedException e) {
        }
        return claseAlumno;
    }

    public ClassAlumno() {
    }

    public ClassAlumno(int numeroregistro, String dni, String nombre, String apellido1, String apellido2) {
        this.idRegistro = numeroregistro;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassAlumno other = (ClassAlumno) obj;
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }

        return true;
    }

}
