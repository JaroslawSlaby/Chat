package com.jarek.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by Jarek on 09.11.16.
 */
public class ServerFrame extends JFrame implements ActionListener{
    private JPanel panelMain;
    private JTextField msgText = new JTextField();
    private JPanel btnPanel = new JPanel();
    private JButton msgSend = new JButton("Send message!");
    private JButton clearArea = new JButton("Clear message area!");
    private JButton fileSend = new JButton("Send file!");
    private JTextArea msgArea = new JTextArea(10,32);
    private File file;
    private SendFileFrame sendFile;

    private static ServerSocket ss;
    private static Socket s;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    private void createGUI(String title) {

        msgArea.setEditable(false);
        msgArea.setWrapStyleWord(true);
        msgArea.setLineWrap(true);

        msgSend.addActionListener(this);
        clearArea.addActionListener(this);
        fileSend.addActionListener(this);

        Container container = getContentPane();
        container.add(new JScrollPane(msgArea), BorderLayout.CENTER);
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setPreferredSize(new Dimension(200,200));
        btnPanel.add(msgSend, BorderLayout.NORTH);
        btnPanel.add(clearArea, BorderLayout.SOUTH);
        btnPanel.add(fileSend, BorderLayout.WEST);
        container.add(btnPanel, BorderLayout.EAST);
        container.add(msgText, BorderLayout.SOUTH);
        setTitle(title);
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(container, "Exit?", "Close CHAT",
                        JOptionPane.OK_OPTION);
                if (choice == JOptionPane.OK_OPTION)
                    System.exit(0); // do dopracowania
            }
        });
        setVisible(true);
    }

    public ServerFrame(String title) {
        super(title);
        createGUI(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == msgSend) {

            try {

                String msgOut;
                msgOut = msgText.getText().trim();
                if(!Objects.equals(msgOut, "")) {
                    dataOutputStream.writeUTF(msgOut);
                    msgText.setText("");
                }

            } catch (Exception i) {
                JOptionPane.showMessageDialog(this, "Send message error! Check your " +
                        "connection or if server is closed!", "Send error!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == clearArea) {

            msgArea.setText("");

        } else if (e.getSource() == fileSend) {

            sendFile = new SendFileFrame(this);

        }
    }

    private void listen() {

        String msgIn = "";

        try {

            ss = new ServerSocket(1220); //number of server starting port
            s = ss.accept(); //accepting connections to server

            dataInputStream = new DataInputStream(s.getInputStream());
            dataOutputStream = new DataOutputStream(s.getOutputStream());

            while(msgIn != "exit") {

                msgIn = dataInputStream.readUTF();
                System.out.println(msgIn);
                if(msgArea.getText()!=null)
                msgArea.setText(msgArea.getText() + "\n" + msgIn);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Receive message error! Check your " +
                "connection!", "Receive error!", JOptionPane.ERROR_MESSAGE);
            }
    }

    public static void main(String[] args) {

            ServerFrame serverFrame = new ServerFrame("Server");
            serverFrame.listen();

    }

    public void setFile(File file) {
        this.file = file;
    }
}
