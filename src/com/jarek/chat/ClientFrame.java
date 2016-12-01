package com.jarek.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Jarek on 09.11.16.
 */
public class ClientFrame extends JFrame{
    private JPanel panelMain;
    private JTextArea msgArea = new JTextArea(10, 32);
    private JTextField msgText = new JTextField();
    private JPanel btnPanel = new JPanel();
    private JButton msgSend = new JButton("Send message!");
    private JButton clearArea = new JButton("Clear message area!");

    static Socket s;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;


    public ClientFrame() {
        msgSend.addActionListener(e -> {

            try {
                String msgOut;
                msgOut = msgText.getText().trim();
                dataOutputStream.writeUTF(msgOut);
                msgText.setText("");
            } catch (Exception i) {
                JOptionPane.showMessageDialog(null, "Send message error! Check your " +
                        "connection", "Send error!", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearArea.addActionListener(e -> msgArea.setText(""));

        msgArea.setEditable(false);
        msgArea.setWrapStyleWord(true);
        msgArea.setLineWrap(true);

        Container container = getContentPane();
        container.add(new JScrollPane(msgArea), BorderLayout.CENTER);
        btnPanel.add(msgSend, BorderLayout.NORTH);
        btnPanel.add(clearArea, BorderLayout.SOUTH);
        container.add(btnPanel, BorderLayout.EAST);
        container.add(msgText, BorderLayout.SOUTH);
        setTitle("Client");
        pack();
        setVisible(true);
//hello world - git test
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(container, "Exit?", "Close CHAT",
                        JOptionPane.OK_OPTION);
                if (choice == JOptionPane.OK_OPTION)
                    System.exit(0); // do dopracowania
            }
        });

    }
    private void listen()  {

        String msgIn;

        try {

            s = new Socket("127.0.0.1" , 1220);

            dataInputStream = new DataInputStream(s.getInputStream());
            dataOutputStream = new DataOutputStream(s.getOutputStream());

            while(true) {

                msgIn = dataInputStream.readUTF();
                System.out.println(msgIn);
                msgArea.setText(msgArea.getText() + "\nServer: " + msgIn);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Receive message error! Check your " +
                "connection!", "Receive error!", JOptionPane.ERROR_MESSAGE);
            }

    }

    public static void main(String[] args) {

                ClientFrame clientFrame = new ClientFrame();
                clientFrame.listen();
    }
}
