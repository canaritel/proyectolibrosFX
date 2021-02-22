package entidades;

import datos.interfaces.ClonarClase;
import java.sql.Date;  //importante usar SQL.DATE

public class ClassPrestamoAlumno implements ClonarClase {

    private int id;
    private Date fechapres; //declaramos las variables fecha como Date SQL
    private Date fechadevo; //declaramos las variables fecha como Date SQL
    private String codalumno;
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String codlibro; //para completar los datos al editar
    private String estado;  //para completar los datos al editar

    @Override
    public ClonarClase clonar() {
        ClassPrestamoAlumno clasePrestamoAlumno = null;
        try {
            clasePrestamoAlumno = (ClassPrestamoAlumno) clone();
        } catch (CloneNotSupportedException e) {
        }
        return clasePrestamoAlumno;
    }

    public ClassPrestamoAlumno() {
    }

    public ClassPrestamoAlumno(int id, Date fechapres, Date fechadevo, String codalumno, String dni, String nombre, String apellido1, String apellido2, String codlibro, String estado) {
        this.id = id;
        this.fechapres = fechapres;
        this.fechadevo = fechadevo;
        this.codalumno = codalumno;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.codlibro = codlibro;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechapres() {
        return fechapres;
    }

    public void setFechapres(Date fechapres) {
        this.fechapres = fechapres;
    }

    public Date getFechadevo() {
        return fechadevo;
    }

    public void setFechadevo(Date fechadevo) {
        this.fechadevo = fechadevo;
    }

    public String getCodalumno() {
        return codalumno;
    }

    public void setCodalumno(String codalumno) {
        this.codalumno = codalumno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCodlibro() {
        return codlibro;
    }

    public void setCodlibro(String codlibro) {
        this.codlibro = codlibro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
