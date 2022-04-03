package LNU.pmi.Encrypt;

import javax.swing.*;

public abstract class ICipherPanel extends JPanel {
    public abstract String decode(String data);
    public abstract String encode(String data);
    public abstract void test();

}
