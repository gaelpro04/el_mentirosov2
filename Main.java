import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Carta carta = new Carta("oros", 10, false, "BarajaEspañola/10 oros.png");
        frame.setSize(800,800);
        panel.add(carta.getImagenCarta());
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);






    }
}
