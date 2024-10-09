import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class InterfazJuego
{
    //Dos atributos uno para el marco del juego, y otro para todos los componentes del juego
    JFrame frameJugar;
    JPanel panelJugar;
    JPanel barraAbajo;
    JPanel barraArriba;
    ArrayList<Jugador> jugadores;
    JButton mentirBoton;
    JButton verdadBoton;
    JButton colocarBoton;

    //Barra del menú
    JMenuBar menuBar;
    JMenu menuAyuda;
    JMenuItem menuItemSalir;
    JMenuItem menuItemRegresar;

    /**
     * Constructor de la clase donde se inicia todo el marco con sus componentes
     */
    public InterfazJuego()
    {
        //Creación de todos los elementos de la ventana de juego
        frameJugar = new JFrame("El mentiroso");
        frameJugar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelJugar = new JPanel();
        barraAbajo = new JPanel();
        mentirBoton = new JButton("Mentir");
        mentirBoton.addActionListener(evento -> botonMentir());
        verdadBoton = new JButton("Verdad");
        verdadBoton.addActionListener(evento -> botonVerdad());
        colocarBoton = new JButton("Colocar en el pozo");
        colocarBoton.addActionListener(evento -> botonColocar());
        barraArriba = new JPanel();


        menuBar = new JMenuBar();
        menuAyuda = new JMenu("Ayuda");
        menuItemRegresar = new JMenuItem("Regresar");
        menuItemRegresar.addActionListener(evento -> regresar());
        menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(evento -> System.exit(0));


        //Impresión de la ventana
        frameJugar.pack();
        frameJugar.setSize(800,700);
        frameJugar.setVisible(true);
        frameJugar.setLocationRelativeTo(null);

        //Acomodo de los elementos de la ventana
        panelJugar.setLayout(new BorderLayout());
        panelJugar.setBackground(new Color(53, 101, 77));
        barraAbajo.add(mentirBoton, Component.CENTER_ALIGNMENT);
        barraAbajo.add(verdadBoton, Component.CENTER_ALIGNMENT);
        barraAbajo.add(colocarBoton, Component.CENTER_ALIGNMENT);
        frameJugar.add(barraArriba, BorderLayout.NORTH);

        menuAyuda.add(menuItemRegresar);
        menuAyuda.add(menuItemSalir);
        menuBar.add(menuAyuda);
        frameJugar.setJMenuBar(menuBar);
        frameJugar.add(panelJugar);
        frameJugar.add(barraAbajo, BorderLayout.SOUTH);

    }

    public void botonMentir()
    {

    }

    public void botonVerdad()
    {

    }

    public void botonColocar()
    {

    }

    private void regresar() {
        frameJugar.setVisible(false);
        new Interfaz();
    }


}
