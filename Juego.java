import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Juego {

    //Por el momento la clase consta de una colección de jugadores
    //Una mesa(donde se pondrán las cartas jugadas), una baraja
    //que será la baraja con la que se jugará toda la partida
    private ArrayList<Jugador> jugadores;
    private Mesa mesa;
    private TableroVisual tableroVisual;
    private Baraja baraja;

    /**
     * Constructor del Juego, ingresa la cantidad de jugadores
     * @param cantJugadores
     */
    public Juego(int cantJugadores) {

        this.jugadores = new ArrayList<>(cantJugadores);
        this.mesa = new Mesa();
        this.tableroVisual = new TableroVisual();

        //El constructor por defecto se tiene que poner
        //48(que es la cantidad de cartas de una baraja española)
        this.baraja = new Baraja(48);

        //Se inicializa primero los jugadores para que no haya problemas
        //al pedirles el nombre
        for (int i = 0; i < cantJugadores; ++i) {
            jugadores.add(new Jugador(""));
        }

        //Método para hacer manos(explicado más adelante)
        hacerManos();
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Hacer mano de cada jugador
     */
    private void hacerManos()
    {
        //Se crea una variable Scanner para poder leer los nombres de cada jugador
        Scanner respuesta = new Scanner(System.in);


        //Aquí se inicia el constructor de cada jugador de la colección de jugadores
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println("Ingresa el nombre del jugador " + (i+1));
            jugadores.get(i).setNombre(respuesta.next());
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
                mesa.getCementerio().add(baraja.getBaraja().removeFirst());
            }
        }
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public TableroVisual getTableroVisual() {
        return tableroVisual;
    }

    public void setTableroVisual(TableroVisual tableroVisual) {
        this.tableroVisual = tableroVisual;
    }

    /**
     * Método para seleccionar el veredicto del jugador y que hacer con las cartas
     * @param veredicto
     */
    public void setVerdadOMentira(String veredicto)
    {
        switch (veredicto) {
            case "verdad":

                break;
            case "mentira":
                break;
        }
        System.out.println("arre2");
    }

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

        //El ciclo parará hasta halla 3 cartas en su contenido
        while (cartasEleccion.getBaraja().size() != 3) {

            System.out.println("Cartas de " + jugador.getNombre() + "\n");
            jugador.mostrarMano();

            System.out.println("\nCartas elegidas: \n");
            if (!cartasEleccion.getBaraja().isEmpty()) {
                cartasEleccion.mostrarEnConsola();
            }

            System.out.println("Eliga las cartas a meter al pozo(máximo 3)\n");
            cartasEleccion.getBaraja().add(jugador.getMano().remove(respuesta.nextInt()));
        }

        System.out.println("\nCartas elegidas: \n");
        cartasEleccion.mostrarEnConsola();

        return cartasEleccion;
    }

    /**
     * Método de juego, es decir donde ya se juega
     */
    public void jugar()
    {
        int turnoActual = 0;
        boolean juegoTerminado = false;
        Scanner respuesta = new Scanner(System.in);

        while (!juegoTerminado) {
            Jugador jugadorActual = jugadores.get(turnoActual);
            Baraja cartasEleccion = new Baraja();
            int indiceEleccion = 0;

            if (!finDelJuego(jugadores)) {

                System.out.println("===Turno del jugador " + jugadorActual.getNombre() + "===");
                cartasEleccion = sistemaEleccionCartas(jugadorActual);

                System.out.println("Quieres...\n");
                System.out.println("Mentir\n");
                System.out.println("Verdad\n");
                setVerdadOMentira(respuesta.next());
            } else {

            }


            turnoActual = (turnoActual + 1) % jugadores.size();
        }

    }

}
