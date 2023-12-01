package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class SocketSender {
    private final static String QUEUE_NAME = "text";

    public static void main(String[] args)  {

        try {
            ServerSocket serverSocket  = new ServerSocket(8000);
            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected");

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(),true);

            writer.println("this is a message");

            serverSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
