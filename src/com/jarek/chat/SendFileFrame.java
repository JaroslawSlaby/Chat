package com.jarek.chat;

import javafx.scene.Parent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by jarek on 12/2/16.
 */
public class SendFileFrame extends JPanel implements ActionListener {


    private JPanel panelMain;
    private JFileChooser fileChooser;
    private JButton selectFile = new JButton("Select file to send!");
    private JButton sendFile = new JButton("Send choosed file!");
    private JButton cancel = new JButton("Cancel!");
    private JTextField fileName;
    private JTextField filePath;
    private JPanel main;
    private File file;
    private static JFrame frame = new JFrame("Choose file to send!");
    private ClientFrame clientFrame;

    public SendFileFrame(ClientFrame client){

        super(new BorderLayout());
        clientFrame = client;
        fileName = new JTextField();
        fileName.setEditable(false);
        filePath = new JTextField();
        filePath.setEditable(false);
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selectFile.addActionListener(this);
        sendFile.addActionListener(this);
        cancel.addActionListener(this);
        panelMain = new JPanel();
        panelMain.add(selectFile);
        panelMain.add(sendFile);
        panelMain.add(cancel);
        add(panelMain, BorderLayout.PAGE_START);
        add(fileName,BorderLayout.CENTER);
        add(filePath, BorderLayout.SOUTH);
     }

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == selectFile) {

            int returnVal = fileChooser.showOpenDialog(SendFileFrame.this);

            if(returnVal == JFileChooser.APPROVE_OPTION) {

                file = fileChooser.getSelectedFile();
                fileName.setText("Name: " + file.getName());
                filePath.setText("Path: " + file.getPath());

            }
            else
                fileName.setText("Cancelled by user!");

        }
        else if(e.getSource() == sendFile)
        {
            clientFrame.setFile(file);
        }

        else if(e.getSource() == cancel){
            this.setVisible(false);
        }
            // close

    }

    public static void createFrame() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     //   ClientFrame cl = new ClientFrame();
      //  frame.add(new SendFileFrame(cl));
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {
             //   UIManager.put("swing.boldMetal", Boolean.FALSE);
                createFrame();
            });

    }

    public File getFile() {
        return file;
    }
}