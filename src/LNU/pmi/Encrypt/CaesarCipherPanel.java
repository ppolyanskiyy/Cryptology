package LNU.pmi.Encrypt;

import LNU.pmi.Helpers.Resources;

import javax.swing.*;
import java.awt.*;

public class CaesarCipherPanel extends ICipherPanel {
    private SpinnerModel shiftSpinnerModel;
    private JSpinner shiftSpinner;
    private JTextArea alphabetTextArea;

    private JPanel mainPanel;
    private JProgressBar progressBar1;

    CaesarCipherPanel()
    {
        setupPanel();
    }

    @Override
    public String encode(String data) {
        //data = data.toLowerCase();
        String alphabet = alphabetTextArea.getText();
        int shift = (int)shiftSpinnerModel.getValue();
        String cipherText = "";

        for (int i = 0; i < data.length(); i++) {
            int charPosition = alphabet.indexOf(data.charAt(i));
            if (charPosition != -1)
            {
                int key = (shift + charPosition) % alphabet.length();
                cipherText += alphabet.charAt(key);
            }
            else
            {
                cipherText += data.charAt(i);
            }
        }

        return cipherText;
    }

    @Override
    public String decode(String data) {
        //data = data.toLowerCase();
        String alphabet = alphabetTextArea.getText();
        int shift = (int)shiftSpinnerModel.getValue();
        String cipherText = "";

        for (int i = 0; i < data.length(); i++) {
            int charPosition = alphabet.indexOf(data.charAt(i));
            if (charPosition != -1)
            {
                int key = (charPosition - shift) % alphabet.length();
                if (key < 0)
                {
                    key += alphabet.length();
                }
                cipherText += alphabet.charAt(key);
            }
            else
            {
                cipherText += data.charAt(i);
            }
        }
        return cipherText;
    }


    @Override
    public void test() {

    }


    private void setupPanel()
    {
        // Crete alphabet area
        alphabetTextArea.setFont(Resources.ALPHABET_TEXT_FONT);
        alphabetTextArea.setText("_abcdefghijklmnopqrstuvwxyz");

        // Create shift option
        shiftSpinnerModel = new SpinnerNumberModel(3, 1, 26, 1);
        shiftSpinner.setModel(shiftSpinnerModel);
        shiftSpinner.setFont(Resources.SHIFT_TEXT_FONT);

        // Set up panel
        this.setLayout(new GridLayout(0, 1));
        this.add(mainPanel);
    }
}
