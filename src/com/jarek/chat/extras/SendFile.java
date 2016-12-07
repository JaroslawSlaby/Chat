package com.jarek.chat.extras;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
    private OutputStream  outputStream;
    private InputStream inputStream;
    private File newFile;

    private static int MAX_FILE_SIZE = 20*1024;
    private int bytesRead;
    private int curTotal;

    public void sendFile(File file, Socket socket) throws IOException {
        this.file = file;
        fileByteArray = new byte[(int)file.length()];
        fileInputStream = new FileInputStream(this.file);
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        bufferedInputStream.read(fileByteArray,0,fileByteArray.length);
        outputStream = socket.getOutputStream(); // tu sie wykrzacza socket == null
        outputStream.write(fileByteArray,0,fileByteArray.length);
        outputStream.flush();
    }
    public void receiveFile(Socket socket) throws IOException{
        fileByteArray = new byte[MAX_FILE_SIZE];
        inputStream = socket.getInputStream();
        newFile = new File("/jarek.txt");
        newFile.createNewFile();
        fileOutputStream = new FileOutputStream(newFile);
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bytesRead = inputStream.read(fileByteArray, 0, fileByteArray.length);
        curTotal = bytesRead;

        do {
            bytesRead = inputStream.read(fileByteArray, curTotal, (fileByteArray.length - curTotal));
            if(bytesRead >= 0)
                curTotal += bytesRead;
        } while (bytesRead > -1);

        bufferedOutputStream.write(fileByteArray, 0, curTotal);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
}
