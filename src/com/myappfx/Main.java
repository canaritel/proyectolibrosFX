package com.myappfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.layout.Pane;

public class Main extends Application {

    private static Pane root;  // //ventana principal madre de la cula no vamos hacer uso directo
    private static Scene scene;   //donde se produce la acción con los elementos creados
    private static Stage stage;   //ventana actual

    @Override
    public void init() {  //Primer método que se ejecuta al instanciar la clase
        //Usado para validaciones con bases de datos
        //Cargar configuración inicial
        //NO vale para cargar componentes de nuestra interfaz gráfica
        System.out.println("Método init()");
    }

    @Override
    public void start(Stage stage) throws IOException {  //Cargamos todo lo referente a nuestra interfaz gráfica
        scene = new Scene(loadFXML("/vista/LoginVista")); //sino indicamos el ancho y alto lo coge directamente del diseño establecido en el FXML
        //scene = new Scene(loadFXML("/vista/LoginVista"), 640, 480); //Podemos establecer el tamaño de la ventana, siendo incluso distinto al tamaño creado
        stage.setScene(scene);
        stage.setTitle("Creado en JavaFX");
        stage.show();
        System.out.println("Método start()");
        System.out.println("Java version: " + System.getProperty("java.version") + "\nJavaFX version: " + System.getProperty("javafx.version"));
        stage.setResizable(false);
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

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml)); //especifica una "raíz dinámica" para el archivo FXML
    }

    public static Parent loadFXML(String fxml) throws IOException {
        System.out.println("Cargamos el fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void cargarLogo() {
        ImageView imageLogo;
        InputStream inputStream;
        inputStream = getClass().getResourceAsStream("/imagenes/icono_java.png");

        //Image nuevaImage = inputStream;
        //   Image imagen = new Image();
        //  imageLogo = new ImageView(imagen);
    }

}
