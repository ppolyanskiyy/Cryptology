package LNU.pmi;

import javax.swing.*;
import java.awt.*;

public class KeyExchangePanel extends JPanel{
    private JPanel mainPanel;
    private JSpinner pSpinner;
    private JSpinner qSpinner;
    private JButton submitButton;
    private JSpinner alicePrivateKeySpinner;
    private JSpinner bobPrivateKeySpinner;
    private JTextArea alicePublicKeyTextArea;
    private JTextArea bobPublicKeyTextArea;
    private JTextArea aliceSecretKeyTextArea;
    private JTextArea bobSecretKeyTextArea;
    private JTextArea instructionsTextArea;

    private SpinnerModel pShiftSpinnerModel;
    private SpinnerModel qShiftSpinnerModel;
    private SpinnerModel alicePublicKeySpinnerModel;
    private SpinnerModel bobPublicKeySpinnerModel;

    KeyExchangePanel()
    {
        this.setLayout(new GridLayout(0,1));
        this.add(mainPanel);

        submitButton.addActionListener(e ->
        {
            exchangeKey();
        });

        pShiftSpinnerModel = new SpinnerNumberModel(23, 1, 10000, 1);
        pSpinner.setModel(pShiftSpinnerModel);

        qShiftSpinnerModel = new SpinnerNumberModel(9, 1, 10000, 1);
        qSpinner.setModel(qShiftSpinnerModel);

        alicePublicKeySpinnerModel = new SpinnerNumberModel(4, 1, 10000, 1);
        alicePrivateKeySpinner.setModel(alicePublicKeySpinnerModel);

        bobPublicKeySpinnerModel = new SpinnerNumberModel(3, 1, 10000, 1);
        bobPrivateKeySpinner.setModel(bobPublicKeySpinnerModel);

        instructionsTextArea.setText(
                """
Choose p and q, where
     * p - prime
     * q - prime and primitive root of p
                """);

    }

    void exchangeKey()
    {
        int p = (int) pSpinner.getValue();
        int q = (int) qSpinner.getValue();

        int alicePrivateKey = (int)alicePrivateKeySpinner.getValue();
        int bobPrivateKey = (int)bobPrivateKeySpinner.getValue();

        int alicePublicKey = ((int)Math.pow(q, alicePrivateKey)) % p;
        int bobPublicKey = ((int)Math.pow(q, bobPrivateKey)) % p;

        int aliceSecretKey = ((int)Math.pow(bobPublicKey, alicePrivateKey)) % p;
        int bobSecretKey = ((int)Math.pow(alicePublicKey, bobPrivateKey)) % p;

        alicePublicKeyTextArea.setText(String.valueOf(alicePublicKey));
        bobPublicKeyTextArea.setText(String.valueOf(bobPublicKey));

        aliceSecretKeyTextArea.setText(String.valueOf(aliceSecretKey));
        bobSecretKeyTextArea.setText(String.valueOf(bobSecretKey));

    }
}
