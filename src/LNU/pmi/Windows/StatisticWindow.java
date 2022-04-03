package LNU.pmi.Windows;

import LNU.pmi.GUI;

import javax.swing.*;
import java.util.HashMap;
import java.util.TreeMap;

public class StatisticWindow extends JFrame {
    GUI gui;

    String[] columns = {"Letter", "Amount"};

    TreeMap<Character, Integer> currentStatistic;

    JTable statisticTable;
    JScrollPane scrollPane;

    public StatisticWindow(GUI gui) {
        this.gui = gui;

        this.setLocationRelativeTo(gui.window);
        this.setSize(400, 600);

    }

    public void showWindow() {
        createTable();
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    private void createTable() {
        String inputText = gui.cipherTextArea.getText();

        currentStatistic = new TreeMap<Character, Integer>();
        char[] strArray = inputText.toCharArray();

        for (char c : strArray) {
            if (currentStatistic.containsKey(c)) {
                currentStatistic.put(c, currentStatistic.get(c) + 1);
            } else {
                currentStatistic.put(c, 1);
            }
        }

        String[][] data = new String[currentStatistic.size()][2];

        int count = 0;
        for (HashMap.Entry<Character, Integer> entry : currentStatistic.entrySet()) {
            data[count][0] = entry.getKey().toString();
            data[count][1] = entry.getValue().toString();
            count++;
        }

        statisticTable = new JTable(data, columns);

        if (scrollPane != null) {
            this.remove(scrollPane);
        }

        scrollPane = new JScrollPane(statisticTable);
        this.add(scrollPane);
    }
}
