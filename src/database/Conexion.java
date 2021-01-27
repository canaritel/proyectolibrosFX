package database;

//En este método vamos a usar el patrón Singleton
// ************************************************MÉTODO SIN POOL DE CONEXIONES*************
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String EXTRA = "?zeroDateTimeBehavior=convertToNull&?autoReconnet=true&useSSL=false";
    private final String DB = "libros_todojunto";
    private final String USER = "root";
    private final String PASSWORD = "1q2w3e4r5t";
    
    
    
    public Connection cadena;
    public static Conexion instancia;

    //El patrón Singleton se implementa mediante una clase con un constructor privado.
    private Conexion() {
        this.cadena = null;
    }

    public Connection conectar() {
        try {
            Class.forName(DRIVER);
            this.cadena = DriverManager.getConnection(URL + DB + EXTRA, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        return this.cadena;
    }

    public void desconectar() {
        try {
            this.cadena.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    //Establecemos un método sincronizado para evitar problemnas datos en la DB y que cree la conexión
    public synchronized static Conexion getInstacia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

}
*/