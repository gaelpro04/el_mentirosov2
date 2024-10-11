import java.awt.*;
import javax.swing.*;

public class Interfaz {

    private JFrame frame;

    public Interfaz()
    {
        makeFrame();
    }

    /**
     * Método que elabora el marco con los botones de sucesos.
     */
    public void makeFrame() {
        frame = new JFrame("El mentiroso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cerrar aplicación al cerrar la ventana
        Container containerPane = frame.getContentPane();
        containerPane.setLayout(new BorderLayout());

        // Título del juego
        JLabel tituloJuego = new JLabel("El mentiroso", SwingConstants.CENTER);

        //Se establece una fuente para el título. Aquí se usa una fuente "Serif", con estilo Font.BOLD (negrita) y tamaño 24.
        tituloJuego.setFont(new Font("Serif", Font.BOLD, 24));

        // Se obtiene el contentPane del JFrame.
        // El contentPane es el panel principal donde se colocan todos los componentes visuales (botones, etiquetas, paneles, etc.)
        // Cada JFrame tiene un contentPane donde se añaden los elementos gráficos.
        containerPane.add(tituloJuego, BorderLayout.NORTH);
        containerPane.add(Box.createRigidArea(new Dimension(0,15)));

        // Panel para los botones
        JPanel panelBotones = new JPanel();

        // Se establece un BoxLayout con orientación vertical (BoxLayout.Y_AXIS)
        // para organizar los botones uno debajo del otro dentro del panel.
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        // Establece la alineación del panel de botones en el eje X.
        // Con Component.CENTER_ALIGNMENT, todos los componentes dentro
        // del panel estarán centrados horizontalmente.
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón para jugar
        JButton botonJugar = new JButton("Jugar");
        botonJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonJugar.setPreferredSize(new Dimension(150, 40));  // Tamaño fijo
        botonJugar.setMaximumSize(botonJugar.getPreferredSize()); // Se asegura que el botón no crezca más allá del tamaño preferido especificado anteriormente.
        botonJugar.addActionListener(evento -> botonJugar());
        panelBotones.add(botonJugar); // Se agrega el boton al panel de botones.
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));  // Espacio entre los botones

        // Botón para acerca de:
        JButton botonAcerca = new JButton("Acerca de");
        botonAcerca.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonAcerca.setPreferredSize(new Dimension(150, 40));  // Tamaño fijo
        botonAcerca.setMaximumSize(botonAcerca.getPreferredSize()); // Se asegura que el botón no crezca más allá del tamaño preferido especificado anteriormente.
        botonAcerca.addActionListener(evento -> botonAcerca());
        panelBotones.add(botonAcerca); // El boton se añade al panel de botones.
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10))); //Espacio entre los botones

        // Botón para salir:
        JButton botonSalir = new JButton("Salir");
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.setPreferredSize(new Dimension(150,40));
        botonSalir.setMaximumSize(botonSalir.getPreferredSize());
        botonSalir.addActionListener(evento -> botonSalir());
        panelBotones.add(botonSalir);

        // Agregar el panel de botones al centro
        containerPane.add(panelBotones, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(800, 700);  // Tamaño inicial de la ventana
        frame.setLocationRelativeTo(null);  // Centrar la ventana en la pantalla
        frame.setVisible(true);
    }

    /**
     * Método del botón Jugar
     */
    public void botonJugar()
    {
        frame.setVisible(false);
        Juego juego = new Juego(2);
    }

    /**
     * Método del botón Acerca de
     */
    public void botonAcerca()
    {
        System.out.println("Simon2");
    }

    /**
     * Método para el bóton salir
     */
    public void botonSalir()
    {
        System.exit(0);
    }

    public static void main2(String[] args) {
        new Interfaz();
    }

}
