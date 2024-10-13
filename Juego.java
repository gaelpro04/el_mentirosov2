import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Juego {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Por el momento la clase consta de una colección de jugadores
    //Una mesa(donde se pondrán las cartas jugadas), una baraja
    //que será la baraja con la que se jugará toda la partida
    private ArrayList<Jugador> jugadores;
    private Mesa mesa;
    private Baraja baraja;
    private boolean listener;
    private String veredicto;
    private String veredictoFinal;
    private  boolean descubrir;
    private Carta cartaOculta;
    private Baraja cartasMentira;
    private Baraja cartasEleccion;
    private boolean desactivarMouseListener;
    private int turnoActual;

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
    private  JButton botonSiguienteTurno;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor del Juego, ingresa la cantidad de jugadores
     * @param cantJugadores
     */
    public Juego(int cantJugadores) {

        this.jugadores = new ArrayList<>(cantJugadores);
        this.mesa = new Mesa();
        listener = true;
        veredicto = null;
        veredictoFinal = null;
        descubrir = false;
        cartaOculta = new Carta("oros",1,false,"BarajaEspañola/CartaInversa.png");
        cartasEleccion = new Baraja();
        turnoActual = 0;
        desactivarMouseListener = true;

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
        botonSiguienteTurno = new JButton("Siguiente turno");
        botonSiguienteTurno.addActionListener(evento -> botonSiguienteTurno());
        botonMentira.addActionListener(evento -> botonMentira());
        botonVerdad.addActionListener(evento -> botonVerdad());
        botonColocarPozo.addActionListener(evento -> botonColocarPozo());

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
        panelControlAbajo.add(botonSiguienteTurno, BorderLayout.CENTER);
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
                jugador.getMano().add(baraja.getBaraja().removeFirst());
            }
        }

        //En dado caso que la asignación de cartas por jugador no sea entera y
        //quede un residuo en la baraja esta se alamacenará en el cementerio del juego
        if (!baraja.getBaraja().isEmpty()) {
            for (int i = 0; i < baraja.getBaraja().size(); ++i) {
                mesa.getCementerio().getBaraja().add(baraja.getBaraja().removeFirst());
            }
        }
    }

    private void cartasSeleccionables(Jugador jugador)
    {
        if (desactivarMouseListener) {
            panelCartasSeleccionadas.removeAll();

            for (Carta carta : jugador.getMano()) {
                carta.getImagenCarta().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (listener) {
                            if (cartasEleccion.getBaraja().size() < 3) {
                                panelCartasSeleccionadas.add(carta.getImagenCarta());
                                cartasEleccion.getBaraja().add(carta);

                                panelCartasSeleccionadas.repaint();
                                panelCartasSeleccionadas.revalidate();
                                jugador.getMano().remove(carta);


                            } else {
                                estadoJuego.setText("Has alcanzado el máximo de cartas");
                            }
                        } else {
                            estadoJuego.setText("Debes colocar las cartas al pozo");
                        }




                    }
                });
            }
        }




    }

    private void mostrarMano(Jugador jugador)
    {
        panelMano.removeAll();
        for (Carta carta : jugador.getMano()) {
            panelMano.add(carta.getImagenCarta());
        }
        panelMano.repaint();
        panelMano.revalidate();
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
        cartasSeleccionables(jugadores.get(turnoActual));



    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //AREA DE BOTONES

    private void botonSiguienteTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();

        // Actualiza el estado del turno en la interfaz
        turno.setText("Turno de jugador: " + (turnoActual + 1));
        panelCartasSeleccionadas.removeAll();

        switch (veredicto) {
            case "Mentira":
                estadoJuego.setText("El jugador ha metido las siguientes cartas decide si es mentira o verdad");
                for (Carta carta : cartasEleccion.getBaraja()) {
                    panelCartasSeleccionadas.add(carta.getImagenCarta());
                }
                break;
            case "Verdad":
                estadoJuego.setText("El jugador ha metido las siguientes cartas decide si es mentira o verdad");
                for (Carta carta : cartasEleccion.getBaraja()) {
                    panelCartasSeleccionadas.add(carta.getImagenCarta());
                }
                break;
        }

        panelCartasSeleccionadas.repaint();
        panelCartasSeleccionadas.revalidate();

        // Muestra la mano del jugador actual
        mostrarMano(jugadores.get(turnoActual));

        // Permite que el jugador seleccione cartas
        cartasSeleccionables(jugadores.get(turnoActual));

        // Reinicia el veredicto
        veredicto = null; // Reinicia el veredicto para la siguiente ronda
        desactivarMouseListener = true;
    }

    private void botonMentira()
    {
        veredicto = "Mentira";
        desactivarMouseListener = false;
    }

    private void botonVerdad()
    {
        veredicto = "Verdad";
        desactivarMouseListener = false;
    }

    private void botonColocarPozo()
    {
        Random rd = new Random();
        listener = true;


    }


    /**
     * Método que es el botón para salir del juego
     */
    private void botonSalir()
    {
        System.exit(0);
    }

}
