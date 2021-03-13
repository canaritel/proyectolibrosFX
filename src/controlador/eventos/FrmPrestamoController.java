package controlador.eventos;

import com.toedter.calendar.JDateChooser;
import controlador.AlumnoVistaController;
import controlador.LibroVistaController;
import entidades.ClassAlumno;
import entidades.ClassLibro;
import entidades.ClassPrestamo;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.MensajeFX;
import negocio.PrestamoNegocio;
import negocio.Variables;

/**
 * FXML Controller class
 *
 * @author telev
 */
public class FrmPrestamoController implements Initializable {

    public PrestamoNegocio CONTROL;  //instanciamos nuestra clase para realizar CRUD
    private int codIdPrestamo;
    private ObservableList<String> itemsCombo;  //creamos un objeto ObservableList para nuestro comboBox
    private ClassAlumno claseAlumno;
    private ClassLibro claseLibro;
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private double[] posicion;    //posición de la ventana en eje X-Y

    @FXML
    private Label lblTextoFrm;
    @FXML
    private ComboBox<String> cmbEstado;
    @FXML
    private TextField txtEstado;
    @FXML
    private ImageView btnCancelar;
    @FXML
    private ImageView bntAceptar;
    @FXML
    private TextField txtCodAlumnoPresta;
    @FXML
    private TextField txtCodLibroPresta;
    @FXML
    private TextField txtFechaPrestamoPresta;
    @FXML
    private TextField txtFechaDevoPresta;
    @FXML
    private TextField infotxtDni;
    @FXML
    private TextField infotxtNombre;
    @FXML
    private TextField infotxtApellido1;
    @FXML
    private TextField infotxtApellido2;
    @FXML
    private TextField infotxtTitulo;
    @FXML
    private TextField infotxtAutor;
    @FXML
    private TextField infotxtEditorial;
    @FXML
    private TextField infotxtAsignatura;
    @FXML
    private Button btnSeleccionAlumno;
    @FXML
    private Button btnSeleccionLibro;
    @FXML
    private DatePicker dateFechaPrestamo;
    @FXML
    private DatePicker dateFechaDevolucion;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CONTROL = new PrestamoNegocio();  //instanciamos la clase Negocio
        lblTextoFrm.setText(Variables.getTextoFrm());  //Envíamos el texto de la variable como título del campo label de nuestra ventana
        this.OffcamposInfo();  //desactivo los campos textfield de información adicional
        if ("ELIMINAR PRÉSTAMO".equals(Variables.getTextoFrm())) { //dependiendo de la acción a realizar (NUEVO/EDITAR/ELIMINAR) activamos/desactivamos botones
            this.OnOffCampos(false);
            this.OnOffBotones(true);
        } else {
            this.OnOffCampos(false);
            this.OnOffBotones(false);
            this.inicializarCombo(); //Inicializamos nuestro comboBox
        }
    }

    @FXML
    private void seleccionCombo(ActionEvent event) {
        //Este evento se produce cuando seleccionamos dentro de nuestro ComboBox
        String textoEstado = cmbEstado.getSelectionModel().getSelectedItem();
        if (!textoEstado.equalsIgnoreCase("Seleccione estado")) {
            txtEstado.setText(textoEstado); //solo enviamos el texto cuando es distinto del texto Promptext
        }
    }

    @FXML
    private void seleccionFechaPresta(ActionEvent event) {
        txtFechaPrestamoPresta.setText(String.valueOf(dateFechaPrestamo.getValue()));
    }

    @FXML
    private void seleccionFechaDevo(ActionEvent event) {
        txtFechaDevoPresta.setText(String.valueOf(dateFechaDevolucion.getValue()));
    }

    @FXML
    private void seleccionAlumno(ActionEvent event) {
        Variables.setOffBotonesAlumnos(1); //activamos este valor para que los botones en la nueva ventana Alumno se desactiven
        try {
            //cargamos la vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AlumnoVista.fxml"));
            //instanciamos y cargamos el FXML en el padre
            Parent root = loader.load();
            //instanciamos al controlador alumno haciendo uso del nuevo método getController
            AlumnoVistaController controladorAlumno = loader.getController();
            //creamos la nueva escena que viene del padre
            scene = new Scene(root);
            stage = new Stage();    //creamos la nueva ventana
            stage.setHeight(600); //asignamos alto
            stage.setTitle("Gestión de Alumnos"); //ponemos un título
            stage.initModality(Modality.APPLICATION_MODAL);  //hacemos que la escena nueva tome el foco y no permita cambiarse de ventana
            stage.setScene(scene); //establecemos la escena
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/icons8_java_duke_50px.png")));
            //posicionamos la nueva ventana
            this.ventanaPosicion(-140, 60);
            //cambiamos la opacidad de la ventana 
            this.cambiarOpacidad(0.5);
            //El programa continua en esta línea cuando la nueva ventana se cierre
            stage.showAndWait(); //mostramos la nueva ventana y esperamos
            //Cuando regresemos quitamos la opacidad
            this.cambiarOpacidad(1);
            Variables.setOffBotonesAlumnos(0); //ponemos a 0 la variable general que activa/desactiva los botones dentro de la ventana Alumno
            pasaDatosAlumnoInfo();  //pasamos los datos capturados a los campos textfield de información adicional
        } catch (IOException ex) {
            System.err.println("Error en el inicio validado " + ex);
        }
    }

    @FXML
    private void seleccionLibro(ActionEvent event) {
        Variables.setOffBotonesLibros(1); //activamos este valor para que los botones en la nueva ventana Libro se desactiven
        try {
            //cargamos la vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/LibroVista.fxml"));
            //instanciamos y cargamos el FXML en el padre
            Parent root = loader.load();
            //instanciamos al controlador libro haciendo uso del nuevo método getController
            LibroVistaController controladorLibro = loader.getController();
            //creamos la nueva escena que viene del padre
            scene = new Scene(root);
            stage = new Stage();    //creamos la nueva ventana
            stage.setHeight(600); //asignamos alto
            stage.setTitle("Gestión de Libros"); //ponemos un título
            stage.initModality(Modality.APPLICATION_MODAL);  //hacemos que la escena nueva tome el foco y no permita cambiarse de ventana
            stage.setScene(scene); //establecemos la escena
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/icons8_java_duke_50px.png")));
            //posicionamos la nueva ventana
            this.ventanaPosicion(-140, 60);
            //cambiamos la opacidad de la ventana 
            this.cambiarOpacidad(0.5);
            //El programa continua en esta línea cuando la nueva ventana se cierre
            stage.showAndWait(); //mostramos la nueva ventana y esperamos
            //Cuando regresemos quitamos la opacidad
            this.cambiarOpacidad(1);
            Variables.setOffBotonesLibros(0); //ponemos a 0 la variable general que activa/desactiva los botones dentro de la ventana Libro
            pasaDatosLibroInfo();
        } catch (IOException ex) {
            System.err.println("Error en el inicio validado " + ex);
        }
    }

    @FXML
    private void cancelarLibro(ActionEvent event) {
        Stage myStage = (Stage) this.txtEstado.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void grabarLibro(ActionEvent event) {
        if (comprobarDatos()) {
            guardarDatos();
        }
    }

    //Este método viene de [Alumno/Libro]VistaController y nos pasa los datos de los campos a editar/eliminar
    public void pasarDatos(ClassPrestamo objPrestamo) {
        claseAlumno = new ClassAlumno(); //debemos instaciar una clase alumno para poder cargar los datos específicos de dichos alumnos
        claseLibro = new ClassLibro();  //debemos instaciar una clase libro para poder cargar los datos específicos de dichos libros
        //Pasamos los datos mientras a las variables que mostramos en pantalla
        codIdPrestamo = objPrestamo.getId();
        txtCodAlumnoPresta.setText(objPrestamo.getCodalumno());
        txtCodLibroPresta.setText(objPrestamo.getCodlibro());
        txtFechaPrestamoPresta.setText(String.valueOf(objPrestamo.getFechapres()));
        txtFechaDevoPresta.setText(String.valueOf(objPrestamo.getFechadevo()));
        txtEstado.setText(objPrestamo.getEstado());

        //cargamos los datos correspondientes Alumnos y Libros. Estos datos están para informar mejor de los registros del CRUD (información adicional)
        int idAlumno, idLibro;
        idAlumno = (Integer.parseInt(txtCodAlumnoPresta.getText()));
        idLibro = (Integer.parseInt(txtCodLibroPresta.getText()));
        //cargamos en los nuevos objetos  alumno y libro
        try {
            claseAlumno = CONTROL.devolverAlumno(idAlumno); //llamamos a un método que nos devuelve un objeto de tipo alumno
            claseLibro = CONTROL.devolverLibro(idLibro);    //llamamos a un método que nos devuelve un objeto de tipo libro
        } catch (SQLException ex) {
            Logger.getLogger(FrmPrestamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //una vez con los datos de los objetos los enviamos a los campos textfield correspondientes
        infotxtDni.setText(claseAlumno.getDni());
        infotxtNombre.setText(claseAlumno.getNombre());
        infotxtApellido1.setText(claseAlumno.getApellido1());
        infotxtApellido2.setText(claseAlumno.getApellido2());
        infotxtTitulo.setText(claseLibro.getTitulo());
        infotxtAutor.setText(claseLibro.getAutor());
        infotxtEditorial.setText(claseLibro.getEditorial());
        infotxtAsignatura.setText(claseLibro.getAsignatura());
    }

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana de mensajes en la pantalla correctamente
    public double[] posicionX_Y() {
        posicion = new double[2];
        Stage myStage = (Stage) this.lblTextoFrm.getScene().getWindow();
        posicion[0] = myStage.getX() + 140;
        posicion[1] = myStage.getY();
        return posicion;
    }

    public void ventanaPosicion(int x, int y) {
        posicion = posicionX_Y();
        posicion[0] = posicion[0] + x;
        posicion[1] = posicion[1] + y;
        stage.setX(posicion[0]);
        stage.setY(posicion[1]);
    }

    public void limpiar() {
        codIdPrestamo = 0;
        txtCodAlumnoPresta.setText("");
        txtCodLibroPresta.setText("");
        txtFechaPrestamoPresta.setText("");
        txtFechaDevoPresta.setText("");
        txtEstado.setText("");
    }

    public void cambiarOpacidad(double valor) {
        Stage myStage = (Stage) this.lblTextoFrm.getScene().getWindow();
        myStage.setOpacity(valor);
    }

    public void inicializarCombo() {
        //Inicializamos nuestro comboBox usando ObservableList
        itemsCombo = FXCollections.observableArrayList();
        itemsCombo.addAll("Nuevo", "Nuevo seminuevo", "Usado", "Usado nuevo", "Usado seminuevo", "Usado estropeado", "Restaurado", "Estropeado");
        cmbEstado.setItems(itemsCombo); //muestro los iconos
        cmbEstado.setPromptText("Seleccione estado");
    }

    private void OnOffCampos(boolean estado) {
        txtCodLibroPresta.setEditable(estado);
        txtFechaDevoPresta.setEditable(estado);
        txtFechaPrestamoPresta.setEditable(estado);
        txtEstado.setEditable(false);
        txtCodAlumnoPresta.setEditable(estado);
    }

    private void OnOffBotones(boolean estado) {
        dateFechaPrestamo.setDisable(estado);
        dateFechaDevolucion.setDisable(estado);
        cmbEstado.setDisable(estado);
        btnSeleccionAlumno.setDisable(estado);
        btnSeleccionLibro.setDisable(estado);
    }

    private void OffcamposInfo() {
        infotxtDni.setDisable(true);
        infotxtNombre.setDisable(true);
        infotxtApellido1.setDisable(true);
        infotxtApellido2.setDisable(true);
        infotxtTitulo.setDisable(true);
        infotxtAutor.setDisable(true);
        infotxtEditorial.setDisable(true);
        infotxtAsignatura.setDisable(true);
    }

    private boolean comprobarDatos() {
        //Comprobamos los campos no estén vacíos
        if (txtCodAlumnoPresta.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Código Alumno' está vacío", "WARNING", posicionX_Y());
            txtCodAlumnoPresta.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        if (txtCodLibroPresta.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Código Libro' está vacío", "WARNING", posicionX_Y());
            txtCodLibroPresta.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtFechaPrestamoPresta.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Fecha Préstamo' está vacío", "WARNING", posicionX_Y());
            txtFechaPrestamoPresta.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtFechaDevoPresta.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Fecha Devolución' está vacío", "WARNING", posicionX_Y());
            txtFechaDevoPresta.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtEstado.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Estado' está vacío", "WARNING", posicionX_Y());
            cmbEstado.requestFocus(); //llevo el 'foco' al campo
            return false;  //devuelvo el código y no continuo
        }

        //comprobamos que la fecha devolución sea mayor a la fecha de préstamo
        if (!this.ComparaFecha(txtFechaPrestamoPresta.getText(), txtFechaDevoPresta.getText())) {
            MensajeFX.printTexto("El campo 'Fecha Devolución' no puede ser igual o menor al campo 'Fecha Préstamo'", "WARNING", posicionX_Y());
            dateFechaDevolucion.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo 
        }

        return true;
    }

    private void guardarDatos() {
        String respuesta;
        try {
            switch (Variables.getTextoFrm()) {
                case "CREAR PRÉSTAMO":
                    respuesta = this.CONTROL.insertar(txtCodAlumnoPresta.getText(), txtCodLibroPresta.getText(), ParseFecha(txtFechaPrestamoPresta.getText()),
                                                      ParseFecha(txtFechaDevoPresta.getText()), txtEstado.getText().strip().toUpperCase());
                    if ("OK".equals(respuesta)) {
                        MensajeFX.printTexto("Préstamo añadido correctamente", "INFO", posicionX_Y());
                        this.limpiar();
                        this.cerrarVentana();
                    } else {
                        MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "EDITAR PRÉSTAMO":
                    respuesta = this.CONTROL.actualizar(codIdPrestamo, txtCodAlumnoPresta.getText(), txtCodLibroPresta.getText(), ParseFecha(txtFechaPrestamoPresta.getText()),
                                                        ParseFecha(txtFechaDevoPresta.getText()), txtEstado.getText().strip().toUpperCase());
                    if ("OK".equals(respuesta)) {
                        MensajeFX.printTexto("Libro editado correctamente", "INFO", posicionX_Y());
                        this.limpiar();
                        this.cerrarVentana();
                    } else {
                        MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "ELIMINAR PRÉSTAMO":
                    if (MensajeFX.printTexto("¿Desea eliminar este registro?", "CONFIRM", posicionX_Y())) {
                        respuesta = this.CONTROL.eliminar(codIdPrestamo);
                        if ("OK".equals(respuesta)) {
                            MensajeFX.printTexto("Libro eliminado correctamente", "INFO", posicionX_Y());
                            this.limpiar();
                            this.cerrarVentana();
                        } else {
                            MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                        }
                    }
                    break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(FrmAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pasaDatosAlumnoInfo() {
        infotxtDni.setText(Variables.getDni());
        infotxtNombre.setText(Variables.getNombre());
        infotxtApellido1.setText(Variables.getApellido1());
        infotxtApellido2.setText(Variables.getApellido2());
        txtCodAlumnoPresta.setText(Variables.getCodigoAlumno());
    }

    private void pasaDatosLibroInfo() {
        infotxtTitulo.setText(Variables.getTitulo());
        infotxtAutor.setText(Variables.getAutor());
        infotxtEditorial.setText(Variables.getEditorial());
        infotxtAsignatura.setText(Variables.getAsignatura());
        txtCodLibroPresta.setText(Variables.getCodigoLibro());
    }

    public java.sql.Date ParseFecha(String fechaPrestamo) {
        //convertimos el campo Date a un formato compatible con SQL
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(fechaPrestamo); //tenemos que usar una variable Date de tipo java.util
            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            return sql;

        } catch (NumberFormatException | ParseException ex) {
            System.err.println("error en la conversión " + ex);
        }
        System.err.println("fecha no parseada");
        return null;
    }

    public boolean ComparaFecha(String fecha1, String fecha2) {
        //comparamos las fechas String usando formato java.util.Date
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date compara1 = format.parse(fecha1); //tenemos que usar una variable Date de tipo java.util
            Date compara2 = format.parse(fecha2); //tenemos que usar una variable Date de tipo java.util
            //realizamos la comparación de fechas, en este caso FechaDevolución contra la FechaPréstamo
            int res = compara2.compareTo(compara1);
            //si el resultado es mayor a 0 es que está correcto (0 es idéntico, -1 es menor)
            return res > 0;
        } catch (NumberFormatException | ParseException ex) {
            System.err.println("error en la conversión " + ex);
        }
        return false;
    }

    //con este método cambiamos la fecha del JDateChooser a tipo String
    public String getFecha(JDateChooser fechaCalendario) {
        SimpleDateFormat FormatoCliente = new SimpleDateFormat("yyyy-MM-dd");

        if (fechaCalendario.getDate() != null) {
            return FormatoCliente.format(fechaCalendario.getDate());
        } else {
            return null;
        }
    }

    private void cerrarVentana() {
        Stage myStage = (Stage) this.txtEstado.getScene().getWindow();
        myStage.close();
    }

}
