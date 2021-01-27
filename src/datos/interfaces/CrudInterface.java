package datos.interfaces;

import java.util.List;


public interface CrudInterface<T> {
    public List<T> listar(String texto);
    public boolean insertar(T obj);
    public boolean actualizar(T obj);
    public boolean eliminar(T obj);
    public int total();
    public boolean existe(String texto);
}
