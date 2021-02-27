package controlador.eventos;

import entidades.ClassAlumno;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import negocio.AlumnoNegocio;
import negocio.MensajeFX;
import negocio.Variables;

public class FrmAlumnoController implements Initializable {

    private AlumnoNegocio CONTROL;  //instanciamos nuestra clase para realizar CRUD
    private int idRegistro;
    String dniAnterior;

    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtApellido2;
    @FXML
    private ImageView btnCancelar;
    @FXML
    private ImageView bntAceptar;
    @FXML
    private Label lblTextoFrm;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CONTROL = new AlumnoNegocio();  //instanciamos la clase AlumnoNegocio
        lblTextoFrm.setText(Variables.getTextoFrm());  //Envíamos el texto de la variable como título del campo label de nuestra ventana
        if ("ELIMINAR ALUMNO".equals(Variables.getTextoFrm())) { //dependiendo de la acción a realizar (NUEVO/EDITAR/ELIMINAR) activamos/desactivamos botones
            txtDni.setEditable(false);
            txtNombre.setEditable(false);
            txtApellido1.setEditable(false);
            txtApellido2.setEditable(false);
        } else {
            txtDni.setEditable(true);
            txtNombre.setEditable(true);
            txtApellido1.setEditable(true);
            txtApellido2.setEditable(true);
        }
    }

    @FXML
    private void cancelarAlumno(ActionEvent event) {
        Stage myStage = (Stage) this.txtDni.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void grabarAlumno(ActionEvent event) {
        if (comprobarDatos()) {
            guardarDatos();
        }
    }

    //Este método viene de AlumnoVistaController y nos pasa los datos de los campos a editar/eliminar
    public void pasarDatos(ClassAlumno objAlumno) {
        idRegistro = objAlumno.getIdRegistro();
        txtDni.setText(objAlumno.getDni());
        dniAnterior = objAlumno.getDni();
        txtNombre.setText(objAlumno.getNombre());
        txtApellido1.setText(objAlumno.getApellido1());
        txtApellido2.setText(objAlumno.getApellido2());
    }

    public boolean comprobarDatos() {
        //Comprobamos los campos no estén vacíos
        if (txtDni.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'DNI' está vacío", "WARNING", posicionX_Y());
            txtDni.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtNombre.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Nombre' está vacío", "WARNING", posicionX_Y());
            txtNombre.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtApellido1.getText().isEmpty()) {
            MensajeFX.printTexto("El campo 'Apellido1' está vacío", "WARNING", posicionX_Y());
            txtApellido1.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        //Comprobamos los campos no excendan del tañano permitido
        if (txtApellido2.getText().length() > 21) {
            MensajeFX.printTexto("El campo 'Apellido2' excede del tamaño de 21 carácteres", "WARNING", posicionX_Y());
            txtApellido2.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        if (txtApellido1.getText().length() > 21) {
            MensajeFX.printTexto("El campo 'Apellido1' excede del tamaño de 21 carácteres", "WARNING", posicionX_Y());
            txtApellido1.requestFocus();
            return false; //devuelvo el código y no continuo
        }
        if (txtNombre.getText().length() > 26) {
            MensajeFX.printTexto("El campo 'Nombre' excede del tamaño de 26 carácteres", "WARNING", posicionX_Y());
            txtNombre.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }
        if (txtDni.getText().length() > 14) {
            MensajeFX.printTexto("El campo 'Dni' excede del tamaño de 14 carácteres", "WARNING", posicionX_Y());
            txtDni.requestFocus(); //llevo el 'foco' al campo
            return false; //devuelvo el código y no continuo
        }

        return true;  //si llega aquí es que todo está correcto
    }

    private void guardarDatos() {
        String respuesta;
        try {
            switch (Variables.getTextoFrm()) {
                case "CREAR ALUMNO":
                    respuesta = this.CONTROL.insertar(txtDni.getText().strip().toUpperCase(), txtNombre.getText().strip().toUpperCase(),
                                                      txtApellido1.getText().strip().toUpperCase(), txtApellido2.getText().strip().toUpperCase());
                    if ("OK".equals(respuesta)) {
                        MensajeFX.printTexto("Alumno añadido correctamente", "INFO", posicionX_Y());
                        this.limpiar();

                    } else {
                        MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "EDITAR ALUMNO":
                    respuesta = this.CONTROL.actualizar(idRegistro, txtDni.getText().strip().toUpperCase(), dniAnterior, txtNombre.getText().strip().toUpperCase(),
                                                        txtApellido1.getText().strip().toUpperCase(), txtApellido2.getText().strip().toUpperCase());
                    if ("OK".equals(respuesta)) {
                        MensajeFX.printTexto("Alumno editado correctamente", "INFO", posicionX_Y());
                        this.limpiar();
                        Stage myStage = (Stage) this.txtDni.getScene().getWindow();
                        myStage.close();
                    } else {
                        MensajeFX.printTexto(respuesta, "ERROR", posicionX_Y());
                    }
                    break;

                case "ELIMINAR ALUMNO":
                    if (MensajeFX.printTexto("¿Desea eliminar este registro?", "CONFIRM", posicionX_Y())) {
                        respuesta = this.CONTROL.eliminar(idRegistro);
                        if ("OK".equals(respuesta)) {
                            MensajeFX.printTexto("Alumno eliminado correctamente", "INFO", posicionX_Y());
                            this.limpiar();
                            Stage myStage = (Stage) this.txtDni.getScene().getWindow();
                            myStage.close();
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

    //este método obtiene la posición de la actual ventana en coordenadas x, y
    //vamos a usar estos datos para posicionar la ventana de mensajes en la pantalla correctamente
    public double[] posicionX_Y() {
        double[] posicion = new double[2];
        Stage myStage = (Stage) this.txtDni.getScene().getWindow();
        posicion[0] = myStage.getX() - 40;
        posicion[1] = myStage.getY();
        return posicion;
    }

    public void limpiar() {
        idRegistro = 0;
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
    }

}
