package negocio;

public class Variables {

    //en estas variables globales vamos a guardar los datos necesarios para ciertas acciones a realizar
    public static int opcionCheckBox = 0;  //0 el valor general para la selección del tipo de BD a usar
    public static boolean varLogin = false;  //establecemos en falso que se hayan logeado, cambiará a true cuando se haya realizado correctamente
    public static int registrosMostrados = 0; //almacenamos en número de registros mostrados en las busquedas de las tablas
    public static String textoFrmAlumno;   //almacenamos el texto del título que aparece cuando se abre FrmAlumnoNuevo
    public static String textoFrmLibro;   //almacenamos el texto del título que aparece cuando se abre FrmLibro
    //guardamos en una varible la ruta hacia nuestro jrxml
    //la ruta que vamos acceder es dentro de nuestra aplicación
    // public static final String rutaAlumno = "reportes/reporte_prestamos.jrxml";
   // public static final String rutaLibro = "reportes/reporte_prestamos_libro.jrxml";

}
