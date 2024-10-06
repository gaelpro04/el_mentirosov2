import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Pozo {
    private ArrayList<Carta> pozo;
    private boolean estaVacio = true;

    public Pozo() {
        this.pozo = new ArrayList();
    }

    public ArrayList<Carta> getPozo() {
        return pozo;
    }

    public void setPozo(ArrayList<Carta> pozo) {
        this.pozo = pozo;
    }

    public void agregarBaraja(Carta barajaParaAgregar) {
        pozo.add(barajaParaAgregar);
    }

    public boolean isEstaVacio() {
        return estaVacio;
    }

    public void setEstaVacio(boolean estaVacio) {
        this.estaVacio = estaVacio;
    }

    public int contarCartasEnPozo () {
        return pozo.size();
    }

    public void mezclarPozo() {
        Collections.shuffle(this.pozo);
    }

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
}
