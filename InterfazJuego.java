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

    /**
     * Constructor de la clase donde se inicia todo el marco con sus componentes
     */
    public InterfazJuego()
    {
        frameJugar = new JFrame("El mentiroso");
        panelJugar = new JPanel();
        barraAbajo = new JPanel();
        mentirBoton = new JButton("Mentir");
        verdadBoton = new JButton("Verdad");
        barraArriba = new JPanel();



        frameJugar.pack();
        frameJugar.setSize(800,800);
        frameJugar.setVisible(true);
        frameJugar.setLocationRelativeTo(null);

        panelJugar.setLayout(new BorderLayout());
        panelJugar.setBackground(new Color(53, 101, 77));
        barraAbajo.add(mentirBoton, Component.CENTER_ALIGNMENT);
        barraAbajo.add(verdadBoton, Component.CENTER_ALIGNMENT);
        frameJugar.add(barraArriba, BorderLayout.NORTH);
        frameJugar.add(panelJugar);
        frameJugar.add(barraAbajo, BorderLayout.SOUTH);


    }
}
