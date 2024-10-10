import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Baraja baraja = new Baraja(40);
        panel.add(baraja.getImagenCarta());
        frame.setSize(800,800);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);






    }
}
