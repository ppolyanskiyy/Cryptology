package LNU.pmi.Encrypt;

import LNU.pmi.Helpers.Resources;

import javax.swing.*;
import java.awt.*;
import java.math.*;
import java.util.*;


/*
Generating Public Key

1. Select two prime no's. Suppose P = 53 and Q = 59.
Now First part of the Public key  : n = P*Q = 3127.

2. We also need a small exponent say e :
   But e Must be

    -An integer.

    -Not be a factor of n.

    -1 < e < Φ(n) [Φ(n) is discussed below],
     Let us now consider it to be equal to 3.

The public key has been made of n and e

Generating Private Key

1. We need to calculate Φ(n) :
   Such that Φ(n) = (P-1)(Q-1)
      so,  Φ(n) = 3016


2. Now calculate Private Key, d :
   d = (k*Φ(n) + 1) / e for some integer k

3. For k = 2, value of d is 2011.

The private key has been made of d
 */

public class RSACipherPanel extends ICipherPanel {
    private JTextArea publicKeyTextArea;
    private JButton generateKeysButton;
    private JTextArea privateKeyTextArea;
    private JSpinner pSpinner;
    private JSpinner qSpinner;
    private JPanel mainPanel;

    private SpinnerModel pShiftSpinnerModel;
    private SpinnerModel qShiftSpinnerModel;

    int e = 0;
    int d = 0;
    int n = 0;

    RSACipherPanel()
    {
        setupPanel();
    }

    @Override
    public String encode(String data) {
        StringBuilder sb = new StringBuilder();
        char[] incomingChars = data.toCharArray();

        for (int i = 0; i < incomingChars.length; i++) {
            char c = (char) (Math.pow((int) incomingChars[i], e) % n);
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String decode(String data) {
        StringBuilder sb = new StringBuilder();
        char[] incomingChars = data.toCharArray();

        for (int i = 0; i < incomingChars.length; i++) {
            char c = (char) (Math.pow((int) incomingChars[i], d) % n);
            sb.append(c);
        }
        return sb.toString();
    }


    @Override
    public void test() {

    }

    private void setupPanel()
    {
        // Create shift option
        pShiftSpinnerModel = new SpinnerNumberModel(17, 1, 10000, 1);
        pSpinner.setModel(pShiftSpinnerModel);
        pSpinner.setFont(Resources.SHIFT_TEXT_FONT);

        qShiftSpinnerModel = new SpinnerNumberModel(23, 1, 10000, 1);
        qSpinner.setModel(qShiftSpinnerModel);
        qSpinner.setFont(Resources.SHIFT_TEXT_FONT);

        generateKeysButton.setBackground(Color.LIGHT_GRAY);
        generateKeysButton.addActionListener(e ->
        {
            generateKeys();
        });

        publicKeyTextArea.setFont(Resources.TEXT_AREA_FONT);
        privateKeyTextArea.setFont(Resources.TEXT_AREA_FONT);

        // Set up panel
        this.setLayout(new GridLayout(0, 1));
        this.add(mainPanel);
    }

    private void generateKeys()
    {
        int p = (int)qSpinner.getValue();
        int q = (int)pSpinner.getValue();

        n = p * q;
        int z = (p-1) * (q-1);

        for (e = 2; e < z; e++) {

            // e is for public key exponent
            if (gcd(e, z) == 1) {
                break;
            }
        }

        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * z);

            // d is for private key exponent
            if (x % e == 0) {
                d = x / e;
                break;
            }
        }

        publicKeyTextArea.setText(String.format("{e, n} = {%d, %d}", e, n));
        privateKeyTextArea.setText(String.format("{d, n} = {%d, %d}", d, n));
    }

    static int gcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }
}
