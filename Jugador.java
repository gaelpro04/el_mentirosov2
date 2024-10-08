import java.util.ArrayList;

public class Jugador {

    //Los atributos de un jugador constan de:
    //Un nombre, la mano que es de tipo Baraja, una puntuación
    //y cartas restantes
    private String nombre;
    private final Baraja mano;
    private int cartasRestantes;
    private int puntuacion;

    /**
     * Constructor con limites de cartas
     * @param nombre
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new Baraja();
        this.cartasRestantes = 0;
        this.puntuacion = 0;
    }

    /**
     * Método selector, regresa el nombre del jugador
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que cambia el nombre del jugador
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Carta> getMano() {
        return mano.getBaraja();
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

    /**
     * Método que muestra todas las cartas de la mano
     * del jugador, este método se apoya con el método de monstrarEnConsola()
     * de Baraja
     */
    public void mostrarMano()
    {
        mano.mostrarEnConsola();
    }
}
