package com.jarek.chat.gui;

import com.jarek.chat.ClientFrame;
import com.jarek.chat.SendFileFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Objects;

/**
 * Created by jarek on 12/6/16.
 */
public class Gui extends JFrame implements ActionListener{

    public JTextArea msgArea = new JTextArea(10, 32);
    public JTextField msgText = new JTextField();
    private JPanel btnPanel = new JPanel();
    public JButton msgSend = new JButton("Send message!");
    public JButton clearArea = new JButton("Clear message area!");
    public JButton fileSend = new JButton("Send file!");
    public File file;

    public Gui(String title){
    msgArea.setEditable(false);
    msgArea.setWrapStyleWord(true);
    msgArea.setLineWrap(true);
    msgSend.addActionListener(this);
    clearArea.addActionListener(this);
    fileSend.addActionListener(this);

    Container container = getContentPane();
        container.add(new

    JScrollPane(msgArea),BorderLayout.CENTER);
        btnPanel.setLayout(new

    FlowLayout());
        btnPanel.setPreferredSize(new

    Dimension(200,200));
        btnPanel.add(msgSend,BorderLayout.NORTH);
        btnPanel.add(clearArea,BorderLayout.SOUTH);
        btnPanel.add(fileSend,BorderLayout.WEST);
        container.add(btnPanel,BorderLayout.EAST);
        container.add(msgText,BorderLayout.SOUTH);

    setTitle(title);

    // setLocationRelativeTo(null);
    addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing (WindowEvent e){
            int choice = JOptionPane.showConfirmDialog(container, "Exit?", "Close CHAT",
                    JOptionPane.OK_OPTION);
            if (choice == JOptionPane.OK_OPTION)
                System.exit(0); // do dopracowania
        }
    });

    pack();
    setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == msgSend) {

            try {

                String msgOut;
                msgOut = msgText.getText().trim();
                if(!Objects.equals(msgOut, "")) {
                    ClientFrame.dataOutputStream.writeUTF(msgOut);
                    msgText.setText("");
                }

            } catch (Exception i) {
                JOptionPane.showMessageDialog(this, "Send message error! Check your " +
                        "connection or if server is closed!", "Send error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == clearArea) {

            msgArea.setText("");

        }
        else if(e.getSource() == fileSend) {

         //   SendFileFrame sendFile = new SendFileFrame(this);

        }

    }

}