package sample;

import java.io.*;
import java.net.*;

/**
 * Created by Saif Niaz on 2016-03-23.
 */
public class FileServer {

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {

        try {
            serverSocket = new ServerSocket(1212);
            System.out.println("Server started.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection : " + clientSocket);

                Thread t = new Thread(new ClientConnectionHandler(clientSocket));
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}


