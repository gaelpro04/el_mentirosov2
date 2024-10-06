import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Interfaz {

    private JFrame frame;

    public Interfaz()
    {
        makeFrame();
    }

    public void makeFrame() {
        frame = new JFrame("El mentiroso");
        frame.setSize(1920,1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cerrar aplicación al cerrar la ventana
        Container containerPane = frame.getContentPane();
        containerPane.setLayout(new BorderLayout());

        // Título del juego
        JLabel tituloJuego = new JLabel("El mentiroso", SwingConstants.CENTER);
        tituloJuego.setFont(new Font("Serif", Font.BOLD, 24));
        containerPane.add(tituloJuego, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón para jugar
        JButton botonJugar = new JButton("Jugar");
        botonJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonJugar.setPreferredSize(new Dimension(150, 40));  // Tamaño fijo
        botonJugar.setMaximumSize(botonJugar.getPreferredSize());
        botonJugar.addActionListener(event -> botonJugar());
        panelBotones.add(botonJugar);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));  // Espacio entre los botones

        // Botón para acerca de:
        JButton botonAcerca = new JButton("Acerca de");
        botonAcerca.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonAcerca.setPreferredSize(new Dimension(150, 40));  // Tamaño fijo
        botonAcerca.setMaximumSize(botonAcerca.getPreferredSize());
        botonAcerca.addActionListener(event -> botonAcerca());
        panelBotones.add(botonAcerca);

        // Agregar el panel de botones al centro
        containerPane.add(panelBotones, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(400, 300);  // Tamaño inicial de la ventana
        frame.setLocationRelativeTo(null);  // Centrar la ventana en la pantalla
        frame.setVisible(true);
    }

    public void botonJugar()
    {
        System.out.println("Simon");
    }

    public void botonAcerca()
    {
        System.out.println("Simon2");
    }

    public static void main(String[] args) {
        new Interfaz();
    }
}
