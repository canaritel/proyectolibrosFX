//JAVA FX//
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import negocio.AccesoNegocio;
import negocio.MensajeFX;
import negocio.Variables;

/**
 * FXML Controller class
 *
 * @author telev
 */
public class LoginVistaController implements Initializable {

    public AccesoNegocio CONTROL; //variable de clase CONTROL para el control de las funciones datos DAO
    private MensajeFX mensaje;    //variable tipo MensajeFX para imprimir mensajes en pantalla (ver mértodo creado)
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private JMetro jMetro;        //variable para cambiar la vista de la escena

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnAcceder;
    @FXML
    private CheckBox cboxLocal;
    @FXML
    private CheckBox cboxRemoto;
    @FXML
    private CheckBox cboxFichero;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //En caso de usar radiobutton podemos agruparlos
        //ToggleGroup tg = new ToggleGroup();
        //this.radiobtnlocal.setToggleGroup(tg);
        //this.radiobtnremoto.setToggleGroup(tg);
        //this.radiobtnfichero.setToggleGroup(tg);
        mensaje = new MensajeFX();  //instanciamos la clase mensaje para hacer uso de ella
    }

    //ESTE es el principal método de esta clase, comprobamos y validamos los datos del acceso
    @FXML
    private void comprobarAcceder(ActionEvent event) throws IOException, SQLException {
        if (validarCampos()) {
            validarAcceso();
        }
    }

    //Este método comprobará que checkbox se ha seleccionado
    //el checkbox nos vale para saber que tipo de acceso a la BD se desea (Pool de conexiones)
    //Anotar que este evento está siendo usado por los 3 checkbox creados
    //dicho evento ha sido declarado en el FXML con el mismo nombre
    @FXML
    private void comprobarChbox(ActionEvent event) {
        Object evt = event.getSource();  //creamos un objeto para conocer quien realiza la llamada al evento, usamos el mismo evento en distintos componentes
        if (evt.equals(cboxLocal)) {
            Variables.opcionCheckBox = 1;   //guardamos en la variable el valor seleccinado (dento del método Variables)
            cboxRemoto.setSelected(false);  //deselecciomos el check pulsado de los restantes checkbox
            cboxFichero.setSelected(false); //deselecciomos el check pulsado de los restantes checkbox
        } else if (evt.equals(cboxRemoto)) {
            Variables.opcionCheckBox = 2;    //guardamos en la variable el valor seleccinado (dento del método Variables)
            cboxLocal.setSelected(false);    //deselecciomos el check pulsado de los restantes checkbox
            cboxFichero.setSelected(false);  //deselecciomos el check pulsado de los restantes checkbox
        } else if (evt.equals(cboxFichero)) {
            Variables.opcionCheckBox = 3;  //guardamos en la variable el valor seleccinado (dento del método Variables)
            cboxRemoto.setSelected(false); //deselecciomos el check pulsado de los restantes checkbox
            cboxLocal.setSelected(false);  //deselecciomos el check pulsado de los restantes checkbox
        }
    }

    //Este método NO permite que se introduzcan espacios en blanco en las cadenas de texto a introducir
    //Anotar que este evento está siendo usado por txtUsuario y txtPassword. 
    //dicho evento ha sido declarado en el FXML con el mismo nombre
    @FXML
    private void teclaPulsada(KeyEvent event) {
        Object evt = event.getSource();
        if (evt.equals(txtUsuario)) {
            if (" ".equals(event.getCharacter())) {
                txtUsuario.deletePreviousChar();
            }
        } else if (evt.equals(txtPassword)) {
            if (" ".equals(event.getCharacter())) {
                txtPassword.deletePreviousChar();
            }
        }
    }

    public boolean validarCampos() {
        if (txtUsuario.getText().length() == 0 || txtUsuario.getText().length() > 5) {
            mensaje.printTexto("El campo USUARIO debe tener un tamaño entre 1 a 5 carácteres", "WARNING", posicionX_Y());
            txtUsuario.requestFocus();
            return false;
        }

        if (txtPassword.getText().isEmpty() || txtPassword.getText().length() > 8) {
            mensaje.printTexto("El campo CLAVE debe tener un tamaño entre 1 a 8 carácteres", "WARNING", posicionX_Y());
            txtPassword.requestFocus();
            return false;
        }
        //comprobamos sino tiene seleccionado un checkBox
        if (!seleccionCheckBox()) {
            mensaje.printTexto("Seleccione un sistema de conexión a la Base de Datos", "WARNING", posicionX_Y());
            return false;
        }
        return true; //si llega aquí es que todo está correcto
    }

    public boolean seleccionCheckBox() {
        if (cboxLocal.isSelected() || (cboxRemoto.isSelected() || (cboxFichero.isSelected()))) {
            return true;
        } else {
            Variables.opcionCheckBox = 0;
            return false;
        }
    }

    //Con los datos de usuario y password comprobamos la conexión a la BD
    public void validarAcceso() {
        try {
            //Instanciamos un objeto de tipo acceso negocio
            this.CONTROL = new AccesoNegocio();
            //guardamos la respuesta del método control login pasándole los parámetros necesarios
            String respuesta = CONTROL.login(txtUsuario.getText(), txtPassword.getText());
            //validamos la respuesta
            if (respuesta.equals("OK")) {
                mensaje.printTexto("Acceso correcto", "INFO", posicionX_Y());
                this.cargarPrincipalVista(); //iniciamos correctamente
            } else {
                mensaje.printTexto("Datos de acceso incorrectos", "ERROR", posicionX_Y());
            }
        } catch (SQLException | RuntimeException ex) {
            Logger.getLogger(LoginVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Se ejecuta si los datos de acceso han sido correctos
    public void cargarPrincipalVista() {
        try {
            //cargamos la vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PrincipalVista.fxml"));
            //instanciamos y cargamos el FXML en el padre
            Parent root = loader.load();
            //instanciamos el controlador haciendo uso del nuevo método getController
            PrincipalVistaController ctrPrincipal = loader.getController();
            //creamos la nueva escena que viene del padre
            scene = new Scene(root);
            stage = new Stage();    //creamos la nueva ventana
            stage.setTitle("Menú principal JavaFX"); //ponemos un título
            stage.setScene(scene);
            //Activamos el estilo JMetro, hemos importado la librería que mejora la visualización
            jMetro = new JMetro(jfxtras.styles.jmetro.Style.LIGHT);
            jMetro.setScene(scene);
            //Cargamos el resto de componentes de la vista
            stage.show();   //mostramos la nueva ventana
            //Al activar la nueva ventana es prefirible cerrar la ventana anterior abierta, para lo cual vamos a realizar los siguientes pasos
            //******* Para cerrar la ventana "anterior" realizamos estos pasos *******
            Stage myStage = (Stage) this.btnAcceder.getScene().getWindow();  //creamos una nueva ventana temporal capturando de cualquier btn/txt la scena y ventana
            //se entiende que los btn o txt forman parte de la ventana que deseamos cerrar.
            myStage.close();  //cerramos la ventana y de esta forma solo veremos la nueva ventana
        } catch (IOException ex) {
            System.err.println("Error en el inicio validado " + ex);
        }
    }

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana de mensajes en la pantalla correctamente
    public double[] posicionX_Y() {
        double[] posicion = new double[2];
        Stage myStage = (Stage) this.btnAcceder.getScene().getWindow();
        posicion[0] = myStage.getX();
        posicion[1] = myStage.getY();
        return posicion;
    }

}
