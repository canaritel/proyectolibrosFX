package controlador;

import controlador.eventos.FrmPrestamoController;
import datos.AlumnoDAO;
import datos.PrestamoDAO;
import entidades.ClassAlumno;
import entidades.ClassPrestamo;
import entidades.ClassPrestamoAlumno;
import entidades.ClassPrestamoLibro;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import negocio.Variables;

/**
 * FXML Controller class
 *
 * @author telev
 */
public class PrestamoVistaController implements Initializable {

    private ObservableList<ClassPrestamo> items; //instanciamos un objeto tipo arrayList especial para JavaFX
    private ObservableList<ClassPrestamoAlumno> itemsA; //instanciamos un objeto tipo arrayList especial para JavaFX
    private ObservableList<ClassPrestamoLibro> itemsL; //instanciamos un objeto tipo arrayList especial para JavaFX
    private PrestamoDAO datos;   //instanciamos la clase AlumnoDAO la cual gestiona las acciones hacia nuestra BD
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private double[] posicion;    //posición de la ventana en eje X-Y
    private JMetro jMetro;  //variable para cambiar la vista de la escena
    private int registro;   //variable donde guardar datos de la tabla
    private ClassPrestamo copiaPrestamo;  //objeto donde guardar datos de la tabla
    private ClassPrestamoAlumno copiaPrestamoA;  //objeto donde guardar datos de la tabla
    private ClassPrestamoLibro copiaPrestamoLibro;  //objeto donde guardar datos de la tabla

    private ClassAlumno objetoAlumno;
    private AlumnoDAO alumnoDao;

    @FXML
    private Tab tabListaPrestaCodigo;
    @FXML
    private TextField txtFiltrarPrestamoTabla;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblRegistros;
    @FXML
    private TableView<ClassPrestamo> tablaPrestamo;
    @FXML
    private TableColumn<ClassPrestamo, Integer> colRegistro;
    @FXML
    private TableColumn<ClassPrestamo, String> ColCodAlumno;
    @FXML
    private TableColumn<ClassPrestamo, String> ColCodLibro;
    @FXML
    private TableColumn<ClassPrestamo, Date> colFechaPrestamo;
    @FXML
    private TableColumn<ClassPrestamo, Date> colFechaDevolucion;
    @FXML
    private TableColumn<ClassPrestamo, String> colEstado;

    /////////// declaro valores para tab lista alumnos
    @FXML
    private Tab tabListaPrestaAlumno;
    @FXML
    private TextField txtFiltrarPrestamoTablaA;
    @FXML
    private Button btnBuscarA;
    @FXML
    private Button btnLimpiarA;
    @FXML
    private Button btnNuevoA;
    @FXML
    private Button btnEditarA;
    @FXML
    private Button btnEliminarA;
    @FXML
    private Label lblRegistrosA;
    @FXML
    private TableView<ClassPrestamoAlumno> tablaPrestamoA;
    @FXML
    private TableColumn<ClassPrestamoAlumno, Integer> colRegistroA;
    @FXML
    private TableColumn<ClassPrestamoAlumno, Date> colFechaPrestamoA;
    @FXML
    private TableColumn<ClassPrestamoAlumno, Date> colFechaDevolucionA;
    @FXML
    private TableColumn<ClassPrestamoAlumno, String> ColCodAlumnoA;
    @FXML
    private TableColumn<ClassPrestamoAlumno, String> ColDniA;
    @FXML
    private TableColumn<ClassPrestamoAlumno, String> colNombreA;
    @FXML
    private TableColumn<ClassPrestamoAlumno, String> ColApellido1A;
    @FXML
    private TableColumn<ClassPrestamoAlumno, String> ColApellido2A;
    @FXML
    private TableColumn<ClassPrestamoAlumno, String> ColCodLibroA;
    @FXML
    private TableColumn<ClassPrestamoAlumno, String> ColEstadoA;

