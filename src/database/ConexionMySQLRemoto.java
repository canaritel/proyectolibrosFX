package database;

/**
 *
 * USAMOS EL PATRÓN FACTORY PARA LLAMAR A ESTE MÉTODO
 *
 */
import datos.interfaces.ConexionInterface;
import java.util.TimeZone;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConexionMySQLRemoto implements ConexionInterface {

    private static BasicDataSource basicDataSource = null;
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://PMYSQL114.dns-servicio.com:3306/";
    private final String EXTRA = "?zeroDateTimeBehavior=convertToNull&?autoReconnet=true&useSSL=false&serverTimezone=" + TimeZone.getDefault().getID();
    private final String DB = "6980251_libreria";
    private final String USER = "root_libreria";
    private final String PASSWORD = "1q2w3e4r5t..";

    public ConexionMySQLRemoto() {
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
