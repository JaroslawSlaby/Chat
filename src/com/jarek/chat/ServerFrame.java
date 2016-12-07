package com.jarek.chat;

import com.jarek.chat.extras.Codes;
import com.jarek.chat.extras.SendFile;
import com.jarek.chat.extras.SendFileGUI;
import com.jarek.chat.gui.Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by Jarek on 09.11.16.
 */
public class ServerFrame extends Gui implements ActionListener {

    private static ServerSocket ss;
    private static Socket s;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private SendFile sendFile = new SendFile();

    public ServerFrame(String title) {
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
                    ServerFrame.dataOutputStream.writeUTF(msgOut);
                    msgText.setText("");
                }

            } catch (Exception i) {
                JOptionPane.showMessageDialog(this, "Send message error! Check your " +
                        "connection or if other " +
                        "is closed!", "Send error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listen() {
        try {
            String msgIn = "";
            ss = new ServerSocket(1220); //number of server starting port
            s = ss.accept(); //accepting connections to server
            dataInputStream = new DataInputStream(s.getInputStream());
            dataOutputStream = new DataOutputStream(s.getOutputStream());
            while(!Objects.equals(msgIn, "exit")) {
                msgIn = dataInputStream.readUTF();
                System.out.println(msgIn);
                msgArea.setText(msgArea.getText() + "\n" + s.toString() + ": "  + msgIn);
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

    public void setFile(File file) throws IOException {
        this.file = file;
        msgArea.append("\n Loaded file: " + this.file.getName() + "\n");
        ServerFrame.dataOutputStream.writeUTF(codes.sendFileNotification());
      //  sendFile.sendFile(this.file, fileSocket);
    }

}
