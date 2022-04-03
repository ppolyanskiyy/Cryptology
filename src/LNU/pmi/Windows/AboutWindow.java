package LNU.pmi.Windows;

import javax.swing.*;

public class AboutWindow extends JFrame {
    final String message = "Version: 1.0 \nAuthor: Andrii Polyanskiy \nMail: very.cool.java.dev@gmail.com";
    final String title = "About";

    public AboutWindow() {
    }

    public void showWindow() {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
