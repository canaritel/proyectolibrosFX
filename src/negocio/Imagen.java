package negocio;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/***
* La inserción de una imagen de fondo en nuestro JFRame ha tenido su complejidad ya que usando otros métodos
* alternativos no conseguía se visualizara en sistemas macOS. Era debido a que al hacer uso de la clase io.file
* para cargar la imagen, me obligaba a añadir dicha imagen de forma externa en mi proyecto JAR, es decir aparte
* del fichero .JAR debía incluir la imagen/es que se iban a usar. Mi intención para este proyecto es que TODO
* se compile en UN único fichero JAR, el cual incluya todos los ficheros necesarios (mirar Build.xml) sin necesidad
* de incluir de forma externa imágenes. Esto se ha logrado y es totalmente compatible es macOS, Linux y Windows.
***/
public class Imagen extends javax.swing.JPanel {

    int x, y;

    public Imagen(JFrame jPanel1) {
        this.x = jPanel1.getWidth();
        this.y = jPanel1.getHeight();
        this.setSize(x, y);
    }

    @Override
    public void paint(Graphics g) {
        //cargamos nuestra imagen de fondo en nuestro paquete JAR
        ImageIcon Img = new ImageIcon(getClass().getResource("/presentacion/imagenes/fondo3_M.jpg"));
        g.drawImage(Img.getImage(), 0, -40, x, y, null);
    }

}
