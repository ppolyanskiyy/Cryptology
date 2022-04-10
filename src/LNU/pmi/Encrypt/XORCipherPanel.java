package LNU.pmi.Encrypt;

import LNU.pmi.Helpers.Resources;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class XORCipherPanel extends ICipherPanel {
    private JPanel mainPanel;
    private JTextArea gammaTextArea;
    private JButton clearButton;
    private JButton generateGammaButton;

    private int shift = 2;

    public XORCipherPanel()
    {
        setupPanel();
    }

    @Override
    public String encode(String data) {
        var keys = getKeyFromGamma(data, gammaTextArea.getText());

        StringBuilder sb = new StringBuilder();
        char[] incomingChars = data.toCharArray();

        for (int i = 0; i < incomingChars.length; i++) {
            char c = (char)(Integer.valueOf(incomingChars[i]) ^ keys.get(i));
            sb.append(c);
        }

        return sb.toString();
    }

    @Override
    public String decode(String data) {
        return encode(data);
    }

    @Override
    public void test() {

    }

    public ArrayList<Integer> getKeyFromGamma(String input, String gamma) {
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

    private void setupPanel()
    {
        generateGammaButton.setBackground(Color.LIGHT_GRAY);
        generateGammaButton.addActionListener(e ->
        {
            int leftLimit = 33; // symbol '!'
            int rightLimit = 126; // symbol '~'
            int targetStringLength = ThreadLocalRandom.current().nextInt(1, 30);
            Random random = new Random();

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            gammaTextArea.setText(generatedString);
        });

        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.addActionListener(e ->
        {
            gammaTextArea.setText("");
        });

        gammaTextArea.setFont(Resources.TEXT_AREA_FONT);

        this.setLayout(new GridLayout(0, 1));
        this.add(mainPanel);
    }
}
