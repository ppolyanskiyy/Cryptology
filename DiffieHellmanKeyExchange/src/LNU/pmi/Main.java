package LNU.pmi;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // Create main window
        JFrame window = new JFrame("Diffie-Hellman");
        window.setSize(800, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.add(new KeyExchangePanel());
        window.setVisible(true);

    }
}
