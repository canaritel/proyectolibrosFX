package com.myappfx;

import controlador.LoginVistaController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import jfxtras.styles.jmetro.JMetro;

public class Main extends Application {

    private static Scene scene;   //donde se produce la acción con los elementos creados
    private static Stage stage;   //el maro de la ventana actual
    private static JMetro jMetro; //para aplicar efectos de ventana de manera simple
    /*       Para compartir datos entre ventanas (sobre todo de ventanas hijas a padres) es complejo. Para lo cual vamos hacer uso de
    *        una nueva librería EventBus. Dicha librería nos ofrece métodos y soluciones actuales.
    *        https://greenrobot.org/eventbus/
    *        https://github.com/greenrobot/EventBus#add-eventbus-to-your-project
     */
    //public static EventBus eventos = new EventBus();  //declaramos un objeto EventBus especial para compartir datos entre ventanas

    @Override
    public void init() {  //Primer método que se ejecuta al instanciar la clase
        //Usado para validaciones con bases de datos
        //Cargar configuración inicial
        //NO vale para cargar componentes de nuestra interfaz gráfica
        System.out.println("Método init()");
    }

    @Override
    public void start(Stage stage) throws IOException {  //Cargamos todo lo referente a nuestra interfaz gráfica
        //cargamos la vista FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/LoginVista.fxml"));
        //instanciamos y cargamos el FXML en el padre
        Parent root = loader.load();
        //instanciamos al controlador FrmAlumnoNuevo haciendo uso del nuevo método getController
        LoginVistaController ctrLogin = loader.getController();
        //creamos la nueva escena que viene del padre
        scene = new Scene(root);
        stage = new Stage();    //creamos la nueva ventana
        stage.setScene(scene); //establecemos la escena
        //Activamos el estilo JMetro, hemos importado la librería que mejora la visualización
        jMetro = new JMetro(jfxtras.styles.jmetro.Style.LIGHT);
        jMetro.setScene(scene);
        //Cargamos el resto de componentes de la vista
        stage.setTitle("Creado en JavaFX");
        stage.setResizable(false); //no permitimos que la ventana cambie de tamaño
        stage.show(); //mostramos la ventana
        System.out.println("Método start()");
        System.out.println("Java version: " + System.getProperty("java.version") + "\nJavaFX version: " + System.getProperty("javafx.version"));

    }

    @Override
    public void stop() { //Creamos procesos de finalización
        System.out.println("Método stop()");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void cerrar() {
        Platform.exit(); //Es ideal para cuando se cierre la aplicación se ejecute el proceso stop()
        //Es decir no tendremos que usar la función System.exit(0) ya que debemos sustituirlo por el nuevo Platform.exit()
    }

}
