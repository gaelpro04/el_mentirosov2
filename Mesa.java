import java.util.ArrayList;
import java.util.Collections;

public class Mesa {

    //Atributos de una mesa de cartas, donde se compone
    //de un pozo donde se pondrán las cartas jugadas, y
    //en dado caso que haya cartas en el cementerio se pondrán
    private Baraja pozo;
    private Baraja cementerio;

    public Mesa() {
        this.pozo = new Baraja();
        this.cementerio = new Baraja();
    }

    public Baraja getPozo()
    {
        return pozo;
    }

    public Baraja getCementerio()
    {
        return cementerio;
    }

    public void setPozo(Baraja pozo) {
        this.pozo = pozo;
    }

    public void agregarBaraja(Carta barajaParaAgregar) {
        pozo.getBaraja().add(barajaParaAgregar);
    }

    public int tamanioPozo () {
        return pozo.getBaraja().size();
    }

    public void mezclarPozo() {
        Collections.shuffle(pozo.getBaraja());
    }

    /*
    @Override
    public String toString() {
        if (!estaVacio) {
            return "Pozo{" +
                    "pozo=" + pozo +
                    '}';
        } else {
            return "Pozo{" +
                    "pozo=" +
                    '}';
        }
    }
    */
}
