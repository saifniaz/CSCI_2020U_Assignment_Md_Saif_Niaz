package sample;

import java.io.*;
import java.net.*;

/**
 * Created by Saif Niaz on 2016-03-23.
 */
public class FileServer {

    private static ServerSocket serverSocket = null;

    public FileServer(int port){
        try{
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server Listening");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleRequest(){
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection : " + clientSocket);

                Thread t = new Thread(new ClientConnectionHandler(clientSocket));
                t.start();
                //clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileServer fileServer = new FileServer(1212);
        fileServer.handleRequest();
    }


}


