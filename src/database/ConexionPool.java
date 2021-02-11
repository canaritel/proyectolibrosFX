package database;

/**
 *********************************************************************
 *********************************************************************
 ******************* MODELO PATRÓN SINGLETON *************************
 * *******************************************************************
 * *******************************************************************
 * https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
 * *******************************************************************
 */
import java.sql.Connection;
import java.sql.SQLException;
import negocio.Variables;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConexionPool {

    private String DRIVER;
    private String URL;
    private String EXTRA;
    private String DB;
    private String USER;
    private String PASSWORD;
    private static ConexionPool instancia;
    //Basic implementation of javax.sql.DataSource that is configured via JavaBeans properties.
    //This is not the only way to combine the commons-dbcp2 and commons-pool2 packages,
    //but provides a "one stop shopping" solution for basic requirements.
    public BasicDataSource basicDataSource = null;
    ConexionMySQLLocal cml;
    ConexionMySQLRemoto cmr;
    ConexionH2Fichero chf;

    //Vamos a usar el patrón Singleton. 
    //Si da error en el sistema de Reportes nos vemos obligados a declarar este método como público. Ver PrestamoNegocio cnn = new ConnectionPool();
    private ConexionPool() {
    }

    //Establecemos un método sincronizado para evitar problemas de datos en la DB y que cree la conexión
    public synchronized static ConexionPool getInstancia() {
        if (instancia == null) {
            instancia = new ConexionPool();
            return instancia;
        } else {
            return instancia;
        }
    }

    public Connection conectar() throws SQLException {
        //instanciamos las distintos tipos de conexión a la BD usando el ****** PATRÓN FACTORY *******
        if (cml == null) {
            cml = new ConexionMySQLLocal();
        }
        if (cmr == null) {
            cmr = new ConexionMySQLRemoto();
        }
        if (chf == null) {
            chf = new ConexionH2Fichero();
        }

        if (Variables.varLogin == false) { //comprobamos si no estamos logeados
            //llamamos al método para cargar los valores que hayamos seleccionado
            switch (Variables.opcionCheckBox) {
                case 1:
                    basicDataSource = cml.getDataSource();
                    System.out.println("Conexión MySQL Local...");
                    break;

                case 2:
                    basicDataSource = cmr.getDataSource();
                    System.out.println("Conexión MySQL Remota...");
                    break;

                case 3:
                    basicDataSource = chf.getDataSource();
                    System.out.println("Conexión H2 Fichero...");
                    break;
            }
        }

        return this.basicDataSource.getConnection();
    }

    public void desconectar(Connection connection) throws SQLException {
        connection.close();
    }

}
