package LNU.pmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.IdentityHashMap;
import java.util.Map;

public class CipherSettingsPanel extends JPanel implements ActionListener {
    GUI gui;

    Map<String, ICipherPanel> cipherPanels;

    JComboBox cipherOptionsComboBox;
    CardLayout cardLayout = new CardLayout();
    JPanel cipherPanelsContainer;

    public CipherSettingsPanel(GUI gui)
    {
        this.gui = gui;

        // Set up cipher panels
        cipherPanels = new IdentityHashMap<>();
        cipherPanels.put("Caesar", new CaesarCipherPanel());
        //cipherPanels.put("Trithemius", new TrithemiusCipherPanel());
        // TODO

        // Set up panels container
        cipherPanelsContainer = new JPanel();
        cipherPanelsContainer.setLayout(cardLayout);

        for (var cipherPanel : cipherPanels.entrySet())
        {
            cipherPanelsContainer.add(cipherPanel.getValue(), cipherPanel.getKey());
        }

        // Create combo box
        cipherOptionsComboBox = new JComboBox(cipherPanels.keySet().toArray());
        cipherOptionsComboBox.setFont(Resources.CIPHER_OPTION_FONT);
        cipherOptionsComboBox.addActionListener(this);
        cipherOptionsComboBox.setActionCommand("CipherOptionsComboBox");

        // Set up component
        this.setLayout(new BorderLayout(1, 50));
        this.add(cipherOptionsComboBox, BorderLayout.NORTH);
        this.add(cipherPanelsContainer, BorderLayout.CENTER);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox comboBox = (JComboBox)e.getSource();
        String selected = comboBox.getSelectedItem().toString();

        System.out.println(selected);
        cardLayout.show(cipherPanelsContainer, selected);

        this.revalidate();
        this.repaint();
    }

    public void encode()
    {
        String cipherItem = cipherOptionsComboBox.getSelectedItem().toString();
        ICipherPanel cipher = cipherPanels.get(cipherItem);

        gui.cipherTextArea.setText(cipher.encode(gui.inputTextArea.getText()));
    }

    public void decode()
    {
        String cipherItem = cipherOptionsComboBox.getSelectedItem().toString();
        ICipherPanel cipher = cipherPanels.get(cipherItem);

        gui.cipherTextArea.setText(cipher.decode(gui.inputTextArea.getText()));
    }
}
