package entidades;

import datos.interfaces.ClonarClase;
import java.sql.Date;  //importante usar SQL.DATE

public class ClassPrestamo implements ClonarClase {

    private int id;
    private String codalumno;
    private String codlibro;
    //No vamos a usar java.util.Date ya que está obsoleto. Para más información click en la web de referencia abajo
    // https://www.campusmvp.es/recursos/post/como-manejar-correctamente-fechas-en-java-el-paquete-java-time.aspx
    private Date fechapres; //declaramos las variables fecha como Date SQL
    private Date fechadevo; //declaramos las variables fecha como Date SQL
    //private LocalDate fechapres;  //Pendiente declarar los campos fecha de tipo LocalDate, https://www.eninsoft.com/manejo-de-fechas-y-horas-con-java-8-y-versiones-posteriores/
    private String estado;

    @Override
    public ClonarClase clonar() {
        ClassPrestamo clasePrestamo = null;
        try {
            clasePrestamo = (ClassPrestamo) clone();
        } catch (CloneNotSupportedException e) {
        }
        return clasePrestamo;
    }

    public ClassPrestamo() {
    }

    public ClassPrestamo(int id, String codalumno, String codlibro, Date fechapres, Date fechadevo, String estado) {
        this.id = id;
        this.codalumno = codalumno;
        this.codlibro = codlibro;
        this.fechapres = fechapres;
        this.fechadevo = fechadevo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodalumno() {
        return codalumno;
    }

    public void setCodalumno(String codalumno) {
        this.codalumno = codalumno;
    }

    public String getCodlibro() {
        return codlibro;
    }

    public void setCodlibro(String codlibro) {
        this.codlibro = codlibro;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ClassPrestamo{" + "id=" + id + ", codalumno=" + codalumno + ", codlibro=" + codlibro + ", fechapres=" + fechapres + ", fechadevo=" + fechadevo + ", estado=" + estado + '}';
    }

}
