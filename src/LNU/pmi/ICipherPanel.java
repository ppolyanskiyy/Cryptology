package LNU.pmi;

import javax.swing.*;

public abstract class ICipherPanel extends JPanel {
    public abstract String decode(String data);
    public abstract String encode(String data);

}
