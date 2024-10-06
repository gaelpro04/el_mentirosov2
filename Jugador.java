import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> mano;
    private int cartasRestantes;
    private int puntuacion;

    /**
     * Constructor con limites de cartas
     * @param nombre
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.cartasRestantes = 0;
        this.puntuacion = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public void setMano(ArrayList<Carta> mano) {
        this.mano = mano;
    }

    public int getCartasRestantes() {
        return cartasRestantes;
    }

    public void setCartasRestantes(int cartasRestantes) {
        this.cartasRestantes = cartasRestantes;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", mano=" + mano +
                ", cartasRestantes=" + cartasRestantes +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
