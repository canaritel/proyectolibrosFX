package datos.interfaces;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * INTERFACE PARA EL MODELO PATRÓN FACTORY
 *
 */
public interface ConexionInterface {

    public BasicDataSource  getDataSource();
}
