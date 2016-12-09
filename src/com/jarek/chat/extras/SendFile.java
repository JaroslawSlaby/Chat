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
    private File file;
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private BufferedInputStream bufferedInputStream;
    private BufferedOutputStream bufferedOutputStream;
    private OutputStream outputStream;
    private InputStream inputStream;
    private File newFile;

    private static int MAX_FILE_SIZE = 20 * 1024;
    private int bytesRead;
    private int curTotal;

    public void sendFile(File file) throws IOException {

    }

    public void receiveFile(Socket socket) throws IOException {

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        try {
            fileByteArray = (byte []) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String fileName = JOptionPane.showInputDialog("Name of file: ");
        newFile = new File(fileName);
        Files.write(newFile.toPath(), fileByteArray);

    }
}