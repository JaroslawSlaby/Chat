package com.jarek.chat;

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
public class ClientFrame extends Gui implements ActionListener{



    static Socket s;
    static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;


    public ClientFrame(String title) {
        super(title);
    }


    private void listen()  {

        try {

            String msgIn = "";
            s = new Socket("127.0.0.1" , 1220);

            dataInputStream = new DataInputStream(s.getInputStream());
            dataOutputStream = new DataOutputStream(s.getOutputStream());

            while(msgIn!="exit") {

                msgIn = dataInputStream.readUTF();
                System.out.println(msgIn);
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
        msgArea.append("Loaded file: " + this.file.getName() + "\n");
    }
}
