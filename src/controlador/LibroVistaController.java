package controlador;

import controlador.eventos.FrmLibroController;
import datos.LibroDAO;
import entidades.ClassLibro;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import negocio.Variables;

public class LibroVistaController implements Initializable {

    private ObservableList<ClassLibro> items; //instanciamos un objeto tipo arrayList especial para JavaFX
    private LibroDAO datos;   //instanciamos la clase AlumnoDAO la cual gestiona las acciones hacia nuestra BD
    private static Scene scene;   //variable de clase Scene donde se produce la acción con los elementos creados
    private static Stage stage;   //variable de clase Stage que es la ventana actual
    private double[] posicion;    //posición de la ventana en eje X-Y
    private JMetro jMetro;  //variable para cambiar la vista de la escena
    private ClassLibro copiaLibro;  //objeto donde guardar datos de la tabla

    @FXML
    private TextField txtFiltrarLibroTabla;
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
    private TableView<ClassLibro> tablaLibro;
    @FXML
    private TableColumn<ClassLibro, Integer> colCodigo;
    @FXML
    private TableColumn<ClassLibro, String> ColTitulo;
    @FXML
    private TableColumn<ClassLibro, String> ColAutor;
    @FXML
    private TableColumn<ClassLibro, String> colEditorial;
    @FXML
    private TableColumn<ClassLibro, String> colAsignatura;
    @FXML
    private TableColumn<ClassLibro, String> colEstado;
    @FXML
    private Label lblInfoLibro;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        offOnBotones(true);
        datos = new LibroDAO();  //instanciamos un objeto para hacer consultas a la BD
        this.cargarTabla(""); //cargamos la tabla de libros
    }

    @FXML
    private void pulsoEnter(KeyEvent event) {
        //keyPressed: cuando se pulsa ENTER en la caja de textoBuscar hacemos la acción de buscar
        Object evt = event.getSource();
        if (evt.equals(txtFiltrarLibroTabla)) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                this.cargarTabla(txtFiltrarLibroTabla.getText());
            }
        }
    }

    @FXML
    private void posicionTeclaTabla(KeyEvent event) {
        //cuando nos desplazamos con el cursor por la tabla capturamos la información de la fila
        ClassLibro claseLibro = tablaLibro.getSelectionModel().getSelectedItem();
        if (claseLibro != null) {  //si no es NULL capturamos los datos de la fila
            copiaLibro = (ClassLibro) claseLibro.clonar();
        }
        offOnBotones(false);
        //si se pulsa ENTER en algún registro de la tabla y offOnbotones está en 1 y el objeto no es nulo
        if (event.getCode().equals(KeyCode.ENTER) && (Variables.getOffBotonesLibros() == 1) && (claseLibro != null)) {
            guardarDatosLibro();  //Esta condición solo se cumple cunando viene de Préstamo
            cerrarVentana();
            //PROCESAMOS COMO EDITAR si se pulsa ENTER en algún registro de la tabla y el objeto no es nulo
        } else if (event.getCode().equals(KeyCode.ENTER) && (Variables.getOffBotonesLibros() == 0) && (claseLibro != null)) {
            Variables.setTextoFrm("EDITAR LIBRO");  //Lo procesamos como Editar
            this.cargarFrmLibro();
        }
    }

    @FXML
    private void posicionRatonTabla(MouseEvent event) {
        //cuando pulsamos con el ratón en algún registro de la tabla capturamos la información de la fila
        ClassLibro claseLibro = tablaLibro.getSelectionModel().getSelectedItem();
        if (claseLibro != null) {  //si no es NULL capturamos los datos de la fila
            copiaLibro = (ClassLibro) claseLibro.clonar();
        }
        offOnBotones(false);
        //Si se pulsa 2 veces en un registro y la variable offOnBotonesLibro está en 1
        if (event.getClickCount() == 2 && (Variables.getOffBotonesLibros() == 1) && (claseLibro != null)) {
            guardarDatosLibro();  //Esta condición solo se cumple cunando viene de Préstamo
            cerrarVentana();
            //PROCESAMOS COMO EDITAR si se pulsa 2 click con el ratón en algún registro de la tabla y el objeto no es nulo
        } else if (event.getClickCount() == 2 && (Variables.getOffBotonesLibros() == 0) && (claseLibro != null)) {
            Variables.setTextoFrm("EDITAR LIBRO");  //Lo procesamos como Editar
            this.cargarFrmLibro();
        }
    }

    @FXML
    private void buscarLibroTabla(ActionEvent event) {
        offOnBotones(true);
        this.cargarTabla(txtFiltrarLibroTabla.getText());
    }

    @FXML
    private void limpiarLibroTabla(ActionEvent event) {
        this.limpiarVista();
        this.cargarTabla("");
    }

    @FXML
    private void nuevoLibroTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.setTextoFrm("CREAR LIBRO");  //Se usará posteriormente en el controlador FrmLibro
        this.cargarFrmLibro();
    }

    @FXML
    private void editarLibroTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.setTextoFrm("EDITAR LIBRO");  //Se usará posteriormente en el controlador FrmLibro
        this.cargarFrmLibro();
    }

    @FXML
    private void eliminarLibroTabla(ActionEvent event) {
        //guardamos en la variable el valor de la acción a ejecutar.
        Variables.setTextoFrm("ELIMINAR LIBRO");  //Se usará posteriormente en el controlador FrmLibro
        this.cargarFrmLibro();
    }

    @SuppressWarnings("unchecked")
    private void cargarTabla(String filtro) {
        //asignamos a cada columna de la tabla el valor de su campo referenciado en ClassLibro
        this.colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.ColTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.ColAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        this.colEditorial.setCellValueFactory(new PropertyValueFactory("editorial"));
        this.colAsignatura.setCellValueFactory(new PropertyValueFactory("asignatura"));
        this.colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        items = datos.listar(filtro);  //llamamos al método "listar" dentro de la clase LibroDAO
        this.tablaLibro.refresh();  //refrescamos los datos de la tabla (sobre todo es interesante cuando actualizamos)
        this.tablaLibro.setItems(items); //mostramos las columnas de la tabla
        lblRegistros.setText("Mostrando " + Variables.getRegistrosMostrados() + " de un total de " + datos.total() + " registros");
    }

    private void cargarFrmLibro() {
        try {
            //cargamos la vista FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FrmLibro.fxml"));
            //instanciamos y cargamos el FXML en el padre
            Parent root = loader.load();
            //instanciamos al controlador FrmLibro haciendo uso del nuevo método getController
            FrmLibroController ctrFrmLibro = loader.getController();
            //creamos la nueva escena que viene del padre
            scene = new Scene(root);
            stage = new Stage();    //creamos la nueva ventana
            stage.setTitle("Crud de Libros"); //ponemos un título
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
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/icons8_java_duke_50px.png")));
            //Pasamos los datos a la nueva ventana FrmAlumno mientras sea distinto a CREAR LIBRO (se usará para EDITAR/ELIMINAR)
            if (!"CREAR LIBRO".equals(Variables.getTextoFrm())) {
                ctrFrmLibro.pasarDatos(copiaLibro);
            }
            stage.showAndWait(); //mostramos la nueva ventana y esperamos
            //El programa continua en esta línea cuando la nueva ventana se cierre
            this.limpiarVista();
            this.cargarTabla("");

        } catch (IOException ex) {
            System.err.println("Error en el inicio validado " + ex);
        }
    }

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana correctamente
    private double[] obtenPosicionX_Y() {
        double[] posicionxy = new double[2];
        //creamos una nueva ventana temporal capturando de cualquier btn/lbl la escena y ventana
        //se entiende que los btn o lbl forman parte de la ventana que deseamos obtener datos
        Stage myStage = (Stage) this.lblRegistros.getScene().getWindow();
        int frmX = 420 / 2; //tamaño ancho componente FrmLibro
        int frmY = 500 / 2; //tamaño alto componente FrmLibro
        int x = (int) (myStage.getWidth() / 2);
        int y = (int) (myStage.getHeight() / 2);
        posicionxy[0] = myStage.getX() + (x - frmX);
        posicionxy[1] = myStage.getY() + (y - frmY);
        return posicionxy;
    }

    private void ventanaPosicion() {
        posicion = obtenPosicionX_Y();
        stage.setX(posicion[0]);
        stage.setY(posicion[1]);
    }

    private void cambiarOpacidad(double valor) {
        Stage myStage = (Stage) this.lblRegistros.getScene().getWindow();
        myStage.setOpacity(valor);
    }

    private void limpiarVista() {
        this.cambiarOpacidad(1);
        offOnBotones(true);
        this.txtFiltrarLibroTabla.setText("");
        Variables.setTextoFrm("");  //el texto superior que aparece al entrar en FrmAlumno
    }

    private void offOnBotones(boolean estado) {
        if (Variables.getOffBotonesLibros() == 0) {
            this.btnEditar.setDisable(estado);
            this.btnEliminar.setDisable(estado);
            lblInfoLibro.setText("Haz doble clic o [ENTER] sobre el registro para Editar");
        } else {
            this.btnNuevo.setDisable(true);
            this.btnEditar.setDisable(true);
            this.btnEliminar.setDisable(true);
            lblInfoLibro.setText("Haz doble clic o [ENTER] sobre el registro a seleccionar");
        }
    }

    private void cerrarVentana() {
        Stage myStage = (Stage) this.lblRegistros.getScene().getWindow();
        myStage.close();
    }

    private void guardarDatosLibro() {
        Variables.setCodigoLibro(String.valueOf(copiaLibro.getCodigo()));
        Variables.setTitulo(copiaLibro.getTitulo());
        Variables.setAutor(copiaLibro.getAutor());
        Variables.setEditorial(copiaLibro.getEditorial());
        Variables.setAsignatura(copiaLibro.getAsignatura());
    }

}
