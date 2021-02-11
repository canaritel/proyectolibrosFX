package database;

/**
 *
 * USAMOS EL PATRÓN FACTORY
 *
 */
import datos.interfaces.ConexionInterface;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConexionH2Fichero implements ConexionInterface {

    private static BasicDataSource basicDataSource = null;
    private final String DRIVER = "org.h2.Driver";  //usamos una BD de tipo archivo creado por la aplicación H2
    private final String URL = "jdbc:h2:file:./bd_portable/libros_todojunto;IFEXISTS=TRUE";
    private final String EXTRA = "";
    private final String DB = "";
    private final String USER = "root";
    private final String PASSWORD = "1q2w3e4r5t";

    public ConexionH2Fichero() {
        if (basicDataSource == null) {
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

    @Override
    public BasicDataSource getDataSource() {
        return basicDataSource;
    }

}
