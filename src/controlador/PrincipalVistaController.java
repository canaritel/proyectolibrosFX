package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private ImageView imageView;
    private Image fondoImagen;

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
    @FXML
    private MenuItem mnuiCerrarAccesos;
    @FXML
    private MenuItem mnuiCerrarReportes;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fondoImagen = new Image(getClass().getResourceAsStream("/imagenes/logo javafx2.png")); //cargamos una imagen para el fondo(aparece cuando se cierra la ventana)
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
            cargarFondo1();
        }
        if (evt.equals(mnuiCerrarLibro)) {
            vboxPrincipal.setVisible(false); //hacemos la ventana no visible
            cargarFondo2();
        }
        if (evt.equals(mnuiCerrarPrestamo)) {
            vboxPrincipal.setVisible(false); //hacemos la ventana no visible
            cargarFondo3();
        }
        if (evt.equals(mnuiCerrarAccesos)) {
            vboxPrincipal.setVisible(false); //hacemos la ventana no visible
            cargarFondo4();
        }
        if (evt.equals(mnuiCerrarReportes)) {
            vboxPrincipal.setVisible(false); //hacemos la ventana no visible
            cargarFondo5();
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
    private void cargarFondo1() {
        // Añado imagen de fondo a la ventana
        imageView = new ImageView();
        imageView.setImage(fondoImagen);
        imageView.setFitWidth(800);
        imageView.setFitHeight(500);
        //creamos animación de transición
        ScaleTransition st = new ScaleTransition();
        st.setNode(imageView);
        st.setDuration(new Duration(1000));
        st.setByX(1f);
        st.setByY(1f);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        //cargamos todo en nuestro ventana VBox
        vboxPrincipal.getChildren().setAll(imageView);
        vboxPrincipal.setAlignment(Pos.CENTER);
        vboxPrincipal.setVisible(true);
        System.out.println("Muestro efecto1..");
        st.play();
    }

    private void cargarFondo2() {
        // Añado imagen de fondo a la ventana
        imageView = new ImageView();
        imageView.setImage(fondoImagen);
        imageView.setFitWidth(800);
        imageView.setFitHeight(500);
        //creamos animación de transición
        FadeTransition ft = new FadeTransition();
        ft.setNode(imageView);
        ft.setDuration(new Duration(2000));
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        //cargamos todo en nuestro ventana VBox
        vboxPrincipal.getChildren().setAll(imageView);
        vboxPrincipal.setAlignment(Pos.CENTER);
        vboxPrincipal.setVisible(true);
        System.out.println("Muestro efecto2..");
        ft.play();  //ejecutamos la transición
    }

    private void cargarFondo3() {
        // Añado imagen de fondo a la ventana
        imageView = new ImageView();
        imageView.setImage(fondoImagen);
        imageView.setFitWidth(800);
        imageView.setFitHeight(500);
        //creamos animación de transición
        RotateTransition rt = new RotateTransition();
        rt.setNode(imageView);
        rt.setDuration(new Duration(2000));
        rt.setByAngle(360);
        rt.setCycleCount(1);
        rt.setAutoReverse(true);
        //cargamos todo en nuestro ventana VBox
        vboxPrincipal.getChildren().setAll(imageView);
        vboxPrincipal.setAlignment(Pos.CENTER);
        vboxPrincipal.setVisible(true);
        System.out.println("Muestro efecto3..");
        rt.play();  //ejecutamos la transición
    }

    private void cargarFondo4() {
        // Añado imagen de fondo a la ventana
        imageView = new ImageView();
        imageView.setImage(fondoImagen);
        imageView.setFitWidth(800);
        imageView.setFitHeight(500);
        //creamos animación de transición
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(imageView);
        tt.setDuration(new Duration(2000));
        tt.setFromX(0f);
        tt.setToX(300f);
        tt.setCycleCount(2);
        tt.setAutoReverse(true);
        //cargamos todo en nuestro ventana VBox
        vboxPrincipal.getChildren().setAll(imageView);
        vboxPrincipal.setAlignment(Pos.CENTER);
        vboxPrincipal.setVisible(true);
        System.out.println("Muestro efecto4..");
        tt.play();  //ejecutamos la transición
    }

    private void cargarFondo5() {
        // Añado imagen de fondo a la ventana
        imageView = new ImageView();
        imageView.setImage(fondoImagen);
        imageView.setFitWidth(800);
        imageView.setFitHeight(500);
        //creamos animación de transición
        final Duration SEC_2 = Duration.millis(1000);
        final Duration SEC_3 = Duration.millis(2000);

        PauseTransition pt = new PauseTransition(Duration.millis(1000));
        FadeTransition ft = new FadeTransition(SEC_2);
        ft.setFromValue(1.0f);
        ft.setToValue(0.3f);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        TranslateTransition tt = new TranslateTransition(SEC_2);
        tt.setFromX(0f);
        tt.setToX(300f);
        tt.setCycleCount(2);
        tt.setAutoReverse(true);
        RotateTransition rt = new RotateTransition(SEC_3);
        rt.setByAngle(360f);
        rt.setCycleCount(1);
        rt.setAutoReverse(true);
        ScaleTransition st = new ScaleTransition(SEC_2);
        st.setByX(2.5f);
        st.setByY(2.5f);
        st.setCycleCount(2);
        st.setAutoReverse(true);

        ParallelTransition pT = new ParallelTransition(imageView, ft, tt, rt, st);
        pT.setNode(imageView);

        //cargamos todo en nuestro ventana VBox
        vboxPrincipal.getChildren().setAll(imageView);
        vboxPrincipal.setAlignment(Pos.CENTER);
        vboxPrincipal.setVisible(true);
        System.out.println("Muestro efecto5..");
        pT.play();
    }

}
