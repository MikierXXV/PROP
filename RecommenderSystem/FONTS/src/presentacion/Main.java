package presentacion;


/**
 *
 * @author Oriol Martí Jariod
 */

public class Main {
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater (
                new Runnable() {
                    public void run() {
                        ControladorPresentacion CP = new ControladorPresentacion();
                        CP.run();

                    }});
    }
}