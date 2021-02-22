package controlador.eventos;

import entidades.ClassLibro;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import negocio.LibroNegocio;
import negocio.MensajeFX;
import negocio.Variables;

/**
 * FXML Controller class
 *
 * @author telev
 */
public class FrmLibroController implements Initializable {

    private MensajeFX mensaje;    //variable tipo MensajeFX para imprimir mensajes en pantalla
    private LibroNegocio CONTROL;  //instanciamos nuestra clase para realizar CRUD
    private int codLibro;
    private ObservableList<String> itemsCombo;  //creamos un objeto ObservableList para nuestro comboBox
    private ClassLibro copiaLibro;

    @FXML
    private Label lblTextoFrm;
    @FXML
    private ImageView btnCancelar;
    @FXML
    private ImageView bntAceptar;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtAutor;
    @FXML
    private TextField txtEditorial;
    @FXML
    private TextField txtAsignatura;
    @FXML
    private ComboBox<String> cmbEstado;
    @FXML
    private TextField txtEstado;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mensaje = new MensajeFX();  //instanciamos la clase mensaje para hacer uso de ella
        CONTROL = new LibroNegocio();  //instanciamos la clase AlumnoNegocio
        lblTextoFrm.setText(Variables.textoFrm);  //Envíamos el texto de la variable como título del campo label de nuestra ventana

