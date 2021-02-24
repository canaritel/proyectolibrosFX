package negocio;

public class Variables {

    //en estas variables globales vamos a guardar los datos necesarios para ciertas acciones a realizar
    public static int opcionCheckBox = 0;  //0 el valor general para la selección del tipo de BD a usar
    public static boolean varLogin = false;  //establecemos en falso que se hayan logeado, cambiará a true cuando se haya realizado correctamente
    public static int registrosMostrados = 0; //almacenamos en número de registros mostrados en las busquedas de las tablas
    public static String textoFrm;   //almacenamos el texto del título que aparece cuando se abre Frm
    public static int offBotonesAlumnos = 0; //almacenamos el estado de los botones a desactivar. Cuando está en 1 solo activa el nuevo botón de Seleccionar
    public static int offBotonesLibros = 0;
    public static int posx, posy; //vamos a usarla para almacenar posiciones de ventanas en coordenadas x,y
    //Para almacenar datos y compartir entre ventanas distintas usamos estas variables
    //sobre todo se usa para pasar datos de ventanas hijas a padres
    public static String dni, nombre, apellido1, apellido2, codigoAlumno;
    public static String titulo, autor, editorial, asignatura, codigoLibro;

//guardamos en una varible la ruta hacia nuestro jrxml
    //la ruta que vamos acceder es dentro de nuestra aplicación
    // public static final String rutaAlumno = "reportes/reporte_prestamos.jrxml";
    /// public static final String rutaLibro = "reportes/reporte_prestamos_libro.jrxml";
    public static void limpiarDatos() {
        Variables.nombre = "";
        Variables.dni = "";
        Variables.apellido1 = "";
        Variables.apellido2 = "";
        Variables.codigoAlumno = "";

        Variables.titulo = "";
        Variables.autor = "";
        Variables.editorial = "";
        Variables.asignatura = "";
        Variables.codigoLibro = "";
    }

}
