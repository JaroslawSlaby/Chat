package com.jarek.chat;

import com.jarek.chat.gui.Gui;

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
public class ServerFrame extends Gui implements ActionListener{

    private static ServerSocket ss;
    private static Socket s;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public ServerFrame(String title) {
        super(title);
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

            SendFileFrame sendFile = new SendFileFrame(this);

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
        msgArea.append("Loaded file: " + this.file.getName() + "\n");
    }
}
