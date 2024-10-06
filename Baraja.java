import java.util.ArrayList;

public class Baraja {

    //Atributo de la clase(una colección de catas)
    ArrayList<Carta> baraja;
    String[] bancoPalos = {"oro", "copa", "espada", "basto"};

    /**
     * Constructor por preterminado
     */
    public  Baraja()
    {
        //Una baraja española por preterminado tiene 48 cartas
        baraja = new ArrayList<>(48);

        for (String palo : bancoPalos) {
            for (int i = 1; i <= 12; ++i) {
                baraja.add(new Carta(palo, i, true));
            }
        }
    }

    /**
     * Constructor con limites de cartas
     * @param minimo
     * @param maximo
     */
    public Baraja(int minimo, int maximo)
    {
        //Con los nuevos limites podemos calcular el tamaño de la colección
        //simplemente multplicando la cantidad de palos por el maximo
        baraja = new ArrayList<>(maximo * 4);

        for (String palo : bancoPalos) {
            for (int i = minimo; i <= maximo; ++i) {

                baraja.add(new Carta(palo, i, true));
            }
        }
    }

    /**
     * Constructor de baraja vacio(se puede utilizar para las manos)
     * @param tamanio
     */
    public Baraja(int tamanio)
    {
        baraja = new ArrayList<>(tamanio);
    }

    /**
     * Método selector, regresa toda la colección baraja
     * @return
     */
    public ArrayList<Carta> getBaraja()
    {
        return baraja;
    }

    /**
     * Método que muestra en consola todas las cartas
     */
    public void mostrarEnConsola()
    {
        baraja.forEach(carta -> System.out.println(carta.toString()));
    }
}