        if ("ELIMINAR LIBRO".equals(Variables.textoFrm)) { //dependiendo de la acción a realizar (NUEVO/EDITAR/ELIMINAR) activamos/desactivamos botones
            txtTitulo.setEditable(false);
            txtAutor.setEditable(false);
            txtEditorial.setEditable(false);
            txtAsignatura.setEditable(false);
            txtEstado.setEditable(false);
            txtEstado.setDisable(true);
            cmbEstado.setEditable(false);
            cmbEstado.setDisable(true);
        } else {
            txtTitulo.setEditable(true);
            txtAutor.setEditable(true);
            txtEditorial.setEditable(true);
            txtAsignatura.setEditable(true);
            txtEstado.setEditable(false);
            cmbEstado.setEditable(true);
            cmbEstado.setDisable(false);
            this.inicializarCombo(); //Inicializamos nuestro comboBox
        }
    }

    @FXML
    private void cancelarLibro(ActionEvent event) {
        Stage myStage = (Stage) this.txtTitulo.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void grabarLibro(ActionEvent event) {
        if (comprobarDatos()) {
            guardarDatos();
        }
    }

    @FXML
    private void seleccionCombo(ActionEvent event) {
        //Este evento se produce cuando seleccionamos dentro de nuestro ComboBox
        String textoEstado = cmbEstado.getSelectionModel().getSelectedItem();
        if (!"ELIMINAR LIBRO".equals(Variables.textoFrm) || (!textoEstado.equalsIgnoreCase("Seleccione estado"))) {
            txtEstado.setText(textoEstado); //solo enviamos el texto cuando es distinto de Eliminar
        }
    }

    //Este método viene de LibroVistaController y nos pasa los datos de los campos a editar/eliminar
    public void pasarDatos(ClassLibro objLibro) {
        codLibro = objLibro.getCodigo();
        txtTitulo.setText(objLibro.getTitulo());
        txtAutor.setText(objLibro.getAutor());
        txtEditorial.setText(objLibro.getEditorial());
        txtAsignatura.setText(objLibro.getAsignatura());
        txtEstado.setText(objLibro.getEstado());
        copiaLibro = (ClassLibro) objLibro.clonar(); //vamos a usar esta copia para el proceso de editar
    }

    private boolean comprobarDatos() {
        //Comprobamos los campos no estén vacíos
        if (txtTitulo.getText().isEmpty()) {
            mensaje.printTexto("El campo 'Título' está vacío", "WARNING", posicionX_Y());
            txtTitulo.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        if (txtAutor.getText().isEmpty()) {
            mensaje.printTexto("El campo 'Autor' está vacío", "WARNING", posicionX_Y());
            txtAutor.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtEditorial.getText().isEmpty()) {
            mensaje.printTexto("El campo 'Editorial' está vacío", "WARNING", posicionX_Y());
            txtEditorial.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtAsignatura.getText().isEmpty()) {
            mensaje.printTexto("El campo 'Asignatura' está vacío", "WARNING", posicionX_Y());
            txtAsignatura.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtEstado.getText().isEmpty()) {
            mensaje.printTexto("El campo 'Estado' está vacío", "WARNING", posicionX_Y());
            cmbEstado.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }

        //Comprobamos los campos no excendan del tañano permitido
        if (txtTitulo.getText().length() > 68) {
            mensaje.printTexto("El campo 'Título' excede del tamaño de 68 carácteres", "WARNING", posicionX_Y());
            txtTitulo.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        if (txtAutor.getText().length() > 40) {
            mensaje.printTexto("El campo 'Autor' excede del tamaño de 40 carácteres", "WARNING", posicionX_Y());
            txtAutor.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        if (txtEditorial.getText().length() > 36) {
            mensaje.printTexto("El campo 'Editorial' excede del tamaño de 36 carácteres", "WARNING", posicionX_Y());
            txtEditorial.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtAsignatura.getText().length() > 38) {
            mensaje.printTexto("El campo 'Asignatura' excede del tamaño de 38 carácteres", "WARNING", posicionX_Y());
            txtAsignatura.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }

        return true;
    }

    private void guardarDatos() {
        String respuesta;
        try {
            switch (Variables.textoFrm) {
                case "CREAR LIBRO":
                    respuesta = this.CONTROL.insertar(txtTitulo.getText().strip().toUpperCase(), txtAutor.getText().strip().toUpperCase(),
                                                      txtEditorial.getText().strip().toUpperCase(), txtAsignatura.getText().strip().toUpperCase(),
                                                      txtEstado.getText().strip().toUpperCase());
                    if ("OK".equals(respuesta)) {
                        mensaje.printTexto("Libro añadido correctamente", "INFO", posicionX_Y());
                        this.limpiar();

                    } else {
                        mensaje.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "EDITAR LIBRO":
                    respuesta = this.CONTROL.actualizar(codLibro, txtTitulo.getText().strip().toUpperCase(), txtAutor.getText().strip().toUpperCase(),
                                                        txtEditorial.getText().strip().toUpperCase(), txtAsignatura.getText().strip().toUpperCase(),
                                                        txtEstado.getText().strip().toUpperCase(), copiaLibro);
                    if ("OK".equals(respuesta)) {
                        mensaje.printTexto("Libro editado correctamente", "INFO", posicionX_Y());
                        this.limpiar();
                        Stage myStage = (Stage) this.txtTitulo.getScene().getWindow();
                        myStage.close();
                    } else {
                        mensaje.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "ELIMINAR LIBRO":
                    if (mensaje.printTexto("¿Desea eliminar este registro?", "CONFIRM", posicionX_Y())) {
                        respuesta = this.CONTROL.eliminar(codLibro);
                        if ("OK".equals(respuesta)) {
                            mensaje.printTexto("Libro eliminado correctamente", "INFO", posicionX_Y());
                            this.limpiar();
                            Stage myStage = (Stage) this.txtTitulo.getScene().getWindow();
                            myStage.close();
                        } else {
                            mensaje.printTexto(respuesta, "ERROR", posicionX_Y());
                        }
                    }
                    break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(FrmAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana de mensajes en la pantalla correctamente
    public double[] posicionX_Y() {
        double[] posicion = new double[2];
        Stage myStage = (Stage) this.txtTitulo.getScene().getWindow();
        posicion[0] = myStage.getX() - 40;
        posicion[1] = myStage.getY();
        return posicion;
    }

    public void limpiar() {
        codLibro = 0;
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtAsignatura.setText("");
        txtEstado.setText("");
    }

    public void inicializarCombo() {
        //Inicializamos nuestro comboBox usando ObservableList
        itemsCombo = FXCollections.observableArrayList();
        itemsCombo.addAll("Nuevo", "Nuevo seminuevo", "Usado", "Usado nuevo", "Usado seminuevo", "Usado estropeado", "Restaurado", "Estropeado");
        cmbEstado.setItems(itemsCombo); //muestro los iconos
        cmbEstado.setPromptText("Seleccione estado");
    }

}
