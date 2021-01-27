package negocio;

import datos.AccesoDAO;
import entidades.ClassAcceso;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class AccesoNegocio {

    private final AccesoDAO DATOS;
    private final ClassAcceso objeto;
    private DefaultTableModel modeloTabla;
    private int registrosMostrados;

    public AccesoNegocio() throws SQLException {
        this.DATOS = new AccesoDAO();
        this.objeto = new ClassAcceso();
        this.registrosMostrados = 0;
    }

    public DefaultTableModel listar() throws SQLException {
        //recorremos el arralist con los datos almacenados y creamos la tabla
        List<ClassAcceso> lista = new ArrayList<>();
        lista.addAll(DATOS.listar());

        String[] titulos = {"Usuario", "Clave"};
        this.modeloTabla = new DefaultTableModel(null, titulos) {
            //para evitar la tabla pueda ser editada directamente
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] registro = new String[2];
        this.registrosMostrados = 0;
        //recorremos el arrayList
        for (ClassAcceso item : lista) {
            registro[0] = item.getUsuario();
            registro[1] = item.getClave();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }

    public String actualizar(String usuario, String clave) throws SQLException {
        objeto.setUsuario(usuario);
        objeto.setClave(clave);
        if (DATOS.actualizar(objeto)) {
            return "OK";
        } else {
            return "Error en la actualización";
        }
    }

    public String insertar(String usuario, String clave) throws SQLException {
        //comprobamos si existe el usuario a insertar
        if (DATOS.existe(usuario)) {
            return "El registro con ese USUARIO ya existe";
        } else {
            objeto.setUsuario(usuario);
            objeto.setClave(clave);
            if (DATOS.insertar(objeto)) {
                return "OK";
            } else {
                return "Error en el registro";
            }
        }
    }

    public String eliminar(String usuario) throws SQLException {
        objeto.setUsuario(usuario);
        if (DATOS.eliminar(objeto)) {
            return "OK";
        } else {
            return "Error al eliminar registro";
        }
    }

    public String login(String user, String password) throws SQLException {
        if (DATOS.login(user, password)) {
            return "OK";
        } else {
            return "No existe coincidencia de acceso";
        }
    }

    public int total() throws SQLException {
        return DATOS.total();
    }

    public int totalMostrados() {
        return this.registrosMostrados;
    }

}
