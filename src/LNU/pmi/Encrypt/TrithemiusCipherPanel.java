package LNU.pmi.Encrypt;

import LNU.pmi.Helpers.Resources;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TrithemiusCipherPanel extends ICipherPanel {

    String[] keyTypeOptions = {"Linear", "Non linear", "Motto"};

    private JPanel mainPanel;

    private JComboBox keyTypeComboBox;
    private JTextArea keyTextArea;
    private JSpinner shiftSpinner;

    private int defaultShiftValue = 126;

    TrithemiusCipherPanel() {
        setupPanel();
    }

    @Override
    public String encode(String data) {
        var keys = getKeys(data);
        int shift = (int)shiftSpinner.getValue();

        StringBuilder sb = new StringBuilder();
        char[] incomingChars = data.toCharArray();

        for (int i = 0; i < incomingChars.length; i++) {
            char c = (char) (((int) incomingChars[i] + keys.get(i)) % shift);
            sb.append(c);
        }

        return sb.toString();
    }

    @Override
    public String decode(String data) {
        var keys = getKeys(data);
        int shift = (int)shiftSpinner.getValue();

        StringBuilder sb = new StringBuilder();
        char[] incomingChars = data.toCharArray();

        for (int i = 0; i < incomingChars.length; i++) {
            char c = (char) (((int) incomingChars[i] + shift - (keys.get(i) % shift)) % shift);
            sb.append(c);
        }

        return sb.toString();
    }

    @Override
    public void test() {

    }

    public ArrayList<Integer> getKeys(String data)
    {
        ArrayList<Integer> result = new ArrayList<>();
        String chosenKeyType = keyTypeComboBox.getSelectedItem().toString();

        var keyText = keyTextArea.getText().toString().trim().split(",");

        if (chosenKeyType == "Linear")
        {
            int A = Integer.parseInt(keyText[0]);
            int B = Integer.parseInt(keyText[1]);

            result = getKeyFromLinearFunction(data, A, B);
        }
        else if (chosenKeyType == "Non linear")
        {
            int A = Integer.parseInt(keyText[0]);
            int B = Integer.parseInt(keyText[1]);
            int C = Integer.parseInt(keyText[2]);

            result = getKeyFromNonLinearFunction(data, A, B, C);
        }
        else if ( chosenKeyType == "Motto")
        {
            result = getKeyFromMotto(data, keyTextArea.getText().toString());
        }
        else
        {
            System.out.printf("[ERROR] Chose wrong key type %s", chosenKeyType);
        }

        return result;
    }

    public ArrayList<Integer> getKeyFromLinearFunction(String input, int A, int B) {
        ArrayList<Integer> result = new ArrayList<>();
        char[] inputAsArray = input.toCharArray();

        for (int i = 0; i < inputAsArray.length; i++) {
            int key = A * i + B;
            result.add(key);
        }
        return result;
    }

    public ArrayList<Integer> getKeyFromNonLinearFunction(String input, int A, int B, int C) {
        ArrayList<Integer> result = new ArrayList<>();
        char[] inputAsArray = input.toCharArray();

        for (int i = 0; i < inputAsArray.length; i++) {
            int key = A * i * i + B * i + C;
            result.add(key);
        }
        return result;
    }

    public ArrayList<Integer> getKeyFromMotto(String input, String motto) {
        ArrayList<Integer> result = new ArrayList<>();
        char[] inputAsArray = input.toCharArray();
        char[] mottoAsArray = motto.toCharArray();

        if (inputAsArray.length > mottoAsArray.length) {
            int count = inputAsArray.length / mottoAsArray.length + 1;
            char[] newMottoAsArray = new char[mottoAsArray.length * count];
            System.arraycopy(mottoAsArray, 0, newMottoAsArray, 0, mottoAsArray.length);

            for (int j = 1; j < count; j++) {
                System.arraycopy(newMottoAsArray, 0, newMottoAsArray, mottoAsArray.length, mottoAsArray.length * j);
            }
            mottoAsArray =  newMottoAsArray;
        }

        for (int i = 0; i < inputAsArray.length; i++) {
            result.add((int)mottoAsArray[i]);
        }
        return result;
    }

    private void setupPanel() {
        keyTypeComboBox.setFont(Resources.TEXT_AREA_FONT);

        keyTextArea.setText("2,4,6");
        keyTextArea.setFont(Resources.TEXT_AREA_FONT);

        // Create shift option
        var spinnerNumberModel = new SpinnerNumberModel(defaultShiftValue, 126, 1000, 1);
        shiftSpinner.setModel(spinnerNumberModel);
        shiftSpinner.setFont(Resources.SHIFT_TEXT_FONT);

        this.setLayout(new GridLayout(0, 1));
        this.add(mainPanel);
    }
}
