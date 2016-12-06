package com.jarek.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.Semaphore;

/**
 * Created by jarek on 12/2/16.
 */
public class SendFileFrame extends JPanel implements ActionListener {


    private JPanel panelMain;
    private JFileChooser fileChooser;
    private JButton selectFile = new JButton("Select file to send!");
    private JButton sendFile = new JButton("Send choosed file!");
    private static JFrame frame = new JFrame("Choose file to send!");
    private JButton cancel = new JButton("Cancel!");
    private JTextField fileName;
    private JTextField filePath;
    private JPanel main;
    public File file = null;

    private ClientFrame clientFrame;
    private ServerFrame serverFrame;
    private int mode;


    private void showGUI() {

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
     }

     public SendFileFrame(ClientFrame client) {
        super(new BorderLayout());
        showGUI();
        this.clientFrame = client;
        mode = 1;

     }

     public SendFileFrame(ServerFrame server) {
         super(new BorderLayout());
         showGUI();
         this.serverFrame = server;
         mode = 2;
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
            if(mode == 1)
                clientFrame.setFile(this.file);
            else if(mode == 2)
                serverFrame.setFile(this.file);
            frame.dispose();
        }

        else if(e.getSource() == cancel){
            frame.dispose();
        }

    }

}