package LNU.pmi.Encrypt;

import LNU.pmi.Helpers.Resources;

import javax.swing.*;
import java.awt.*;

public class CaesarCipherPanel extends ICipherPanel {
    SpinnerModel shiftSpinnerModel;
    JSpinner shiftSpinner;
    JTextArea alphabetTextArea;

    CaesarCipherPanel()
    {
        createPanel();
    }


    private void createPanel()
    {
        // Crete alphabet area
        alphabetTextArea = new JTextArea();
        alphabetTextArea.setFont(Resources.ALPHABET_TEXT_FONT);
        alphabetTextArea.setText("_abcdefghijklmnopqrstuvwxyz");

        // Create shift option
        shiftSpinnerModel = new SpinnerNumberModel(3, 0, 10, 1);
        shiftSpinner = new JSpinner(shiftSpinnerModel);
        shiftSpinner.setFont(Resources.CAESAR_SHIFT_TEXT_FONT);

        // Set up panel
        this.setLayout(new BorderLayout(10, 10));

        this.add(shiftSpinner, BorderLayout.NORTH);
        this.add(alphabetTextArea, BorderLayout.CENTER);

        this.setVisible(true);
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
}
