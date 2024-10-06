public class Carta {
    /*
    String[] bancoPalos = {"oro", "copa", "espada", "basto"};
     */

    //Atributos que componen una carta española
    private String palo;
    private int valor;
    private boolean visibilidad;

    /**
     * Constructor preterminado de clase carta
     */
    public Carta()
    {
        palo = "oro";
        valor = 1;
        visibilidad = true;
    }

    /**
     * Constructor con parametros requeridos
     * @param palo
     * @param valor
     * @param visibilidad
     */
    public Carta(String palo, int valor, boolean visibilidad)
    {
        this.palo = palo;
        this.valor = valor;
        this.visibilidad = visibilidad;
    }

    /**
     * Constructor con parametro carta para una rápida absorción de atributos
     * @param carta
     */
    public Carta(Carta carta)
    {

    }

    /**
     * Método mutador, cambia el atributo valor de carta.
     * @param valor
     */
    public void setValor(int valor)
    {
        this.valor = valor;
    }

    /**
     * Método mutador, cambia el atributo palo de carta.
     * @param palo
     */
    public void setPalo(String palo)
    {
        this.palo = palo;
    }

    /**
     * Método mutador, cambia el atributo visibilidad de carta.
     * @param visibilidad
     */
    public void setVisibilidad(boolean visibilidad)
    {
        this.visibilidad = visibilidad;
    }

    /**
     * Método mutador, cambia la carta
     * @param carta
     */
    public void setCarta(Carta carta)
    {
        this.valor = carta.getValor();
        this.palo = carta.getPalo();
        this.visibilidad = carta.getVisibilidad();
    }

    /**
     * toString de la clase
     * @return
     */
    //En dado caso que visibilidad sea negativa(no se puede ver el contenido de la carta) el toString se verá con gatos
    public String toString()
    {
        if (visibilidad) {
            if (valor == 10) {
                return "sota - " + palo;
            } else if (valor == 11) {
                return "caballero - " + palo;
            } else if (valor == 12) {
                return "rey - " + palo;
            } else {
                return valor + " - " + palo;
            }
        }
        return "## - ####";
    }

    /**
     * Método selector, regresa el valor
     * @return
     */
    public int getValor()
    {
        return valor;
    }

    /**
     * Método selector, regresa el palo en formato String
     * @return
     */
    public String getPalo()
    {
        return palo;
    }

    /**
     * Método selector, regresa la visibilidad de la carta
     * @return
     */
    public boolean getVisibilidad()
    {
        return visibilidad;
    }
}

