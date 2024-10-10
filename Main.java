import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Carta carta = new Carta("oro",1,true,"BarajaEspañola/2 oros.png");
        panel.add(carta.getImagenCarta());

        frame.setSize(800,800);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);






    }
}
