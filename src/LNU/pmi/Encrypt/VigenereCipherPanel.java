package LNU.pmi.Encrypt;

import LNU.pmi.Helpers.Resources;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VigenereCipherPanel extends ICipherPanel {

    private JPanel mainPanel;
    private JButton clearButton;
    private JButton resetButton;
    private JTextArea keyTextArea;
    private JSpinner moduloSpinner;
    private JButton generateKeyButton;

    private int defaultShiftValue = 126;
    private String defaultKey = "UKRAINE";

    VigenereCipherPanel() {
        setupPanel();
    }

    @Override
    public String encode(String data) {
        var keys = getKey(data, keyTextArea.getText());
        int modulo = (int)moduloSpinner.getValue();

        StringBuilder sb = new StringBuilder();
        char[] incomingChars = data.toCharArray();

        for (int i = 0; i < incomingChars.length; i++) {
            char c = (char) (((int) incomingChars[i] + keys.get(i)) % modulo);
            sb.append(c);
        }

        return sb.toString();
    }

    @Override
    public String decode(String data) {
        var keys = getKey(data, keyTextArea.getText());
        int modulo = (int)moduloSpinner.getValue();

        StringBuilder sb = new StringBuilder();
        char[] incomingChars = data.toCharArray();

        for (int i = 0; i < incomingChars.length; i++) {
            char c = (char) (((int) incomingChars[i] - keys.get(i) + modulo) % modulo);
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
        var spinnerNumberModel = new SpinnerNumberModel(defaultShiftValue, 126, 1000, 1);
        moduloSpinner.setModel(spinnerNumberModel);
        moduloSpinner.setFont(Resources.SHIFT_TEXT_FONT);

        keyTextArea.setFont(Resources.TEXT_AREA_FONT);

        generateKeyButton.setBackground(Color.LIGHT_GRAY);
        generateKeyButton.addActionListener(e ->
        {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = ThreadLocalRandom.current().nextInt(1, 30);
            Random random = new Random();

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            keyTextArea.setText(generatedString);
        });

        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.addActionListener(e ->
        {
            keyTextArea.setText("");
            moduloSpinner.setValue(0);
        });

        resetButton.setBackground(Color.LIGHT_GRAY);
        resetButton.addActionListener(e ->
        {
            keyTextArea.setText(defaultKey);
            moduloSpinner.setValue(defaultShiftValue);
        });

        this.setLayout(new GridLayout(0, 1));
        this.add(mainPanel);
    }

    public ArrayList<Integer> getKey(String input, String gamma) {
        ArrayList<Integer> result = new ArrayList<>();
        char[] inputAsArray = input.toCharArray();
        char[] gammaAsArray = gamma.toCharArray();

        if (inputAsArray.length > gammaAsArray.length) {
            int count = inputAsArray.length / gammaAsArray.length + 1;
            char[] newGammaAsArray = new char[gammaAsArray.length * count];
            System.arraycopy(gammaAsArray, 0, newGammaAsArray, 0, gammaAsArray.length);

            for (int j = 1; j < count; j++) {
                System.arraycopy(newGammaAsArray, 0, newGammaAsArray, gammaAsArray.length, gammaAsArray.length * j);
            }
            gammaAsArray = newGammaAsArray;
        }

        for (int i = 0; i < inputAsArray.length; i++) {
            result.add((int)gammaAsArray[i]);
        }
        return result;
    }
}
