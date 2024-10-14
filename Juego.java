import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Juego extends javax.swing.JFrame implements MouseListener {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Por el momento la clase consta de una colección de jugadores
    //Una mesa(donde se pondrán las cartas jugadas), una baraja
    //que será la baraja con la que se jugará toda la partida
    private ArrayList<Jugador> jugadores;
    private Mesa mesa;
    private Baraja baraja;
    private int turnoActual;
    private Carta cartaOculta;;
    private Baraja cartasEleccion;
    private String veredicto;
    private String VeredictoFinal;

    //Atributos para la interfaz de juego
    private JFrame frame;
    private JPanel panelCartas;
    private JPanel panelControlArriba;
    private JPanel panelControlArribaIzquierda;
    private JPanel panelControlArribaDerecha;
    private JPanel panelControlAbajo;
    private JPanel panelMano;
    private JPanel panelPozo;
    private JPanel panelCartasSeleccionadas;

    private JMenuBar menuBar;
    private JMenu menuAyuda;
    private JMenuItem itemSalir;

    private JLabel estadoJuego;
    private JLabel turno;
    private  JLabel mentiraOverdad;

    private JButton botonMentira;
    private JButton botonVerdad;
    private JButton botonColocarPozo;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor del Juego, ingresa la cantidad de jugadores
     * @param cantJugadores
     */
    public Juego(int cantJugadores) {

        this.jugadores = new ArrayList<>(cantJugadores);
        this.mesa = new Mesa();
        cartaOculta = new Carta("oros",1,false,"BarajaEspañola/CartaInversa.png");
        cartasEleccion = new Baraja();
        turnoActual = 0;

        //El constructor por defecto se tiene que poner
        //48(que es la cantidad de cartas de una baraja española)
        this.baraja = new Baraja(40);

        //Se inicializa primero los jugadores para que no haya problemas
        //al pedirles el nombre
        for (int i = 0; i < cantJugadores; ++i) {
            jugadores.add(new Jugador(""));
        }

        //Método para hacer manos(explicado más adelante)
        hacerManos();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método privado que hace la interfaz del juego
     */
    private void hacerFrame()
    {
        //Crea el frame
        frame = new JFrame("El mentiroso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Crea los paneles del frame
        panelCartas = new JPanel();
        panelCartas.setLayout(new BorderLayout());
        panelMano = new JPanel();
        panelPozo = new JPanel(new BorderLayout());
        panelCartasSeleccionadas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelMano.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelCartas.add(panelMano, BorderLayout.SOUTH);
        panelCartas.add(panelPozo, BorderLayout.CENTER);
        panelCartas.add(panelCartasSeleccionadas, BorderLayout.NORTH);
        panelControlArriba = new JPanel();
        panelControlAbajo = new JPanel();
        panelControlArribaIzquierda = new JPanel();
        panelControlArribaDerecha = new JPanel();

        //Creaciones de botones
        botonMentira = new JButton("Mentira");
        botonVerdad = new JButton("Verdad");
        botonColocarPozo = new JButton("Colocar en Pozo");
        botonMentira.addActionListener(evento -> botonMentira());
        botonVerdad.addActionListener(evento -> botonVerdad());
        botonColocarPozo.addActionListener(evento -> botonColocarPozo());
        if (cartasEleccion.getBaraja().isEmpty()) {
            botonMentira.setEnabled(false);
            botonVerdad.setEnabled(false);
            botonColocarPozo.setEnabled(false);
        }

        estadoJuego = new JLabel("Estado Juego");
        turno = new JLabel("Turno");
        mentiraOverdad = new JLabel("| Mentira o verdad");

        //Modificación de paneles
        panelMano.setBackground(new Color(53,101,77));
        panelCartas.setBackground(new Color(53,101,77));
        panelCartasSeleccionadas.setBackground(new Color(53,101,77));
        panelPozo.setBackground(new Color(53,101,77));

        panelControlArribaIzquierda.setLayout(new FlowLayout());
        panelControlArribaDerecha.setLayout(new FlowLayout());
        panelControlArribaIzquierda.add(turno, BorderLayout.WEST);
        panelControlArribaDerecha.add(estadoJuego, BorderLayout.EAST);
        panelControlArribaDerecha.add(mentiraOverdad, BorderLayout.EAST);
        panelControlArriba.add(panelControlArribaIzquierda, BorderLayout.WEST);
        panelControlArriba.add(panelControlArribaDerecha, BorderLayout.EAST);
        panelControlAbajo.add(botonMentira, BorderLayout.CENTER);
        panelControlAbajo.add(botonVerdad, BorderLayout.CENTER);
        panelControlAbajo.add(botonColocarPozo, BorderLayout.CENTER);

        //Crea la barra menú y menú, junto a sus actionListeners
        menuBar = new JMenuBar();
        menuAyuda = new JMenu("Ayuda");
        itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(evento -> botonSalir());

        //Almacén de items, menús en la barra menú y añadir paneles a frame
        menuAyuda.add(itemSalir);
        menuBar.add(menuAyuda);
        frame.setJMenuBar(menuBar);
        frame.add(panelCartas);
        frame.add(panelControlArriba, BorderLayout.NORTH);
        frame.add(panelControlAbajo, BorderLayout.SOUTH);


        //Hacer el frame visible junto a modificaciones
        frame.setSize(1500, 900);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Hacer mano de cada jugador
     */
    private void hacerManos()
    {

        //Aquí se inicia el constructor de cada jugador de la colección de jugadores
        for (int i = 0; i < jugadores.size(); i++) {
            jugadores.get(i).setNombre("Jugador "+(i+1));
        }

        //Se revuelve la baraja para poder asignar las cartas más fácil a cada jugador
        Collections.shuffle(baraja.getBaraja());

        //Para determinar las cartas por jugador se divide la cantidad de cartas entre
        //los jugadores
        int cartasPorJugador = baraja.getBaraja().size() / jugadores.size();

        //El ciclo para asignar cartas consta del ciclo de cada jugador y el
        //ciclo para asignar cartas por jugador
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < cartasPorJugador; i++) {
                baraja.getBaraja().getFirst().getImagenCarta().addMouseListener(this);
                jugador.getMano().add(baraja.getBaraja().removeFirst());
            }
        }

        //En dado caso que la asignación de cartas por jugador no sea entera y
        //quede un residuo en la baraja esta se alamacenará en el cementerio del juego
        if (!baraja.getBaraja().isEmpty()) {
            for (int i = 0; i < baraja.getBaraja().size(); ++i) {
                baraja.getBaraja().getFirst().getImagenCarta().addMouseListener(this);
                mesa.getCementerio().getBaraja().add(baraja.getBaraja().removeFirst());
            }
        }
    }

    private void mostrarMano(Jugador jugador)
    {
        panelMano.removeAll();
        panelCartasSeleccionadas.removeAll();
        for (Carta carta : jugador.getMano()) {
            panelMano.add(carta.getImagenCarta());
        }
        panelMano.repaint();
        panelMano.revalidate();
        estadoJuego.setText("Escoge máximo tres cartas");
    }

    private void seleccionCartas()
    {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método de juego, es decir donde ya se juega
     */
    public void jugar()
    {
        hacerFrame();
        turno.setText("Turno de jugador: " + (turnoActual+1));
        mostrarMano(jugadores.get(turnoActual));



    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //AREA DE BOTONES

    private void botonMentira()
    {
        botonVerdad.setEnabled(false);
        botonMentira.setEnabled(false);
        veredicto = "Mentira";
    }

    private void botonVerdad()
    {
        botonVerdad.setEnabled(false);
        botonMentira.setEnabled(false);
        veredicto = "Verdad";
    }

    private void botonColocarPozo()
    {
        botonColocarPozo.setEnabled(false);

        if (!cartasEleccion.getBaraja().isEmpty()) {
            for (int i = 0; i < cartasEleccion.getBaraja().size(); ++i) {
                mesa.getPozo().getBaraja().add(cartasEleccion.getBaraja().getFirst());
            }
        }

        if (!mesa.getPozo().getBaraja().isEmpty()) {
            panelPozo.add(cartaOculta.getImagenCarta());
            panelPozo.repaint();
            panelPozo.revalidate();
        }

        turnoActual = (turnoActual + 1) % jugadores.size();
        turno.setText("Turno de jugador: " + (turnoActual+1));
        mostrarMano(jugadores.get(turnoActual));

        cartasEleccion = new Baraja();

    }


    /**
     * Método que es el botón para salir del juego
     */
    private void botonSalir()
    {
        System.exit(0);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        ArrayList<Carta> cartasEliminar = new ArrayList<>();

        for (Carta carta : jugadores.get(turnoActual).getMano()) {

            if (e.getSource() == carta.getImagenCarta()) {

                botonMentira.setEnabled(true);
                botonVerdad.setEnabled(true);
                botonColocarPozo.setEnabled(true);

                if (cartasEleccion.getBaraja().size() < 3) {
                    cartasEliminar.add(carta);
                    cartasEleccion.getBaraja().add(carta);
                    panelCartasSeleccionadas.add(carta.getImagenCarta());

                    panelCartasSeleccionadas.repaint();
                    panelCartasSeleccionadas.revalidate();
                } else {
                    estadoJuego.setText("Has alcanzado el máximo de cartas");
                }

            }
        }

        for (Carta carta : cartasEliminar) {
            jugadores.get(turnoActual).getMano().remove(carta);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
