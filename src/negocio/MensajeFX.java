//JAVA FX//
package negocio;

import javafx.scene.control.Alert;

public class MensajeFX {

    private final String INFO = "INFO";
    private final String WARNING = "WARNING";
    private final String ERROR = "ERROR";

    public MensajeFX() {
    }

    public void printTexto(String mensaje, String tipo, double[] posicion) {
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        //ponemos las coordenadas a las ventanas de mensajes
        posicion[0]= posicion[0] + 40;  //ajustamos la posición en eje X
        posicion[1]= posicion[1] + 150; //ajustamos la posición en el eje Y
        alertInfo.setX(posicion[0]);   //seteamos la posición en eje X de la ventana de mensaje
        alertInfo.setY(posicion[1]);   //seteamos la posición en el eje Y de la ventana de mensaje
        alertWarning.setX(posicion[0]);
        alertWarning.setY(posicion[1]);
        alertError.setX(posicion[0]);
        alertError.setY(posicion[1]);
        if ("INFO".equals(tipo)) {
            alertInfo.setHeaderText(null);
            alertInfo.setTitle("Sistema Login");
            alertInfo.setContentText(mensaje);
            alertInfo.showAndWait();
        }
        if ("WARNING".equals(tipo)) {
            alertWarning.setHeaderText(null);
            alertWarning.setTitle("Sistema Login");
            alertWarning.setContentText(mensaje);
            alertWarning.showAndWait();
        }
        if ("ERROR".equals(tipo)) {
            alertError.setHeaderText(null);
            alertError.setTitle("Sistema Login");
            alertError.setContentText(mensaje);
            alertError.showAndWait();
        }
    }

}
