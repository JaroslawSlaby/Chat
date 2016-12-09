package com.jarek.chat.extras;

import com.jarek.chat.ServerFrame;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Created by jarek on 12/7/16.
 */
public class SendFile {

    private byte[] fileByteArray;
    private File newFile;
    private JFileChooser chooser = new JFileChooser();

    public void sendFile(File file) throws IOException {

    }

    public Boolean receiveFile(Socket socket) throws IOException {

        int choose = JOptionPane.showConfirmDialog(null, "Incoming file! Recieve?", null, JOptionPane.YES_NO_OPTION);

        if (choose == JOptionPane.YES_OPTION) {

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            try {
                fileByteArray = (byte[]) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            chooser.setDialogTitle("Save a file!");
            int result = chooser.showSaveDialog(chooser);
            if (result == chooser.APPROVE_OPTION) {
                newFile = chooser.getSelectedFile();
                Files.write(newFile.toPath(), fileByteArray);
            }
            return true;

        } else return false;

    }
}