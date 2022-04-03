package LNU.pmi.Helpers;

import LNU.pmi.GUI;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class FileFunctions
{
    GUI gui;
    String fileName;
    String fileAddress;

    public FileFunctions(GUI gui)
    {
        this.gui = gui;
    }

    public void newFile()
    {
        gui.inputTextArea.setText("");
        gui.window.setTitle("*New");
        fileName = null;
        fileAddress = null;
    }

    public void open()
    {
        FileDialog fd = new FileDialog(gui.window, "Open", FileDialog.LOAD);
        fd.setVisible(true);

        if(fd.getFile() != null)
        {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();

            gui.window.setTitle(fileName);
        }

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));

            gui.inputTextArea.setText("");

            String line = null;

            while((line = br.readLine()) != null)
            {
                gui.inputTextArea.append(line + "\n");
            }

            br.close();
        }
        catch (Exception e)
        {
            System.out.println("[ERROR] File is not opened");
        }
    }

    public void save()
    {
        if(fileName == null)
        {
            saveAs();
        }
        else
        {
            try
            {
                FileWriter fw = new FileWriter(fileAddress + fileName);
                fw.write(gui.inputTextArea.getText());
                gui.window.setTitle(fileName);
                fw.close();
            }
            catch (Exception e)
            {
                System.out.println("[ERROR] Can't save file");
            }
        }

    }

    public void saveAs()
    {
        FileDialog fd = new FileDialog(gui.window, "Save as", FileDialog.SAVE);
        fd.setVisible(true);

        if(fd.getFile() != null)
        {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();

            gui.window.setTitle(fileName);
        }

        try
        {
            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(gui.inputTextArea.getText());
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println("[ERROR] Can't save file");
        }
    }

}
