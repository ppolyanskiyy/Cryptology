package LNU.pmi;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI implements ActionListener
{
    JFrame window;

    // Input text
    JPanel inputTextPanel;
    JTextArea inputTextArea;
    JScrollPane inputTextScrollPane;

    // Cipher text
    JPanel cipherTextPanel;
    JTextArea cipherTextArea;
    JScrollPane cipherTextScrollPane;

    // Cipher settings panel
    JPanel cipherSettingsPanel;

    // Operation panel
    JPanel operationsPanel;
    
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
        createCipherSettingsPanel();
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
        window = new JFrame("Crypt");
        window.setSize(1200, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        aboutWindow = new AboutWindow();
        aboutWindow.setLocationRelativeTo(this.window);
    }


    private void createInputTextPanel()
    {
        // Create text panel
        inputTextPanel = new JPanel();
        inputTextPanel.setBorder(BorderFactory.createTitledBorder("Input text"));

        // Create text area
        inputTextArea = new JTextArea();
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);

        inputTextScrollPane = new JScrollPane(inputTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        inputTextScrollPane.setBorder(BorderFactory.createEmptyBorder());

        inputTextPanel.add(inputTextScrollPane);

        window.add(inputTextPanel);
    }

    
    private void createCipherTextPanel()
    {
        // Create text panel
        cipherTextPanel = new JPanel();
        cipherTextPanel.setBorder(BorderFactory.createTitledBorder("Cipher text"));

        // Create text area
        cipherTextArea = new JTextArea();

        cipherTextScrollPane = new JScrollPane(cipherTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        cipherTextScrollPane.setBorder(BorderFactory.createEmptyBorder());

        cipherTextPanel.add(cipherTextScrollPane);

        window.add(cipherTextPanel);
    }


    private void createCipherSettingsPanel()
    {
        cipherSettingsPanel = new JPanel();
        cipherSettingsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));

        window.add(cipherSettingsPanel);

    }

    private void createOperationsPanel()
    {
        operationsPanel = new JPanel();
        operationsPanel.setBorder(BorderFactory.createTitledBorder("Operations"));

        window.add(cipherSettingsPanel);

    }


    private void createMenuBar()
    {
        menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuHelp = new JMenu("Help");

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

        }
    }
    
}
