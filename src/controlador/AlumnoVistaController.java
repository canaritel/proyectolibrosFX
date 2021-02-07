package controlador;

import datos.AlumnoDAO;
import entidades.ClassAlumno;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import negocio.AlumnoNegocio;
import negocio.Variables;

public class AlumnoVistaController implements Initializable {

    private AlumnoNegocio CONTROL;
    private AlumnoDAO datos;

    @FXML
    private TextField txtFiltrar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblRegistros;
    @FXML
    private TableView<ClassAlumno> tablaAlumno;
    @FXML
    private TableColumn<ClassAlumno, Integer> colRegistro;
    @FXML
    private TableColumn<ClassAlumno, String> ColDni;
    @FXML
    private TableColumn<ClassAlumno, String> ColNombre;
    @FXML
    private TableColumn<ClassAlumno, String> colApellido1;
    @FXML
    private TableColumn<ClassAlumno, String> colApellido2;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Inicializo AlumnoVistaController");
        //asignamos a cada columna de la tabla el valor de su campo referenciado en ClassAlumno
        this.colRegistro.setCellValueFactory(new PropertyValueFactory("idRegistro"));
        this.ColDni.setCellValueFactory(new PropertyValueFactory("dni"));
        this.ColNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colApellido1.setCellValueFactory(new PropertyValueFactory("apellido1"));
        this.colApellido2.setCellValueFactory(new PropertyValueFactory("apellido2"));
    }

    @FXML
    private void buscar(ActionEvent event) {
        cargarTabla(txtFiltrar.getText());
    }

    @FXML
    private void limpiar(ActionEvent event) {
        txtFiltrar.setText("");
        cargarTabla("");
    }

    @FXML
    private void editar(ActionEvent event) {
    }

    @FXML
    private void nuevo(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void pulsoEnter(KeyEvent event) {
        //cuando se pulsa ENTER en la caja de textoBuscar hacemos una acción
        Object evt = event.getSource();
        if (evt.equals(txtFiltrar)) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                cargarTabla(txtFiltrar.getText());
            }
        }

    }

    public void cargarTabla(String filtro) {
        datos = new AlumnoDAO();
        ObservableList<ClassAlumno> items = datos.listar(filtro);
        this.tablaAlumno.setItems(items);
        lblRegistros.setText("Mostrando " + Variables.registrosMostrados + " de un total de " + datos.total() + " registros");
    }
}

/*
 @Override
    public void keyReleased(KeyEvent e) {
        //cuando nos desplazamos con el cursor por la tabla, conocemos la posición de la fila
        if (e.getSource().equals(vistaAlumno.tablaListado)) {
            vistaAlumno.tablaListado.getSelectedRow();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //cuando se pulsa ENTER en la caja de textoBuscar hacemos una acción
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {  //txtbuscar es el único evento que tenemos implementado con un KeyListener
            ctr.listar(vistaAlumno.txtBuscar.getText());
        }
    }
*/
