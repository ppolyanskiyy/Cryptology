package LNU.pmi;

import javax.print.DocFlavor;
import javax.swing.*;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.TreeMap;

public class StatisticWindow extends JFrame {
    GUI gui;

    String columns[] = {"Letter", "Amount"};

    TreeMap<Character, Integer> currentStatistic;

    JTable statisticTable;
    JScrollPane scrollPane;

    StatisticWindow(GUI gui)
    {
        this.gui = gui;

        this.setLocationRelativeTo(gui.window);
        this.setSize(400, 600);

    }

    public void showWindow()
    {
        createTable();
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    private void createTable()
    {
        String inputText = gui.inputTextArea.getText();

        // Creating a HashMap containing char
        // as a key and occurrences as  a value
        currentStatistic = new TreeMap<Character, Integer>();

        // Converting given string to char array

        char[] strArray = inputText.toCharArray();

        // checking each char of strArray
        for (char c : strArray) {
            if (currentStatistic.containsKey(c)) {

                // If char is present in charCountMap,
                // incrementing it's count by 1
                currentStatistic.put(c, currentStatistic.get(c) + 1);
            }
            else {

                // If char is not present in charCountMap,
                // putting this char to charCountMap with 1 as it's value
                currentStatistic.put(c, 1);
            }
        }

        String data[][] = new String[currentStatistic.size()][2];

        int count = 0;
        for(HashMap.Entry<Character, Integer> entry : currentStatistic.entrySet()){
            data[count][0] = entry.getKey().toString();
            data[count][1] = entry.getValue().toString();
            count++;
        }

        statisticTable = new JTable(data, columns);

        if (scrollPane != null)
        {
            this.remove(scrollPane);
        }

        scrollPane = new JScrollPane(statisticTable);
        this.add(scrollPane);

    }


}
