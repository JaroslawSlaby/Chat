package com.jarek.chat;

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
    private JTextField log = new JTextField();
    private JPanel main;
    private File file;
    private static JFrame frame = new JFrame("Choose file to send!");

    public SendFileFrame(){

        super(new BorderLayout());
        log = new JTextField();
        log.setEditable(false);
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
        add(log,BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == selectFile) {

            int returnVal = fileChooser.showOpenDialog(SendFileFrame.this);

            if(returnVal == JFileChooser.APPROVE_OPTION) {

                file = fileChooser.getSelectedFile();
                log.setText("Name: " + file.getName() + "\n" + "Path: " + file.getPath());

            }
            else
                log.setText("Cancelled by user.1");

        }

        else if(e.getSource() == cancel){
            frame.dispose();
        }
            // close

    }

    public static void createFrame() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SendFileFrame());
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    UIManager.put("swing.boldMetal", Boolean.FALSE);
                    createFrame();
                }
            });

    }







}