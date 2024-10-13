import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;
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
        botonColocarPozo = new JButton("Colocar Pozo");
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setFrame()
    {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método para seleccionar el veredicto del jugador y que hacer con las cartas
     * @param veredicto
     */
    public String setVerdadOMentira(String veredicto, Baraja cartasELeccion, Jugador jugador)
    {
        //Consta de una variable para crear los indices para la baraja mentira
        Random random = new Random();
        //Cartas mentira sirve por si elige mentira, entonces obtendrá cartas de la misma mano
        //del jugador(que no se sacarán solo se copiaran) y esas se imprimiran al usuario
        Baraja cartasMentira = new Baraja();
        //Será el valor de retorno
        String veredictoFinal = "";

        //Switch case que actúa en los dos casos posibles
        switch (veredicto) {
            case "verdad":

                //Si elige verdad el jugador simplemente se imprimen las cartas reales
                //que metió el jugador al pozo
                System.out.println("El jugador ha metido al pozo: \n");
                for (Carta carta : cartasELeccion.getBaraja()) {
                    System.out.println("Ha metido: " + carta.toString());
                }

                //Se asigna el valor de retorno
                veredictoFinal = "verdad";
                break;
            case "mentir":

                //Aquí es un poco más complejo donde se utiliza de la variable cartas mentira
                //donde se hace un ciclo que su limite es la cantidad de cartas que seleccionó el jugador
                //pero las cartas que se imprimirán a los demás jugadores serán falsas es decir las que
                //verdaderamente no metió el jugador
                System.out.println("El jugador ha metido al pozo: \n");
                for (int i = 0; i < cartasELeccion.getBaraja().size(); ++i) {

                    //se pone menos uno porque recordar que size() imprime la cantidad de objetos en la colección,
                    //más no el número de indices
                    cartasMentira.getBaraja().add(jugador.getMano().get(random.nextInt(jugador.getMano().size() - 1)));

                    //Se imprimen las cartas erroneas
                    System.out.println("Ha metido: " + cartasMentira.getBaraja().get(i).toString());
                }
                veredictoFinal = "mentira";
                break;
        }
        //Ya por ultimo se retorna el veredicto.
        return veredictoFinal;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método que determina si es el fin del juego(cuando un jugador se queda sin cartas)
     * @param jugadores
     * @return
     */
    public boolean finDelJuego(ArrayList<Jugador> jugadores)
    {
        //Con uso de flujos y lambdas se puede saber si un jugador
        //tiene su mano vacia(queriendo decir que el juego ya acabó)
        return jugadores.stream()
                .anyMatch(jugador -> jugador.getMano().isEmpty());

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método que regresa el jugador ganador(Se requiere del método finDelJuego)
     * @param jugadores
     * @return
     */
    public Jugador setGanador(ArrayList<Jugador> jugadores)
    {
        //Con apoyo de flujos y lambdas se puede obtener el jugador con la mano vacía
        //ademas se utiliza de .orElse para brindar seguridad al código.
        return jugadores.stream()
                .filter(jungador -> jungador.getMano().isEmpty())
                .findAny()
                .orElse(null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método para seleccionar las cartas a poner al pozo
     * @param jugador
     * @return
     */
    public Baraja sistemaEleccionCartas(Jugador jugador)
    {
        //Se inicia con una baraja vacia que en este caso se meterán las cartas
        //que se seleccionarán, además de una variable que nos servirá para elegir
        //los indices de las cartas y un scanner para leer el indice que seleccione
        //el jugador
        int indiceEleccion = 0;
        Baraja cartasEleccion = new Baraja();
        Scanner respuesta = new Scanner(System.in);
        int eleccion = 0;
        panelCartasSeleccionadas.removeAll();

        //El ciclo parará hasta halla 3 cartas en su contenido o a menos
        //que el usuario ingrese -1 para parar el ciclo solo seleccionar
        //menos de las tres cartas
        while (cartasEleccion.getBaraja().size() != 3 && eleccion != -1) {

            panelMano.removeAll();
            System.out.println("Cartas de " + jugador.getNombre() + "\n");
            for (Carta carta : jugador.getMano()) {
                panelMano.add(carta.getImagenCarta());
            }
            jugador.mostrarMano();

            //Se imprime si ya hubo un ciclo anterior
            System.out.println("\nCartas elegidas: \n");
            if (!cartasEleccion.getBaraja().isEmpty()) {
                cartasEleccion.mostrarEnConsola();
            }

            if (!cartasEleccion.getBaraja().isEmpty()) {
                for (Carta carta : cartasEleccion.getBaraja()) {
                    panelCartasSeleccionadas.add(carta.getImagenCarta());
                }
            }

            //Lectura de indice de la mano
            estadoJuego.setText("Elija las cartas a meter al pozo (máximo 3)");
            System.out.println("Elija las cartas a meter al pozo (máximo 3) o si ya quieres salir oprime \"-1\" \n");

            eleccion = respuesta.nextInt();

            //Como se mencionó al inicio del ciclo, si el jugador selecciona -1 quiere decir
            //que quiere salir
            if (eleccion == -1) {
                break;
            }

            //Se saca la carta y se mete en la mano del jugador
            cartasEleccion.getBaraja().add(jugador.getMano().remove(eleccion));
        }
        panelCartasSeleccionadas.add(jugador.getMano().get(eleccion).getImagenCarta());

        //Como la ultima impresión de la ultima carta seleccionada no se imprime
        //se vuelve imprimir las cartas seleccionadas aquí
        System.out.println("\nCartas elegidas: \n");
        cartasEleccion.mostrarEnConsola();

        //Se retorna las cartas para poder meterlas en otro método
        return cartasEleccion;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void jugarInterfaz()
    {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método de juego, es decir donde ya se juega
     */
    public void jugar()
    {
        //Variables que constan del turnoActual donde estará cambiando cada iteracion
        //un booleano para saber si el juego terminó, un Scanner para lectura de datos
        //VeredictoFinal, que este es para elegir si el usuario quiere mentira o verdad
        //Bandera escoger es una bandera de solo uso, para la primera iteación ya que
        //no hay cartas en el pozo que analizar al principio y el turno de jugador del
        //veredicto final que este nos servirá por si un jugador decide decir que es mentira
        //lo que dijo el jugador anterior, y si es verdad lo que dice se utiliza de la variable
        //para acceder al indice de ese jugador y almacenar todas las cartas ahí, por ultimo
        //un objeto de tipo Jugador llamado ganador para guardar el ganador en ese objeto
        int turnoActual = 0;
        boolean juegoTerminado = false;
        Scanner respuesta = new Scanner(System.in);
        String veredictoFinal = "";
        boolean banderaEscoger = false;
        int turnoJugadorVeredictoFinal = 0;
        Jugador ganador = null;
        hacerFrame();

        while (!juegoTerminado) {

            //Variables temporales, JugadorActual para mejor legibilidad del jugador
            //que se esta jugando actualmente, cartasEleccion que se utiliza cuando los
            //jugadores eligen que cartas usar
            Jugador jugadorActual = jugadores.get(turnoActual);
            Baraja cartasEleccion = new Baraja();
            int indiceEleccion = 0;

            if (!finDelJuego(jugadores)) {

                turno.setText("Turno del " + jugadorActual.getNombre() + " |");


                //BanderaEscoger como se mencionó se usa una vez para ya poder acceder a la lectura
                //si mentió el jugador anterior o no
                if (banderaEscoger) {
                    estadoJuego.setText("¿Es verdad o mentira?");
                    System.out.println("Es...");
                    System.out.println("mentira\n");
                    System.out.println("verdad\n");
                    String veredictoNuevo = respuesta.next();

                    //Se analiza el verdicto del jugador anterior con el actual y si coinciden en la mentira
                    //Se lleva todo el pozo el jugador mentiroso
                    if (veredictoNuevo.equals("mentira") && veredictoFinal.equals("mentira")) {
                        mentiraOverdad.setText("| Al parecer se desubrió el mentiroso!!!\n");
                        System.out.println("Al parecer se desubrió el mentiroso!!!\n");

                        //Ciclo para agregar las cartas del pozo a la mano del jugador, donde turnoJugadorVeredictoFinal
                        //es una variable timpo int del indice del jugador anterior
                        for (int i = 0; i < mesa.tamanioPozo(); ) {
                            mesa.getPozo().getBaraja().getFirst().setVisibilidad(true);
                            jugadores.get(turnoJugadorVeredictoFinal).getMano().add(mesa.getPozo().getBaraja().removeFirst());
                        }

                        //Si no coincide el veredictoNuevo de mentira con el veredicto anterior, el jugadorActual toma
                        //todas las cartas del pozo
                    } else if (veredictoNuevo.equals("mentira") && veredictoFinal.equals("verdad")) {
                        mentiraOverdad.setText("| Al parecer si era verdad...");
                        System.out.println("Al parecer si era verdad...");

                        //En este no es necesario la variable del indice anterior ya que solo se almacenan en el jugador
                        //actual
                        for (int i = 0; i < mesa.tamanioPozo(); ) {
                            mesa.getPozo().getBaraja().getFirst().setVisibilidad(true);
                            jugadorActual.getMano().add(mesa.getPozo().getBaraja().removeFirst());
                        }
                    }
                }

                //Método para seleccionar las cartas a poner en el pozo donde retorna las tres cartas seleccionadas
                cartasEleccion = sistemaEleccionCartas(jugadorActual);

                estadoJuego.setText("¿Quieres mentir o verdad?");
                System.out.println("Quieres...\n");
                System.out.println("mentir\n");
                System.out.println("verdad\n");

                //En este mismo método se lee el veredicto del jugador, donde el método almacena las cartas en
                //el pozo y crea el veredicto para el siguiente jugador, además retotrna el veredicto pero en palabra
                //para poder comparalo con el veredicto del siguiente jugador
                veredictoFinal = setVerdadOMentira(respuesta.next(), cartasEleccion, jugadorActual);

                //Se mete al pozo pero ocultadas par asimular el pozo(las cartas deben estar volteadas)
                for (Carta carta : cartasEleccion.getBaraja()) {
                    carta.setVisibilidad(false);
                    mesa.getPozo().getBaraja().add(carta);
                }

                //Se imprime el pozo la variable bandera se torna true.
                System.out.println("===Pozo===\n");
                mesa.getPozo().mostrarEnConsola();
                banderaEscoger = true;

                //En dado caso que nose cumpla la condición que es el fin del juego(quiere decir que ya hay un jugador
                // sin cartas) se va acá donde se determina el ganador y torna la variable de juego terminado true
            } else {
                ganador = setGanador(jugadores);
                juegoTerminado = true;
            }

            //se toma el indice del jugador actual para poder meter las cartas en la siguiente iteracion
            // en dado caso que el eligiera mentira
            turnoJugadorVeredictoFinal = turnoActual;
            turnoActual = (turnoActual + 1) % jugadores.size();
        }

        //Se imprime el ganador
        estadoJuego.setText("Ha ganado " + ganador.getNombre() + ", se quedó sin cartas!!");
        System.out.println("Ha ganado " + ganador.getNombre() + ", se quedó sin cartas!!");

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //AREA DE BOTONES

    private void botonMentira()
    {

    }

    private void botonVerdad()
    {

    }

    private void botonColocarPozo()
    {

    }

    /**
     * Método que es el botón para salir del juego
     */
    private void botonSalir()
    {
        System.exit(0);
    }

}
