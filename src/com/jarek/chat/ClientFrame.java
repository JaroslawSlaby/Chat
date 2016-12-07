package com.jarek.chat;

import com.jarek.chat.extras.SendFileGUI;
import com.jarek.chat.gui.Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by Jarek on 09.11.16.
 */
public class ClientFrame extends Gui implements ActionListener {

    static Socket s;
    static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;


    public ClientFrame(String title) {
        super(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if(e.getSource() == fileSend) {
            SendFileGUI sendFile = new SendFileGUI(this);
        }
        else if(e.getSource() == msgSend) {
            try {
                String msgOut;
                msgOut = msgText.getText().trim();
                if(!Objects.equals(msgOut, "")) {
                        ClientFrame.dataOutputStream.writeUTF(msgOut);
                    msgText.setText("");
                }

            } catch (Exception i) {
                JOptionPane.showMessageDialog(this, "Send message error! Check your " +
                        "connection or if other " +
                        "is closed!", "Send error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listen()  {
        try {
            String msgIn = "";
            s = new Socket("127.0.0.1" , 1220);
            dataInputStream = new DataInputStream(s.getInputStream());
            dataOutputStream = new DataOutputStream(s.getOutputStream());
            while(!Objects.equals(msgIn, "exit")) {
                msgIn = dataInputStream.readUTF();
                    if(Objects.equals(msgIn, codes.sendFileNotification())) {
                        // sendFile.receiveFile(s);
                        msgIn = "Server's sending file!";
                    }
                msgArea.setText(msgArea.getText() + "\nServer: " + msgIn);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Receive message error! Check your " +
                    "connection!", "Receive error!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {

            ClientFrame clientFrame = new ClientFrame("Client");
            clientFrame.listen();

    }

    public void setFile(File file) {

        this.file = file;
        msgArea.append("\nLoaded file: " + this.file.getName() + "\n");
    }
}
