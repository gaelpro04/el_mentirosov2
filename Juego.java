import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Juego {
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
        this.baraja = new Baraja(48);

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
            System.out.println("Ingresa el nombre del jugador " + i+1);
            jugadores.add(new Jugador(respuesta.next()));
        }

        //Se revuelve la baraja para poder asignar las cartas más fácil a cada jugador
        Collections.shuffle(baraja.getBaraja());

        //El ciclo para asignar cartas consta del ciclo de cada jugador y el
        //ciclo para asignar cartas por jugador
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < baraja.getBaraja().size() / jugadores.size(); i++) {
                jugador.getMano().add(baraja.getBaraja().getFirst());
            }
        }

        //En dado caso que la asignación de cartas por jugador no sea entera y
        //quede un residuo en la baraja esta se alamacenará en el cementerio del juego
        if (!baraja.getBaraja().isEmpty()) {
            for (int i = 0; i < baraja.getBaraja().size(); ++i) {
                mesa.getCementerio().add(baraja.getBaraja().getFirst());
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

    @Override
    public String toString() {
        return "Juego{" +
                "jugadores=" + jugadores +
                ", mesa=" + mesa +
                ", tableroVisual=" + tableroVisual +
                '}';
    }
}
