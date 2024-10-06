import InterfazYCartasVisuales.Interfaz;

public class Main {

    public static void main(String[] args) {

        //TEST CARTA
        Carta carta = new Carta("oro", 1, true);

        System.out.println("toString de carta true\n");
        System.out.println(carta);

        System.out.println("toString de carta false\n");
        carta.setVisibilidad(false);
        System.out.println(carta);

        //No estoy seguro que imprime~~
        Baraja barajita = new Baraja();
        barajita.mostrarEnConsola();

        Interfaz interfaz = new Interfaz();

    }
}
