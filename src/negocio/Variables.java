package negocio;

public class Variables {

    //en estas variables globales vamos a guardar los datos necesarios para ciertas acciones a realizar
    private static int opcionCheckBox = 0;  //0 el valor general para la selección del tipo de BD a usar
    private static boolean varLogin = false;  //establecemos en falso que se hayan logeado, cambiará a true cuando se haya realizado correctamente
    private static int registrosMostrados = 0; //almacenamos en número de registros mostrados en las busquedas de las tablas
    private static String textoFrm;   //almacenamos el texto del título que aparece cuando se abre Frm
    private static int offBotonesAlumnos = 0; //almacenamos el estado de los botones a desactivar. Cuando está en 1 solo activa el nuevo botón de Seleccionar
    private static int offBotonesLibros = 0;

    //Para almacenar datos y compartir entre ventanas distintas usamos estas variables
    //sobre todo se usa para pasar datos de ventanas hijas a padres
    private static String dni, nombre, apellido1, apellido2, codigoAlumno;
    private static String titulo, autor, editorial, asignatura, codigoLibro;

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

    //Creamos los Getter y Setter de los datos a compartir en la aplicación
    public static int getOpcionCheckBox() {
        return opcionCheckBox;
    }

    public static void setOpcionCheckBox(int opcionCheckBox) {
        Variables.opcionCheckBox = opcionCheckBox;
    }

    public static int getRegistrosMostrados() {
        return registrosMostrados;
    }

    public static void setRegistrosMostrados(int registrosMostrados) {
        Variables.registrosMostrados = registrosMostrados;
    }

    public static boolean isVarLogin() {
        return varLogin;
    }

    public static void setVarLogin(boolean varLogin) {
        Variables.varLogin = varLogin;
    }

    public static String getTextoFrm() {
        return textoFrm;
    }

    public static void setTextoFrm(String textoFrm) {
        Variables.textoFrm = textoFrm;
    }

    public static int getOffBotonesAlumnos() {
        return offBotonesAlumnos;
    }

    public static void setOffBotonesAlumnos(int offBotonesAlumnos) {
        Variables.offBotonesAlumnos = offBotonesAlumnos;
    }

    public static int getOffBotonesLibros() {
        return offBotonesLibros;
    }

    public static void setOffBotonesLibros(int offBotonesLibros) {
        Variables.offBotonesLibros = offBotonesLibros;
    }

    public static String getDni() {
        return dni;
    }

    public static void setDni(String dni) {
        Variables.dni = dni;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        Variables.nombre = nombre;
    }

    public static String getApellido1() {
        return apellido1;
    }

    public static void setApellido1(String apellido1) {
        Variables.apellido1 = apellido1;
    }

    public static String getApellido2() {
        return apellido2;
    }

    public static void setApellido2(String apellido2) {
        Variables.apellido2 = apellido2;
    }

    public static String getCodigoAlumno() {
        return codigoAlumno;
    }

    public static void setCodigoAlumno(String codigoAlumno) {
        Variables.codigoAlumno = codigoAlumno;
    }

    public static String getTitulo() {
        return titulo;
    }

    public static void setTitulo(String titulo) {
        Variables.titulo = titulo;
    }

    public static String getAutor() {
        return autor;
    }

    public static void setAutor(String autor) {
        Variables.autor = autor;
    }

    public static String getEditorial() {
        return editorial;
    }

    public static void setEditorial(String editorial) {
        Variables.editorial = editorial;
    }

    public static String getAsignatura() {
        return asignatura;
    }

    public static void setAsignatura(String asignatura) {
        Variables.asignatura = asignatura;
    }

    public static String getCodigoLibro() {
        return codigoLibro;
    }

    public static void setCodigoLibro(String codigoLibro) {
        Variables.codigoLibro = codigoLibro;
    }

}
