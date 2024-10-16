import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

public class Juego extends javax.swing.JFrame implements MouseListener {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Por el momento la clase consta de una colección de jugadores
    //Una mesa(donde se pondrán las cartas jugadas), una baraja
    //que será la baraja con la que se jugará toda la partida
    private ArrayList<Jugador> jugadores;
    private Mesa mesa;
    private Baraja baraja;
    private int turnoActual;
    private Carta cartaOculta;;
    private Baraja cartasEleccion;
    private boolean veredictoFinal;

    //Atributos para la interfaz de juego
    private JFrame frame;
    private JPanel panelCartas;
    private JPanel panelControlArriba;
    private JPanel panelControlArribaIzquierda;
    private JPanel panelControlArribaDerecha;
    private JPanel panelControlAbajo;
    private JPanel panelMano;
    private JPanel panelPozo;
    private JPanel panelCartasSeleccionadas;

    private JMenuBar menuBar;
    private JMenu menuAyuda;
    private JMenuItem itemSalir;
    private JMenuItem itemCreditos;

    private JLabel estadoJuego;
    private JLabel turno;
    private  JLabel mentiraOverdad;
    private JLabel cartasFaltantes;

    private JButton botonMentira;
    private JButton botonVerdad;
    private JButton botonColocarPozo;
    private JButton botonDesocultar;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor del Juego, ingresa la cantidad de jugadores
     * @param cantJugadores
     */
    public Juego(int cantJugadores) {

        // Lista de jugadores inicializada con una capacidad igual a la cantidad de jugadores
        this.jugadores = new ArrayList<>(cantJugadores);

        // Se crea una instancia de Mesa que representa el área donde se colocarán las cartas durante el juego
        this.mesa = new Mesa();

        // Se crea una carta oculta (no visible para los jugadores), es de "oros", con valor 1,
        // que no está boca arriba, y tiene una imagen que representa el reverso de la carta
        cartaOculta = new Carta("oros", 1, false, "BarajaEspañola/CartaInversa.png");

        // Se inicializa una baraja de cartas (mazo completo) utilizando el constructor por defecto
        // Se espera que esta clase `Baraja` maneje un conjunto de cartas y otras operaciones sobre ellas
        cartasEleccion = new Baraja();

        // El índice del turno actual se inicializa en 0, lo que indica que el primer jugador empezará
        turnoActual = 0;

        // Bandera que indica si el juego ha terminado o no, inicialmente se establece en falso
        veredictoFinal = false;

        // Se crea una baraja de 40 cartas (típico de la baraja española, sin ochos y nueves)
        this.baraja = new Baraja(40);

        // Inicialización de los jugadores, agregando instancias de Jugador a la lista de jugadores
        // Cada jugador se inicializa con un nombre vacío, que se podría completar después
        for (int i = 0; i < cantJugadores; ++i) {
            jugadores.add(new Jugador("")); // Aquí se podría pedir el nombre real del jugador
        }

        // Llama al método hacerManos, que probablemente distribuye las cartas a los jugadores
        hacerManos();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Método privado que hace la interfaz del juego
     */
    private void hacerFrame() {
        // Crea el frame de la ventana con el título "El mentiroso"
        frame = new JFrame("El mentiroso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configura para cerrar la aplicación al cerrar la ventana
        frame.setLayout(new BorderLayout()); // Establece un layout de tipo BorderLayout para organizar los componentes

        // Crea y configura los paneles donde se mostrarán las cartas y otros elementos del juego
        panelCartas = new JPanel(); // Panel principal para las cartas
        panelCartas.setLayout(new BorderLayout()); // Layout para organizar sub-paneles dentro del panelCartas
        panelMano = new JPanel(); // Panel donde se mostrarán las cartas en la mano del jugador
        panelPozo = new JPanel(new BorderLayout()); // Panel donde se mostrará el pozo de cartas
        panelCartasSeleccionadas = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel para las cartas seleccionadas
        panelMano.setLayout(new FlowLayout(FlowLayout.CENTER)); // Layout para organizar las cartas en la mano

        // Crea un JScrollPane para poder desplazarse si la cantidad de cartas en la mano es muy grande
        JScrollPane scrollPane = new JScrollPane(panelMano);
        scrollPane.setPreferredSize(new Dimension(800, 200)); // Tamaño preferido para el área de cartas en la mano
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Scroll horizontal si es necesario
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Scroll vertical si es necesario

        // Añade el JScrollPane y los paneles al panel principal (panelCartas)
        panelCartas.add(scrollPane, BorderLayout.SOUTH); // El panel con las cartas en la mano va en la parte inferior
        panelCartas.add(panelPozo, BorderLayout.CENTER); // El pozo de cartas va en el centro
        panelCartas.add(panelCartasSeleccionadas, BorderLayout.NORTH); // Las cartas seleccionadas van en la parte superior

        // Paneles para los controles en la parte superior e inferior del frame
        panelControlArriba = new JPanel(); // Panel para controles superiores
        panelControlAbajo = new JPanel(); // Panel para controles inferiores
        panelControlArribaIzquierda = new JPanel(); // Subpanel a la izquierda para información del turno
        panelControlArribaDerecha = new JPanel(); // Subpanel a la derecha para estado del juego

        // Creación de los botones de interacción del juego
        botonMentira = new JButton("Mentira"); // Botón para indicar que se está mintiendo
        botonVerdad = new JButton("Verdad"); // Botón para indicar que se dice la verdad
        botonColocarPozo = new JButton("Colocar en Pozo"); // Botón para colocar cartas en el pozo
        botonDesocultar = new JButton("Mostrar"); // Botón para revelar las cartas ocultas

        // Asignar acciones a los botones mediante ActionListeners
        botonDesocultar.addActionListener(evento -> botonDesocultar());
        botonMentira.addActionListener(evento -> botonMentira());
        botonVerdad.addActionListener(evento -> botonVerdad());
        botonColocarPozo.addActionListener(evento -> botonColocarPozo());

        // Deshabilita los botones si la baraja de cartas está vacía
        if (cartasEleccion.getBaraja().isEmpty()) {
            botonMentira.setEnabled(false);
            botonVerdad.setEnabled(false);
            botonColocarPozo.setEnabled(false);
            botonDesocultar.setEnabled(false);
        }

        // Labels para mostrar información del juego
        estadoJuego = new JLabel("Estado Juego"); // Muestra el estado del juego actual
        turno = new JLabel("Turno"); // Muestra el turno actual
        mentiraOverdad = new JLabel("| Mentira o verdad"); // Muestra si la jugada es mentira o verdad
        cartasFaltantes = new JLabel("| 10/10"); // Muestra cuántas cartas faltan

        // Modificación de los colores de fondo de los paneles de cartas
        panelMano.setBackground(new Color(53, 101, 77)); // Fondo verde oscuro para el panel de la mano
        panelCartas.setBackground(new Color(53, 101, 77)); // Fondo verde oscuro para el panel principal de cartas
        panelCartasSeleccionadas.setBackground(new Color(53, 101, 77)); // Fondo verde oscuro para las cartas seleccionadas
        panelPozo.setBackground(new Color(53, 101, 77)); // Fondo verde oscuro para el pozo de cartas

        // Configuración de los paneles de control superiores
        panelControlArribaIzquierda.setLayout(new FlowLayout()); // Layout del panel izquierdo
        panelControlArribaDerecha.setLayout(new FlowLayout()); // Layout del panel derecho
        panelControlArribaIzquierda.add(turno, BorderLayout.WEST); // Añade el label del turno a la izquierda
        panelControlAbajo.add(botonDesocultar, BorderLayout.EAST); // Añade el botón para desocultar cartas a la derecha
        panelControlArribaDerecha.add(estadoJuego, BorderLayout.EAST); // Añade el estado del juego a la derecha
        panelControlArribaDerecha.add(mentiraOverdad, BorderLayout.EAST); // Añade el label "Mentira o verdad" a la derecha
        panelControlArribaDerecha.add(cartasFaltantes, BorderLayout.EAST); // Añade el label "cartas faltantes" a la derecha
        panelControlArriba.add(panelControlArribaIzquierda, BorderLayout.WEST); // Añade el subpanel izquierdo al panel superior
        panelControlArriba.add(panelControlArribaDerecha, BorderLayout.EAST); // Añade el subpanel derecho al panel superior
        panelControlAbajo.add(botonMentira, BorderLayout.CENTER); // Añade el botón de "Mentira" en el centro del panel inferior
        panelControlAbajo.add(botonVerdad, BorderLayout.CENTER); // Añade el botón de "Verdad" en el centro del panel inferior
        panelControlAbajo.add(botonColocarPozo, BorderLayout.CENTER); // Añade el botón "Colocar en Pozo" en el centro del panel inferior

        // Crea la barra de menú y el menú de ayuda
        menuBar = new JMenuBar(); // Barra de menú
        menuAyuda = new JMenu("Ayuda"); // Menú "Ayuda"
        itemSalir = new JMenuItem("Salir"); // Opción para salir del juego
        itemCreditos = new JMenuItem("Créditos"); // Opción para mostrar los créditos
        itemSalir.addActionListener(evento -> botonSalir()); // Asigna acción al botón salir
        itemCreditos.addActionListener(evento -> mostrarCreditos()); // Asigna acción al botón créditos

        // Añade los ítems al menú de ayuda y el menú a la barra de menú
        menuAyuda.add(itemSalir);
        menuAyuda.add(itemCreditos);
        menuBar.add(menuAyuda);
        frame.setJMenuBar(menuBar); // Establece la barra de menú en el frame

        // Añade los paneles de cartas y de control al frame principal
        frame.add(panelCartas); // Añade el panel principal de cartas al frame
        frame.add(panelControlArriba, BorderLayout.NORTH); // Panel de controles superiores en la parte norte
        frame.add(panelControlAbajo, BorderLayout.SOUTH); // Panel de controles inferiores en la parte sur

        // Configura el tamaño de la ventana (frame) y la hace visible
        frame.setSize(1500, 700); // Establece el tamaño de la ventana
        frame.setVisible(true); // Hace visible la ventana
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Hacer mano de cada jugador
     */
    private void hacerManos()
    {

        //Aquí se inicia el constructor de cada jugador de la colección de jugadores
        for (int i = 0; i < jugadores.size(); i++) {
            jugadores.get(i).setNombre("Jugador "+(i+1));
        }

        //Se revuelve la baraja para poder asignar las cartas más fácil a cada jugador
        Collections.shuffle(baraja.getBaraja());

        //Para determinar las cartas por jugador se divide la cantidad de cartas entre
        //los jugadores
        int cartasPorJugador = baraja.getBaraja().size() / jugadores.size();

        //El ciclo para asignar cartas consta del ciclo de cada jugador y el
        //ciclo para asignar cartas por jugador
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < cartasPorJugador; i++) {
                baraja.getBaraja().getFirst().getImagenCarta().addMouseListener(this);
                jugador.getMano().add(baraja.getBaraja().removeFirst());
            }
        }

        //En dado caso que la asignación de cartas por jugador no sea entera y
        //quede un residuo en la baraja esta se alamacenará en el cementerio del juego
        if (!baraja.getBaraja().isEmpty()) {
            for (int i = 0; i < baraja.getBaraja().size(); ++i) {
                baraja.getBaraja().getFirst().getImagenCarta().addMouseListener(this);
                mesa.getCementerio().getBaraja().add(baraja.getBaraja().removeFirst());
            }
        }
    }

    private boolean esFinDelJuego() {
        // Itera a través de la lista de jugadores para verificar si alguno ha terminado su mano
        for (Jugador jugador : jugadores) {
            // Si el jugador actual no tiene más cartas en su mano (mano vacía), se considera el fin del juego
            if (jugador.getMano().isEmpty()) {
                return true; // Si un jugador ha vaciado su mano, el juego termina y retorna true
            }
        }
        // Si ningún jugador ha vaciado su mano, el juego continúa y retorna false
        return false;
    }

    private Jugador determinarGanador() {
        // Itera a través de la lista de jugadores para verificar si alguno ha vaciado su mano
        for (Jugador jugador : jugadores) {
            // Si el jugador actual tiene su mano vacía, se considera que es el ganador
            if (jugador.getMano().isEmpty()) {
                return jugador; // Devuelve el jugador que ha ganado (mano vacía)
            }
        }
        // Si ningún jugador ha vaciado su mano, se devuelve null (no hay ganador aún)
        return null;
    }

    private void mostrarMano(Jugador jugador) {
        // Elimina todos los componentes del panel donde se muestran las cartas de la mano del jugador
        panelMano.removeAll();

        // Itera sobre las cartas en la mano del jugador y añade la imagen de cada carta al panel
        for (Carta carta : jugador.getMano()) {
            panelMano.add(carta.getImagenCarta()); // Añade la representación gráfica de la carta al panel
        }

        // Actualiza el texto que muestra cuántas cartas le quedan al jugador
        cartasFaltantes.setText("| " + (jugador.getMano().size()));

        // Fuerza el repintado del panel para que los cambios en la visualización sean visibles
        panelMano.repaint();

        // Revalida el layout del panel para asegurar que se actualice correctamente tras los cambios
        panelMano.revalidate();
    }

    private void cartasPanelCartasSeleccionadas() {
        // Elimina todas las cartas previamente mostradas en el panel de cartas seleccionadas
        panelCartasSeleccionadas.removeAll();

        // Verifica si el veredicto actual es "MENTIRA"
        if (veredicto == Veredicto.MENTIRA) {
            // Obtiene el jugador anterior al jugador actual (en función del turno) y verifica si su mano tiene menos de 4 cartas
            if (jugadores.get((turnoActual - 1 + jugadores.size()) % jugadores.size()).getMano().size() < 4) {
                // Si la mano del jugador tiene menos de 4 cartas, se crea una nueva baraja de 40 cartas
                Baraja baraja = new Baraja(40);
                Collections.shuffle(baraja.getBaraja()); // Se mezclan las cartas de la baraja aleatoriamente

                // Itera sobre las cartas de la baraja mezclada, añadiendo su imagen al panel de cartas seleccionadas
                for (int i = 0; i < cartasEleccion.getBaraja().size(); ++i) {
                    panelCartasSeleccionadas.add(baraja.getBaraja().get(i).getImagenCarta()); // Añade la imagen de la carta al panel
                }
            } else {
                // Si el jugador tiene 4 o más cartas en su mano, se crea una copia (clon) de la mano del jugador
                ArrayList<Carta> manoClon = new ArrayList<>(jugadores.get((turnoActual - 1 + jugadores.size()) % jugadores.size()).getMano());
                Collections.shuffle(manoClon); // Mezcla la copia de la mano del jugador aleatoriamente

                // Itera sobre las cartas seleccionadas y añade una carta de la mano clonada (mezclada) al panel
                for (int i = 0; i < cartasEleccion.getBaraja().size(); ++i) {
                    panelCartasSeleccionadas.add(manoClon.remove(0).getImagenCarta()); // Añade la imagen de la carta y elimina la primera carta clonada
                }
            }
        } else {
            // Si el veredicto no es "MENTIRA", simplemente añade las cartas actuales de "cartasEleccion" al panel
            for (int i = 0; i < cartasEleccion.getBaraja().size(); ++i) {
                panelCartasSeleccionadas.add(cartasEleccion.getBaraja().get(i).getImagenCarta()); // Añade la imagen de la carta al panel
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método de juego, es decir donde ya se juega
     */
    //Esto asegura que la creación de la interfaz gráfica ocurra en el hilo adecuado.
    public void jugar() {
        SwingUtilities.invokeLater(() -> {
            hacerFrame();
            turno.setText("Turno de jugador: " + (turnoActual + 1));
            mostrarMano(jugadores.get(turnoActual));
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //AREA DE BOTONES

    //Definición del enum Veredicto
    public enum Veredicto {
        MENTIRA, VERDAD, NEUTRO
    }

    //Cambiar el atributo veredicto a tipo Veredicto
    private Veredicto veredicto;

    //Modificación del método auxiliar para usar el enum
    private void procesarVeredicto(Veredicto veredicto) {
        botonVerdad.setEnabled(false);
        botonMentira.setEnabled(false);
        //Almacena el veredicto usando el enum
        this.veredicto = veredicto;
    }

    // Métodos que llaman al auxiliar con el enum
    private void botonMentira() {

        // Desactiva los botones de verdad y mentira
        botonVerdad.setEnabled(false);
        botonMentira.setEnabled(false);

        // Si el veredicto final ya se ha decidido
        if (veredictoFinal) {
            // Calcula el turno anterior al jugador actual
            int turnoAnterior = (turnoActual - 1 + jugadores.size()) % jugadores.size();

            // Habilita el panel de la mano para el jugador
            panelMano.setEnabled(true);

            // Si el veredicto es "MENTIRA"
            if (veredicto == Veredicto.MENTIRA) {
                // Mensaje indicando que el jugador anterior mintió y el actual acertó
                mentiraOverdad.setText("El jugador anterior ha mentido, ¡acertaste!");
                estadoJuego.setText("El jugador anterior obtendrá todas las cartas del pozo.");

                // Mueve todas las cartas del pozo a la mano del jugador anterior
                for (int i = 0; i < mesa.getPozo().getBaraja().size(); ++i) {
                    jugadores.get(turnoAnterior).getMano().add(mesa.getPozo().getBaraja().removeFirst());
                }
                jugadores.get(turnoAnterior).getMano().add(mesa.getPozo().getBaraja().removeFirst());

                // Limpia y actualiza la visualización de la mano
                panelMano.removeAll();
                panelMano.repaint();
                panelMano.revalidate();

                // Limpia el pozo
                mesa.getPozo().getBaraja().clear();

            } else {
                // Si el veredicto es "VERDAD"
                mentiraOverdad.setText("El jugador anterior ha dicho la verdad, ¡no acertaste!");
                estadoJuego.setText("El jugador actual obtendrá todas las cartas del pozo.");

                // Mueve todas las cartas del pozo a la mano del jugador actual
                for (int i = 0; i < mesa.getPozo().getBaraja().size(); ++i) {
                    jugadores.get(turnoActual).getMano().add(mesa.getPozo().getBaraja().removeFirst());
                }
                jugadores.get(turnoActual).getMano().add(mesa.getPozo().getBaraja().removeFirst());

                // Limpia y actualiza la visualización de la mano
                panelMano.removeAll();
                panelMano.repaint();
                panelMano.revalidate();

                // Limpia el pozo
                mesa.getPozo().getBaraja().clear();
            }

            // Limpia el panel del pozo (visualmente)
            panelPozo.removeAll();

            // Reinicia el estado del juego para el siguiente turno
            veredictoFinal = false;

            // Muestra la mano del jugador actual
            mostrarMano(jugadores.get(turnoActual));

            // Limpia el panel de cartas seleccionadas
            panelCartasSeleccionadas.removeAll();

            // Actualiza el estado del juego
            estadoJuego.setText("Escoge máximo tres cartas");

            // Resetea el veredicto
            veredicto = null;

        } else {
            // Si no se ha llegado al veredicto final, habilita el botón para colocar en el pozo
            botonColocarPozo.setEnabled(true);

            // Procesa el veredicto como "MENTIRA"
            procesarVeredicto(Veredicto.MENTIRA);
        }
    }

    private void botonVerdad() {

        // Desactiva los botones de verdad y mentira
        botonVerdad.setEnabled(false);
        botonMentira.setEnabled(false);

        // Si ya se ha decidido el veredicto final
        if (veredictoFinal) {
            // Habilita el panel de la mano para el jugador
            panelMano.setEnabled(true);

            // Limpia el panel de la mano del jugador actual
            panelMano.removeAll();
            panelMano.repaint();
            panelMano.revalidate();

            // Muestra la mano del jugador actual en el panel
            mostrarMano(jugadores.get(turnoActual));

            // Limpia el panel de cartas seleccionadas
            panelCartasSeleccionadas.removeAll();

            // Actualiza el estado del juego, solicitando al jugador que escoja hasta tres cartas
            estadoJuego.setText("Escoge máximo tres cartas");

            // Resetea el estado del veredicto final y el veredicto general
            veredictoFinal = false;
            veredicto = null;

        } else {
            // Si aún no se ha decidido el veredicto final, procesa el veredicto como "VERDAD"
            procesarVeredicto(Veredicto.VERDAD);

            // Habilita el botón para colocar cartas en el pozo
            botonColocarPozo.setEnabled(true);
        }
    }

    private void botonDesocultar() {
        // Desactiva el botón de desocultar para evitar múltiples pulsaciones
        botonDesocultar.setEnabled(false);

        // Habilita los botones de mentira y verdad para permitir al jugador hacer una elección
        botonMentira.setEnabled(true);
        botonVerdad.setEnabled(true);

        // Muestra la mano del jugador actual en la interfaz
        mostrarMano(jugadores.get(turnoActual));
    }

    private void botonColocarPozo() {
        // Verifica si el juego ha llegado a su fin
        if (esFinDelJuego()) {
            // Limpia el panel de control superior
            panelControlArriba.removeAll();

            // Crea un mensaje que indica quién ha ganado el juego
            JLabel mensajeFinal = new JLabel("EL " + determinarGanador().getNombre() + " HA GANADO EL JUEGO!!!");
            panelControlArriba.add(mensajeFinal, BorderLayout.CENTER);

            // Limpia el panel de la mano del jugador
            panelMano.removeAll();
            panelMano.repaint();
            panelMano.revalidate();

            // Limpia el pozo y el panel de cartas seleccionadas
            panelPozo.removeAll();
            panelCartasSeleccionadas.removeAll();

            // Desactiva los botones de colocar en el pozo, verdad y mentira
            botonColocarPozo.setEnabled(false);
            botonVerdad.setEnabled(false);
            botonMentira.setEnabled(false);
            panelControlAbajo.setVisible(false);

            // Crea un botón de salir que finaliza el juego
            JButton botonFinal = new JButton("Salir");
            botonFinal.addActionListener(e -> botonSalir());
            panelControlArriba.add(botonFinal, BorderLayout.EAST);
        }

        // Desactiva los botones de colocar en el pozo, verdad y mentira
        botonColocarPozo.setEnabled(false);
        botonMentira.setEnabled(false);
        botonVerdad.setEnabled(false);

        // Habilita el botón de desocultar
        botonDesocultar.setEnabled(true);

        // Marca que el veredicto final se ha decidido
        veredictoFinal = true;
        mentiraOverdad.setText("Veredicto aún por determinar...");

        // Si hay cartas en la baraja de elección, las coloca en el pozo
        if (!cartasEleccion.getBaraja().isEmpty()) {
            for (int i = 0; i < cartasEleccion.getBaraja().size(); ++i) {
                mesa.getPozo().getBaraja().add(cartasEleccion.getBaraja().get(i));
            }
        }

        // Si hay cartas en el pozo, se muestra la carta oculta
        if (!mesa.getPozo().getBaraja().isEmpty()) {
            panelPozo.add(cartaOculta.getImagenCarta());
            panelPozo.repaint();
            panelPozo.revalidate();
        }

        // Avanza el turno al siguiente jugador
        turnoActual = (turnoActual + 1) % jugadores.size();
        turno.setText("Turno de jugador: " + (turnoActual + 1));
        cartasFaltantes.setText("| " + String.valueOf(jugadores.get(turnoActual).getMano().size()));

        // Limpia el panel de la mano y muestra las cartas del nuevo jugador
        panelMano.removeAll();
        for (Carta carta : jugadores.get(turnoActual).getMano()) {
            panelMano.add(carta.getImagenOculta());
        }
        panelMano.repaint();
        panelMano.revalidate();

        // Actualiza el estado del juego
        estadoJuego.setText("Elige si es verdad o mentira las cartas ingresadas");

        // Actualiza el panel de cartas seleccionadas
        cartasPanelCartasSeleccionadas();

        // Deshabilita la interacción en el panel de la mano
        panelMano.setEnabled(false);

        // Reinicia la baraja de cartas elegidas
        cartasEleccion = new Baraja();
    }


    /**
     * Método que es el botón para salir del juego
     */
    private void botonSalir()
    {
        System.exit(0);
    }

    private void mostrarCreditos() {
        System.out.println("\nHecho por: ");
        System.out.println("Gael Jovani López García");
        System.out.println("Saúl Iván Ramírez Heraldez");
    }

    //En lugar de crear una nueva lista de cartasEliminar en cada clic, puedes crearla una sola vez y vaciarla cada vez que la necesites.
    private final ArrayList<Carta> cartasEliminar = new ArrayList<>();

    @Override
    public void mouseClicked(MouseEvent e) {
        // Vacía la lista de cartas a eliminar antes de cada uso
        cartasEliminar.clear();

        // Verifica si el veredicto final no ha sido determinado
        if (!veredictoFinal) {
            // Itera a través de las cartas en la mano del jugador actual
            for (Carta carta : jugadores.get(turnoActual).getMano()) {
                // Comprueba si la carta clickeada es igual a la imagen de la carta en la mano
                if (e.getSource() == carta.getImagenCarta()) {
                    // Si el veredicto es mentira o verdad, habilita el botón para colocar en el pozo
                    if (veredicto == Veredicto.MENTIRA || veredicto == Veredicto.VERDAD) {
                        botonColocarPozo.setEnabled(true);
                    }

                    // Habilita los botones de mentira y verdad
                    botonMentira.setEnabled(true);
                    botonVerdad.setEnabled(true);

                    // Verifica si se han seleccionado menos de 3 cartas
                    if (cartasEleccion.getBaraja().size() < 3) {
                        // Agrega la carta seleccionada a la lista de cartas a eliminar y a la baraja de elección
                        cartasEliminar.add(carta);
                        cartasEleccion.getBaraja().add(carta);

                        // Añade la imagen de la carta seleccionada al panel de cartas seleccionadas
                        panelCartasSeleccionadas.add(carta.getImagenCarta());

                        // Actualiza la interfaz gráfica del panel de cartas seleccionadas
                        panelCartasSeleccionadas.repaint();
                        panelCartasSeleccionadas.revalidate();
                    } else {
                        // Muestra un mensaje si se ha alcanzado el máximo de cartas seleccionadas
                        estadoJuego.setText("Has alcanzado el máximo de cartas");
                    }
                }
            }

            // Elimina las cartas seleccionadas de la mano del jugador actual
            for (Carta carta : cartasEliminar) {
                jugadores.get(turnoActual).getMano().remove(carta);
            }

            // Actualiza el texto que muestra cuántas cartas le quedan al jugador actual
            cartasFaltantes.setText("| " + jugadores.get(turnoActual).getMano().size());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
