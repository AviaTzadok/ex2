package gameClient.util;

import gameClient.Arena;

import javax.swing.*;
import java.awt.*;

public class Gframe extends JFrame {
    private panel pa;
    private Arena _ar;
    public Gframe(String a){
        super(a);
        _ar= new Arena();
        pa= new panel();
        this.add(pa);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
