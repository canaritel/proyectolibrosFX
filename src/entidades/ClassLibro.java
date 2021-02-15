package entidades;

import datos.interfaces.ClonarClase;
import java.util.Objects;

//implementamos la clase de tipo ClonarClase, que es la interface creada para copiar clases
//la opción de clonar es muy interesante cuado deseemos copiar clases
public class ClassLibro implements ClonarClase {

    private int codigo;
    private String titulo;
    private String autor;
    private String editorial;
    private String asignatura;
    private String estado;

    @Override
    public ClonarClase clonar() {
        ClassLibro claseLibro = null;
        try {
            claseLibro = (ClassLibro) clone();
        } catch (CloneNotSupportedException e) {
        }
        return claseLibro;
    }

    public ClassLibro() {
    }

    public ClassLibro(int codigo, String titulo, String autor, String editorial, String asignatura, String estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.asignatura = asignatura;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final ClassLibro other = (ClassLibro) obj;
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        if (!Objects.equals(this.editorial, other.editorial)) {
            return false;
        }
        if (!Objects.equals(this.asignatura, other.asignatura)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        return true;
    }

}
