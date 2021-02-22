package entidades;

import datos.interfaces.ClonarClase;
import java.sql.Date;

public class ClassPrestamoLibro implements ClonarClase {

    private int id;
    private Date fechapres; //declaramos las variables fecha como Date SQL
    private Date fechadevo; //declaramos las variables fecha como Date SQL
    private String codlibro;
    private String titulo;
    private String autor;
    private String editorial;
    private String asignatura;
    private String codalumno; //para completar los datos al editar
    private String estado;  //para completar los datos al editar

    @Override
    public ClonarClase clonar() {
        ClassPrestamoLibro clasePrestamoLibro = null;
        try {
            clasePrestamoLibro = (ClassPrestamoLibro) clone();
        } catch (CloneNotSupportedException e) {
        }
        return clasePrestamoLibro;
    }

    public ClassPrestamoLibro() {
    }

    public ClassPrestamoLibro(int id, Date fechapres, Date fechadevo, String codlibro, String titulo, String autor, String editorial, String asignatura, String codalumno, String estado) {
        this.id = id;
        this.fechapres = fechapres;
        this.fechadevo = fechadevo;
        this.codlibro = codlibro;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.asignatura = asignatura;
        this.codalumno = codalumno;
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

    public String getCodlibro() {
        return codlibro;
    }

    public void setCodlibro(String codlibro) {
        this.codlibro = codlibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getCodalumno() {
        return codalumno;
    }

    public void setCodalumno(String codalumno) {
        this.codalumno = codalumno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
