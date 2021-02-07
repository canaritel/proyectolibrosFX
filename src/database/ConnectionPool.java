package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;
import negocio.Variables;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPool {

    private String DRIVER;
    private String URL;
    private String EXTRA;
    private String DB;
    private String USER;
    private String PASSWORD;
    private static ConnectionPool dataSource;
    //Basic implementation of javax.sql.DataSource that is configured via JavaBeans properties.
    //This is not the only way to combine the commons-dbcp2 and commons-pool2 packages,
    //but provides a "one stop shopping" solution for basic requirements.
    public BasicDataSource basicDataSource = null;

    //Vamos a usar el patrón Singleton. Por un problema con el sistema de Reportes nos vemos
    //obligados a declarar este método como público. Ver PrestamoNegocio cnn = new ConnectionPool();
    public ConnectionPool() {
    }

    //Establecemos un método sincronizado para evitar problemas de datos en la DB y que cree la conexión
    public synchronized static ConnectionPool getDataSource() {
        if (dataSource == null) {
            dataSource = new ConnectionPool();
            return dataSource;
        } else {
            return dataSource;
        }
    }

    public Connection conectar() throws SQLException {
        if (Variables.varLogin == false) { //comprobamos si no estamos logeados
            valoresConexionPool(); //llamamos al método para cargar los valores que hayamos seleccionado
        }
        return this.basicDataSource.getConnection();
    }

    public void desconectar(Connection connection) throws SQLException {
        connection.close();
    }

    public void valoresConexionPool() {
        //Debo usar esta condicional y método para poder recargar los datos en basicDataSource. Si en el acceso Login inicial
        //se introduce erroneamente los datos, y el usario quiere cambiar de tipo de conexión, nos vemos obligados a recargar dichos datos
        //según el valor de la variable opxioncheckbox cargamos los datos
        if (Variables.opcionCheckBox == 1) {
            DRIVER = "com.mysql.jdbc.Driver";
            URL = "jdbc:mysql://localhost:3306/";
            EXTRA = "?zeroDateTimeBehavior=convertToNull&?autoReconnet=true&useSSL=false&serverTimezone=" + TimeZone.getDefault().getID();
            DB = "libros_todojunto";
            USER = "root";
            PASSWORD = "1q2w3e4r5t";
            System.out.println("Acceso MySQL local...");
        }
        if (Variables.opcionCheckBox == 2) {
            DRIVER = "com.mysql.jdbc.Driver";
            URL = "jdbc:mysql://PMYSQL114.dns-servicio.com:3306/";
            EXTRA = "?zeroDateTimeBehavior=convertToNull&?autoReconnet=true&useSSL=false&serverTimezone=" + TimeZone.getDefault().getID();
            DB = "6980251_libreria";
            USER = "root_libreria";
            PASSWORD = "1q2w3e4r5t..";
            System.out.println("Acceso MySQL remoto...");
        }
        if (Variables.opcionCheckBox == 3) {
            DRIVER = "org.h2.Driver";  //usamos una BD de tipo archivo creado por la aplicación H2
            URL = "jdbc:h2:file:./bd_portable/libros_todojunto;IFEXISTS=TRUE";
            EXTRA = "";
            DB = "";
            USER = "root";
            PASSWORD = "1q2w3e4r5t";
            System.out.println("Acceso BD portable...");
        }
        //cargamos los datos dentro de nuestro objeto basicDataSource
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(DRIVER); //crear una nueva clase para almacenar estos datos y cada vez se pulse en el check actulice estos datos
        basicDataSource.setUsername(USER);
        basicDataSource.setPassword(PASSWORD);
        basicDataSource.setUrl(URL + DB + EXTRA);

        basicDataSource.setMinIdle(5); //Sets the minimum number of idle connections in the pool.
        basicDataSource.setMaxIdle(20); //Sets the maximum number of connections that can remain idle in the pool.
        basicDataSource.setMaxTotal(50); //Sets the maximum total number of idle and borrows connections that can be active at the same time.
        basicDataSource.setMaxWaitMillis(-1); //Sets the MaxWaitMillis property. Use -1 to make the pool wait indefinitely.
    }

}
