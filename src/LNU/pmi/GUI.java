package LNU.pmi;

import LNU.pmi.Encrypt.CipherSettingsPanel;
import LNU.pmi.Helpers.FileFunctions;
import LNU.pmi.Helpers.Resources;
import LNU.pmi.Windows.AboutWindow;
import LNU.pmi.Windows.StatisticWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI implements ActionListener
{
    public JFrame window;

    // Main panels
    JPanel centerMainPanel;
    JPanel bottomMainPanel;

    // Input text
    JPanel inputTextPanel;
    public JTextArea inputTextArea;
    JScrollPane inputTextScrollPane;
    JButton inputTextClearButton;
    JButton inputTextFillButton;

    // Cipher text
    JPanel cipherTextPanel;
    public JTextArea cipherTextArea;
    JScrollPane cipherTextScrollPane;
    JButton cipherTextClearButton;
    JButton cipherTextSwapButton;


    // Cipher settings panel
    JPanel cipherOptionsPanel;
    CipherSettingsPanel cipherSettingsPanel;

    // Operation panel
    JPanel operationsPanel;
    JButton decodeButton, encodeButton, statisticButton, testButton;
    StatisticWindow statisticWindow;
    
    // Top menu bar
    JMenuBar menuBar;
    JMenu menuFile, menuHelp;
    
    // File menu
    JMenuItem menuFileItemNew, menuFileItemOpen, menuFileItemSave, menuFileItemSaveAs;
    FileFunctions fileFunctions = new FileFunctions(this);

    // Help menu
    JMenuItem menuHelpItemAbout, menuHelpItemExit;
    AboutWindow aboutWindow;
    

    public GUI()
    {
        createWindow();

        createInputTextPanel();
        createCipherOptionsPanel();
        createCipherTextPanel();
        createOperationsPanel();

        createMenuBar();
        createFileMenu();
        createHelpMenu();

        // Window
        window.setVisible(true);
    }
    

    private void createWindow()
    {
        // Create center panel
        centerMainPanel = new JPanel(new GridLayout(1,3));

        // Create bottom panel
        bottomMainPanel = new JPanel();
        bottomMainPanel.setBackground(Color.DARK_GRAY);
        bottomMainPanel.setPreferredSize(new Dimension(1, 100));

        // Create main window
        window = new JFrame("Crypt");
        window.setSize(1200, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());
        window.add(centerMainPanel, BorderLayout.CENTER);
        window.add(bottomMainPanel, BorderLayout.SOUTH);

        // Create about window
        aboutWindow = new AboutWindow();
        aboutWindow.setLocationRelativeTo(this.window);
    }

    private void createInputTextPanel()
    {
        // Create text area
        inputTextArea = new JTextArea();
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputTextArea.setFont(Resources.TEXT_AREA_FONT);

        // Create scroll pane
        inputTextScrollPane = new JScrollPane(inputTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        inputTextScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Create clear button
        inputTextClearButton = new JButton("Clear");
        inputTextClearButton.setBackground(Color.LIGHT_GRAY);
        inputTextClearButton.setPreferredSize(new Dimension(-1, 30));
        inputTextClearButton.addActionListener(this);
        inputTextClearButton.setActionCommand("InputTextClearAction");

        //Create fill button
        inputTextFillButton = new JButton("Fill");
        inputTextFillButton.setBackground(Color.LIGHT_GRAY);
        inputTextFillButton.setPreferredSize(new Dimension(-1, 30));
        inputTextFillButton.addActionListener(this);
        inputTextFillButton.setActionCommand("InputTextFillAction");

        // Create panel for buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.add(inputTextClearButton);
        buttonsPanel.add(inputTextFillButton);

        // Create panel
        inputTextPanel = new JPanel(new BorderLayout());
        inputTextPanel.setBackground(Color.LIGHT_GRAY);
        inputTextPanel.setBorder(BorderFactory.createTitledBorder("Input text"));
        inputTextPanel.add(inputTextScrollPane, BorderLayout.CENTER);
        inputTextPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add panel to main one
        centerMainPanel.add(inputTextPanel);
    }

    
    private void createCipherTextPanel()
    {
        // Create text area
        cipherTextArea = new JTextArea();
        cipherTextArea.setLineWrap(true);
        cipherTextArea.setWrapStyleWord(true);
        cipherTextArea.setEditable(false);
        cipherTextArea.setFont(Resources.TEXT_AREA_FONT);

        // Create scroll pane
        cipherTextScrollPane = new JScrollPane(cipherTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        cipherTextScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Create clear button
        cipherTextClearButton = new JButton("Clear");
        cipherTextClearButton.setBackground(Color.LIGHT_GRAY);
        cipherTextClearButton.setPreferredSize(new Dimension(-1, 30));
        cipherTextClearButton.addActionListener(this);
        cipherTextClearButton.setActionCommand("CipherTextClearAction");

        // Create swap button
        cipherTextSwapButton = new JButton("Swap");
        cipherTextSwapButton.setBackground(Color.LIGHT_GRAY);
        cipherTextSwapButton.setPreferredSize(new Dimension(-1, 30));
        cipherTextSwapButton.addActionListener(this);
        cipherTextSwapButton.setActionCommand("CipherTextSwapAction");

        // Create panel for buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.add(cipherTextClearButton);
        buttonsPanel.add(cipherTextSwapButton);

        // Create text panel
        cipherTextPanel = new JPanel(new BorderLayout());
        cipherTextPanel.setBackground(Color.LIGHT_GRAY);
        cipherTextPanel.setBorder(BorderFactory.createTitledBorder("Cipher text"));
        cipherTextPanel.add(cipherTextScrollPane, BorderLayout.CENTER);
        cipherTextPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add panel to main one
        centerMainPanel.add(cipherTextPanel);
    }


    private void createCipherOptionsPanel()
    {
        // Create cipher settings panel
        cipherSettingsPanel = new CipherSettingsPanel(this);

        // Create options panel
        cipherOptionsPanel = new JPanel(new BorderLayout());
        cipherOptionsPanel.setBackground(new Color(0,0,0,0));
        cipherOptionsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
        cipherOptionsPanel.add(cipherSettingsPanel);

        // Add panel to main one
        centerMainPanel.add(cipherOptionsPanel);
    }

    private void createOperationsPanel()
    {
        // Create encode button
        encodeButton = new JButton("Encode");
        encodeButton.setBackground(Color.GREEN);
        encodeButton.setFont(Resources.ENCODE_DECODE_BUTTON_FONT);
        encodeButton.setPreferredSize(new Dimension(100,50));
        encodeButton.addActionListener(this);
        encodeButton.setActionCommand("EncodeAction");

        // Create decode button
        decodeButton = new JButton("Decode");
        decodeButton.setBackground(Color.RED);
        decodeButton.setFont(Resources.ENCODE_DECODE_BUTTON_FONT);
        decodeButton.setPreferredSize(new Dimension(100,50));
        decodeButton.addActionListener(this);
        decodeButton.setActionCommand("DecodeAction");

        // Create test button
        testButton = new JButton("Test");
        testButton.setBackground(Color.LIGHT_GRAY);
        testButton.setPreferredSize(new Dimension(100,25));

        // Create encode button
        statisticButton = new JButton("Statistic");
        statisticButton.setBackground(Color.LIGHT_GRAY);
        statisticButton.setPreferredSize(new Dimension(100,25));
        statisticButton.addActionListener(this);
        statisticButton.setActionCommand("StatisticAction");
        
        // Create panel
        operationsPanel = new JPanel();
        operationsPanel.setBackground(new Color(0,0,0,0));

        operationsPanel.add(testButton);
        operationsPanel.add(encodeButton);
        operationsPanel.add(decodeButton);
        operationsPanel.add(statisticButton);

        // Add panel to main one
        bottomMainPanel.add(operationsPanel);

        // Create statistic window
        statisticWindow = new StatisticWindow(this);
    }


    private void createMenuBar()
    {
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);

        window.setJMenuBar(menuBar);

        menuFile = new JMenu( "File");
        menuFile.setForeground(Color.LIGHT_GRAY);

        menuHelp = new JMenu("Help");
        menuHelp.setForeground(Color.LIGHT_GRAY);


        menuBar.add(menuFile);
        menuBar.add(menuHelp);
    }
    
    
    private void createFileMenu()
    {
        menuFileItemNew = new JMenuItem("New");
        menuFileItemNew.addActionListener(this);
        menuFileItemNew.setActionCommand("FileNew");
        menuFile.add(menuFileItemNew);

        menuFileItemOpen = new JMenuItem("Open");
        menuFileItemOpen.addActionListener(this);
        menuFileItemOpen.setActionCommand("FileOpen");
        menuFile.add(menuFileItemOpen);

        menuFileItemSave = new JMenuItem("Save");
        menuFileItemSave.addActionListener(this);
        menuFileItemSave.setActionCommand("FileSave");
        menuFile.add(menuFileItemSave);

        menuFileItemSaveAs = new JMenuItem("Save as");
        menuFileItemSaveAs.addActionListener(this);
        menuFileItemSaveAs.setActionCommand("FileSaveAs");
        menuFile.add(menuFileItemSaveAs);
    }
    
    
    private void createHelpMenu()
    {
        menuHelpItemAbout = new JMenuItem("About");
        menuHelpItemAbout.addActionListener(this);
        menuHelpItemAbout.setActionCommand("HelpAbout");
        menuHelp.add(menuHelpItemAbout);

        menuHelpItemExit = new JMenuItem("Exit");
        menuHelpItemExit.addActionListener(this);
        menuHelpItemExit.setActionCommand("HelpExit");
        menuHelp.add(menuHelpItemExit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            // File
            case "FileNew":
                fileFunctions.newFile();
                break;
            case "FileOpen":
                fileFunctions.open();
                break;
            case "FileSave":
                fileFunctions.save();
                break;
            case "FileSaveAs":
                fileFunctions.saveAs();
                break;

            // Help
            case "HelpAbout":
                aboutWindow.showWindow();
                break;
            case "HelpExit":
                System.exit(0);
                break;

            // Options
            case "EncodeAction":
                cipherSettingsPanel.encode();
                break;
            case "DecodeAction":
                cipherSettingsPanel.decode();
                break;
            case "StatisticAction":
                statisticWindow.showWindow();
                break;
            case "TestAction":
                cipherSettingsPanel.test();
                break;

            case "InputTextClearAction":
                inputTextArea.setText("");
                break;
            case "InputTextFillAction":
                inputTextArea.setText(Resources.LOREM_IPSUM);
                break;

            case "CipherTextClearAction":
                cipherTextArea.setText("");
                break;
            case "CipherTextSwapAction":
                inputTextArea.setText(cipherTextArea.getText());
                cipherTextArea.setText("");
                break;

        }
    }
    
}
