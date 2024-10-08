import java.util.ArrayList;
import java.util.Collections;

public class Mesa {

    //Atributos de una mesa de cartas, donde se compone
    //de un pozo donde se pondrán las cartas jugadas, y
    //en dado caso que haya cartas en el cementerio se pondrán
    private ArrayList<Carta> pozo;
    private ArrayList<Carta> cementerio;

    public Mesa() {
        this.pozo = new ArrayList<>();
        this.cementerio = new ArrayList<>();
    }

    public ArrayList<Carta> getPozo()
    {
        return pozo;
    }

    public ArrayList<Carta> getCementerio()
    {
        return cementerio;
    }

    public void setPozo(ArrayList<Carta> pozo) {
        this.pozo = pozo;
    }

    public void agregarBaraja(Carta barajaParaAgregar) {
        pozo.add(barajaParaAgregar);
    }

    public int tamanioPozo () {
        return pozo.size();
    }

    public void mezclarPozo() {
        Collections.shuffle(this.pozo);
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
