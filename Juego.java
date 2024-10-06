import InterfazYCartasVisuales.TableroVisual;

import java.util.ArrayList;

public class Juego {
    private ArrayList<Jugador> jugadores;
    private Pozo mesa;
    private TableroVisual tableroVisual;

    public Juego() {
        this.jugadores = new ArrayList<>();
        this.mesa = new Pozo();
        this.tableroVisual = new TableroVisual();
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Pozo getMesa() {
        return mesa;
    }

    public void setMesa(Pozo mesa) {
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
