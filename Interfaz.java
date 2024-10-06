import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Interfaz {

    private JFrame frame;

    public Interfaz()
    {
        makeFrame();
    }

    public void makeFrame()
    {
        frame = new JFrame("El mentiroso");
        Container containerPane = frame.getContentPane();

        JLabel tituloJuego = new JLabel("El mentiroso");
        containerPane.add(tituloJuego, BorderLayout.NORTH);

        //Botón para jugar
        JButton botonJugar = new JButton("Jugar");
        botonJugar.addActionListener(event -> botonJugar());
        containerPane.add(botonJugar, BorderLayout.NORTH);

        //Bóton para acerca de:
        JButton botonAcerca = new JButton("Acerca de");
        botonAcerca.addActionListener(event -> botonAcerca());
        containerPane.add(botonAcerca);

        frame.pack();
        frame.setVisible(true);
    }

    public void botonJugar()
    {
        System.out.println("Simon");
    }

    public void botonAcerca()
    {
        System.out.println("Simon2");
    }
}
