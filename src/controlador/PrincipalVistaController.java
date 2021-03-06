package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author telev
 */
public class PrincipalVistaController implements Initializable {

    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private double[] posicion;  //posición de la ventana en eje X-Y
    //String cargarVista; //elejimos que tipo de vista cargamos
    private VBox ventana; //creamos un objeto de tipo VBox (Todas nuestras ventanas hijas comienzan por este tipo de elemento)

    @FXML
    private Font x3;
    @FXML
    private Label lblTextoInferior;
    @FXML
    private VBox vboxPrincipal;
    @FXML
    private MenuItem mnuiCerrarAlumno;
    @FXML
    private MenuItem mnuiCerrarLibro;
    @FXML
    private MenuItem mnuiCerrarPrestamo;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void CrudAlumno(ActionEvent event) {
        this.cargarVistaAlumno();
    }

    @FXML
    private void CrudLibro(ActionEvent event) {
        this.cargarVistaLibro();
    }

    @FXML
    private void CrudPrestamos(ActionEvent event) {
        this.cargarVistaPrestamo();
    }

    @FXML
    private void CerrarVentana(ActionEvent event) {
        Object evt = event.getSource();
        if (evt.equals(mnuiCerrarAlumno)) {
            vboxPrincipal.setVisible(false); //hacemos la ventana no visible
        }
        if (evt.equals(mnuiCerrarPrestamo)) {
            vboxPrincipal.setVisible(false); //hacemos la ventana no visible
        }
        if (evt.equals(mnuiCerrarLibro)) {
            vboxPrincipal.setVisible(false); //hacemos la ventana no visible
        }
    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        Platform.exit(); //Es ideal para cuando se cierre la aplicación se ejecute el proceso stop()
    }

    public void cargarVistaAlumno() {
        try {
            ventana = FXMLLoader.load(getClass().getResource("/vista/AlumnoVista.fxml"));
            vboxPrincipal.getChildren().setAll(ventana);
            vboxPrincipal.setVisible(true);

        } catch (IOException ex) {
            System.err.println("Error la carga de vista alumno " + ex);
        }
    }

    /*   CAMBIAMOS NUESTRO CÓDGIGO POR EL NUEVO (MIRAR ARRIBA)
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
            stage.setTitle("Gestión de Alumnos"); //ponemos un título
            stage.initModality(Modality.APPLICATION_MODAL);  //hacemos que la escena nueva tome el foco y no permita cambiarse de ventana
            stage.setScene(scene); //establecemos la escena
            //posicionamos la nueva ventana
            this.ventanaPosicion(90, 105);
            //cambiamos la opacidad de la ventana 
            this.cambiarOpacidad(0.5);
            //El programa continua en esta línea cuando la nueva ventana se cierre
            stage.showAndWait(); //mostramos la nueva ventana y esperamos
            //Cuando regresemos quitamos la opacidad
            this.cambiarOpacidad(1);

        } catch (IOException ex) {
            System.err.println("Error en el inicio validado " + ex);
        }
     */
    public void cargarVistaLibro() {
        try {
            ventana = FXMLLoader.load(getClass().getResource("/vista/LibroVista.fxml"));
            vboxPrincipal.getChildren().setAll(ventana);
            vboxPrincipal.setVisible(true);

        } catch (IOException ex) {
            System.err.println("Error la carga de vista libro" + ex);
        }
    }

    /*
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
            stage.setTitle("Gestión de Libros"); //ponemos un título
            stage.initModality(Modality.APPLICATION_MODAL);  //hacemos que la escena nueva tome el foco y no permita cambiarse de ventana
            stage.setScene(scene); //establecemos la escena
            //posicionamos la nueva ventana
            this.ventanaPosicion(90, 105);
            //cambiamos la opacidad de la ventana 
            this.cambiarOpacidad(0.5);
            //El programa continua en esta línea cuando la nueva ventana se cierre
            stage.showAndWait(); //mostramos la nueva ventana y esperamos
            //Cuando regresemos quitamos la opacidad
            this.cambiarOpacidad(1);

        } catch (IOException ex) {
            System.err.println("Error en el inicio validado " + ex);
        }
     */
    public void cargarVistaPrestamo() {
        try {
            ventana = FXMLLoader.load(getClass().getResource("/vista/PrestamoVista.fxml"));
            vboxPrincipal.getChildren().setAll(ventana);
            vboxPrincipal.setVisible(true);

        } catch (IOException ex) {
            System.err.println("Error la carga de vista préstamo " + ex);
        }
    }

    /*
        try {
            //cargamos la vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PrestamoVista.fxml"));
            //instanciamos y cargamos el FXML en el padre
            Parent root = loader.load();
            //instanciamos al controlador libro haciendo uso del nuevo método getController
            PrestamoVistaController controladorPrestamo = loader.getController();
            //creamos la nueva escena que viene del padre
            scene = new Scene(root);
            stage = new Stage();    //creamos la nueva ventana
            stage.setTitle("Gestión de Préstamos"); //ponemos un título
            stage.initModality(Modality.APPLICATION_MODAL);  //hacemos que la escena nueva tome el foco y no permita cambiarse de ventana
            stage.setScene(scene); //establecemos la escena
            //posicionamos la nueva ventana
            this.ventanaPosicion(0, 100);
            //cambiamos la opacidad de la ventana 
            this.cambiarOpacidad(0.5);
            //El programa continua en esta línea cuando la nueva ventana se cierre
            stage.showAndWait(); //mostramos la nueva ventana y esperamos
            //Cuando regresemos quitamos la opacidad
            this.cambiarOpacidad(1);

        } catch (IOException ex) {
            System.err.println("Error en el inicio validado " + ex);
        }
     */
}