    /////////// declaro valores para tab lista libros
    @FXML
    private Tab tabListaPrestaLibro;
    @FXML
    private TextField txtFiltrarPrestamoTablaLibro;
    @FXML
    private Button btnBuscaPrestaLibro;
    @FXML
    private Button btnLimpiaPrestaLibro;
    @FXML
    private Button btnNuevoPrestaLibro;
    @FXML
    private Button btnEditaPrestaLibro;
    @FXML
    private Button btnEliminaPrestaLibro;
    @FXML
    private Label lblRegistroPrestaLibro;
    @FXML
    private TableView<ClassPrestamoLibro> tablaPrestamoLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, Integer> colRegistroPrestaLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, Date> colFPrestamoPrestaLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, Date> colFDevolucionPrestaLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, String> ColCodLibroPrestaLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, String> ColTituloPrestaLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, String> ColAutorPrestaLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, String> colEditorialPrestaLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, String> ColAsignaturaPrestaLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, String> ColCodAlumnoPrestaLibro;
    @FXML
    private TableColumn<ClassPrestamoLibro, String> ColEstadoPrestaLibro;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botonOffEditaElimina(true);
        botonOffEditaEliminaAlumno(true);
        botonOffEditaEliminaLibro(true);
        inicializaTabla();  //inicializamos todas las columnas de las tablas
        datos = new PrestamoDAO();  //instanciamos un objeto para hacer consultas a la BD
        this.cargarTabla(""); //cargamos la tabla de alumnos
    }

    @FXML
    private void pulsoEnter(KeyEvent event) {
        //keyPressed: cuando se pulsa ENTER en la caja de textoBuscar hacemos la acción de buscar
        Object evt = event.getSource();
        if (evt.equals(txtFiltrarPrestamoTabla)) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                this.cargarTabla(txtFiltrarPrestamoTabla.getText());
            }
        }
        if (evt.equals(txtFiltrarPrestamoTablaA)) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                this.cargarTabla(txtFiltrarPrestamoTablaA.getText());
            }
        }
        if (evt.equals(txtFiltrarPrestamoTablaLibro)) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                this.cargarTabla(txtFiltrarPrestamoTablaLibro.getText());
            }
        }
    }

    @FXML
    private void posicionTeclaTabla(KeyEvent event) {
        //cuando nos desplazamos con el cursor por la tabla capturamos la información de la fila
        if (tabListaPrestaCodigo.isSelected()) {
            ClassPrestamo clasePrestamo = tablaPrestamo.getSelectionModel().getSelectedItem();
            if (clasePrestamo != null) {  //si no es NULL capturamos los datos de la fila
                copiaPrestamo = (ClassPrestamo) clasePrestamo.clonar();
            }
            botonOffEditaElimina(false);
        }

        if (tabListaPrestaAlumno.isSelected()) {
            ClassPrestamoAlumno clasePrestamoA = tablaPrestamoA.getSelectionModel().getSelectedItem();
            if (clasePrestamoA != null) {  //si no es NULL capturamos los datos de la fila
                copiaPrestamoA = (ClassPrestamoAlumno) clasePrestamoA.clonar();
            }
            botonOffEditaEliminaAlumno(false);
        }

        if (tabListaPrestaLibro.isSelected()) {
            ClassPrestamoLibro clasePrestamoLibro = tablaPrestamoLibro.getSelectionModel().getSelectedItem();
            if (clasePrestamoLibro != null) {  //si no es NULL capturamos los datos de la fila
                copiaPrestamoLibro = (ClassPrestamoLibro) clasePrestamoLibro.clonar();
            }
            botonOffEditaEliminaLibro(false);
        }
    }

    @FXML
    private void posicionRatonTabla(MouseEvent event) {
        //cuando pulsamos con el ratón en algún registro de la tabla capturamos la información de la fila
        if (tabListaPrestaCodigo.isSelected()) {
            ClassPrestamo clasePrestamo = tablaPrestamo.getSelectionModel().getSelectedItem();
            if (clasePrestamo != null) {  //si no es NULL capturamos los datos de la fila
                copiaPrestamo = (ClassPrestamo) clasePrestamo.clonar();
            }
            botonOffEditaElimina(false);
        }

        if (tabListaPrestaAlumno.isSelected()) {
            ClassPrestamoAlumno clasePrestamoA = tablaPrestamoA.getSelectionModel().getSelectedItem();
            if (clasePrestamoA != null) {  //si no es NULL capturamos los datos de la fila
                copiaPrestamoA = (ClassPrestamoAlumno) clasePrestamoA.clonar();
            }
            botonOffEditaEliminaAlumno(false);
        }

        if (tabListaPrestaLibro.isSelected()) {
            ClassPrestamoLibro clasePrestamoLibro = tablaPrestamoLibro.getSelectionModel().getSelectedItem();
            if (clasePrestamoLibro != null) {  //si no es NULL capturamos los datos de la fila
                copiaPrestamoLibro = (ClassPrestamoLibro) clasePrestamoLibro.clonar();
            }
            botonOffEditaEliminaLibro(false);
        }
    }

    @FXML
    private void buscarPrestamoTabla(ActionEvent event) {
        if (tabListaPrestaCodigo.isSelected()) {
            botonOffEditaElimina(true);
            this.cargarTabla(txtFiltrarPrestamoTabla.getText());
        }

        if (tabListaPrestaAlumno.isSelected()) {
            botonOffEditaEliminaAlumno(true);
            this.cargarTabla(txtFiltrarPrestamoTablaA.getText());
        }

        if (tabListaPrestaLibro.isSelected()) {
            botonOffEditaEliminaLibro(true);
            this.cargarTabla(txtFiltrarPrestamoTablaLibro.getText());
        }

    }

    @FXML
    private void limpiarPrestamoTabla(ActionEvent event) {
        this.limpiarVista();
        this.cargarTabla("");
    }

    @FXML
    private void nuevoPrestamoTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.textoFrm = "CREAR PRÉSTAMO";  //Se usará posteriormente en el controlador FrmPrestamo
        this.cargarFrmPrestamo();
    }

    @FXML
    private void editarPrestamoTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.textoFrm = "EDITAR PRÉSTAMO";  //Se usará posteriormente en el controlador FrmPrestamo
        this.cargarFrmPrestamo();
    }

    @FXML
    private void eliminarPrestamoTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.textoFrm = "ELIMINAR PRÉSTAMO";  //Se usará posteriormente en el controlador FrmPrestamo
        this.cargarFrmPrestamo();
    }

    @FXML
    private void pulsoTabListaAlumno(Event event) {
        this.cargarTabla(""); //cargamos la tabla de préstamos alumnos
    }

    @FXML
    private void pulsoTabListaLibro(Event event) {
        this.cargarTabla(""); //cargamos la tabla de préstamos libros
    }

    private void cargarTabla(String filtro) {
        //asignamos a cada columna de la tabla el valor de su campo referenciado en ClassLibro
        if (tabListaPrestaCodigo.isSelected()) {
            items = datos.listar(filtro);  //llamamos al método "listar" dentro de la clase PrestamoDAO
            this.tablaPrestamo.refresh();  //refrescamos los datos de la tabla (sobre todo es interesante cuando actualizamos)
            this.tablaPrestamo.setItems(items); //mostramos las columnas de la tabla
            lblRegistros.setText("Mostrando " + Variables.registrosMostrados + " de un total de " + datos.total() + " registros");
        } else if (tabListaPrestaAlumno.isSelected()) {
            itemsA = datos.listarAlumno(filtro);  //llamamos al método "listar" dentro de la clase PrestamoDAO
            this.tablaPrestamoA.refresh();  //refrescamos los datos de la tabla (sobre todo es interesante cuando actualizamos)
            this.tablaPrestamoA.setItems(itemsA); //mostramos las columnas de la tabla
            lblRegistrosA.setText("Mostrando " + Variables.registrosMostrados + " de un total de " + datos.total() + " registros");
        } else if (tabListaPrestaLibro.isSelected()) {
            itemsL = datos.listarLibro(filtro);  //llamamos al método "listar" dentro de la clase PrestamoDAO
            this.tablaPrestamoLibro.refresh();  //refrescamos los datos de la tabla (sobre todo es interesante cuando actualizamos)
            this.tablaPrestamoLibro.setItems(itemsL); //mostramos las columnas de la tabla
            lblRegistroPrestaLibro.setText("Mostrando " + Variables.registrosMostrados + " de un total de " + datos.total() + " registros");
        }
    }

    private void cargarFrmPrestamo() {
        try {
            //cargamos la vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FrmPrestamo.fxml"));
            //instanciamos y cargamos el FXML en el padre
            Parent root = loader.load();
            //instanciamos al controlador FrmLibro haciendo uso del nuevo método getController
            FrmPrestamoController ctrFrmPrestamo = loader.getController();
            //creamos la nueva escena que viene del padre
            scene = new Scene(root);
            stage = new Stage();    //creamos la nueva ventana
            stage.setTitle("Crud de Préstamos"); //ponemos un título
            stage.initModality(Modality.APPLICATION_MODAL);  //hacemos que la escena nueva tome el foco y no permita cambiarse de ventana
            stage.setScene(scene); //establecemos la escena
            //Activamos el estilo JMetro, hemos importado la librería que mejora la visualización
            jMetro = new JMetro(jfxtras.styles.jmetro.Style.LIGHT);
            jMetro.setScene(scene);
            //posicionamos la nueva ventana
            this.ventanaPosicion();
            //cambiamos la opacidad de la ventana anterior
            this.cambiarOpacidad(0.5);
            stage.setResizable(false); //no permitimos que la ventana cambie de tamaño
            stage.initStyle(StageStyle.UTILITY); //desactivamos maximinar y minimizar
            //Pasamos los datos a la nueva ventana FrmAlumno
            if (!"CREAR PRÉSTAMO".equals(Variables.textoFrm)) {
                ctrFrmPrestamo.pasarDatos(copiaPrestamo);
            }
            //El programa continua en esta línea cuando la nueva ventana se cierre
            stage.showAndWait(); //mostramos la nueva ventana y esperamos
            this.limpiarVista();
            this.cargarTabla("");

        } catch (IOException ex) {
            System.err.println("Error en el inicio validado " + ex);
        }
    }

    @SuppressWarnings("unchecked")
    private void inicializaTabla() {
        this.colRegistro.setCellValueFactory(new PropertyValueFactory("id"));
        this.ColCodAlumno.setCellValueFactory(new PropertyValueFactory("codalumno"));
        this.ColCodLibro.setCellValueFactory(new PropertyValueFactory("codlibro"));
        this.colFechaPrestamo.setCellValueFactory(new PropertyValueFactory("fechapres"));
        this.colFechaDevolucion.setCellValueFactory(new PropertyValueFactory("fechadevo"));
        this.colEstado.setCellValueFactory(new PropertyValueFactory("estado"));

        this.colRegistroA.setCellValueFactory(new PropertyValueFactory("id"));
        this.colFechaPrestamoA.setCellValueFactory(new PropertyValueFactory("fechapres"));
        this.colFechaDevolucionA.setCellValueFactory(new PropertyValueFactory("fechadevo"));
        this.ColCodAlumnoA.setCellValueFactory(new PropertyValueFactory("codalumno"));
        this.ColDniA.setCellValueFactory(new PropertyValueFactory("dni"));
        this.colNombreA.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.ColApellido1A.setCellValueFactory(new PropertyValueFactory("apellido1"));
        this.ColApellido2A.setCellValueFactory(new PropertyValueFactory("apellido2"));
        this.ColCodLibroA.setCellValueFactory(new PropertyValueFactory("codlibro"));
        this.ColEstadoA.setCellValueFactory(new PropertyValueFactory("estado"));

        this.colRegistroPrestaLibro.setCellValueFactory(new PropertyValueFactory("id"));
        this.colFPrestamoPrestaLibro.setCellValueFactory(new PropertyValueFactory("fechapres"));
        this.colFDevolucionPrestaLibro.setCellValueFactory(new PropertyValueFactory("fechadevo"));
        this.ColCodLibroPrestaLibro.setCellValueFactory(new PropertyValueFactory("codalumno"));
        this.ColTituloPrestaLibro.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.ColAutorPrestaLibro.setCellValueFactory(new PropertyValueFactory("autor"));
        this.colEditorialPrestaLibro.setCellValueFactory(new PropertyValueFactory("editorial"));
        this.ColAsignaturaPrestaLibro.setCellValueFactory(new PropertyValueFactory("asignatura"));
        this.ColCodAlumnoPrestaLibro.setCellValueFactory(new PropertyValueFactory("codalumno"));
        this.ColEstadoPrestaLibro.setCellValueFactory(new PropertyValueFactory("estado"));
    }

    public void botonOffEditaEliminaAlumno(boolean estado) {
        this.btnEditarA.setDisable(estado);
        this.btnEliminarA.setDisable(estado);
    }

    public void botonOffEditaEliminaLibro(boolean estado) {
        this.btnEditaPrestaLibro.setDisable(estado);
        this.btnEliminaPrestaLibro.setDisable(estado);
    }

    public void botonOffEditaElimina(boolean estado) {
        this.btnEditar.setDisable(estado);
        this.btnEliminar.setDisable(estado);
    }

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana correctamente
    private double[] obtenPosicionX_Y() {
        double[] posicionxy = new double[2];
        //creamos una nueva ventana temporal capturando de cualquier btn/lbl la escena y ventana
        //se entiende que los btn o lbl forman parte de la ventana que deseamos obtener datos
        Stage myStage = (Stage) this.lblRegistros.getScene().getWindow();
        posicionxy[0] = myStage.getX();
        posicionxy[1] = myStage.getY();
        return posicionxy;
    }

    private void ventanaPosicion() {
        posicion = obtenPosicionX_Y();
        posicion[0] = posicion[0] + 110;
        posicion[1] = posicion[1] - 30;
        stage.setX(posicion[0]);
        stage.setY(posicion[1]);
    }

    private void cambiarOpacidad(double valor) {
        Stage myStage = (Stage) this.lblRegistros.getScene().getWindow();
        myStage.setOpacity(valor);
    }

    private void limpiarVista() {
        this.cambiarOpacidad(1);
        this.txtFiltrarPrestamoTabla.setText("");
        this.txtFiltrarPrestamoTablaA.setText("");
        this.txtFiltrarPrestamoTablaLibro.setText("");
        botonOffEditaElimina(true);
        botonOffEditaEliminaAlumno(true);
        botonOffEditaEliminaLibro(true);
        Variables.textoFrm = "";  //el texto superior que aparece al entrar en FrmPrestamo
    }

}
