package sample;

import java.io.*;
import java.net.*;
//import java.

/**
 * Created by Saif Niaz on 2016-03-23.
 */
public class FileServer {

    private ServerSocket serverSocket = null;

    public FileServer(int port){
        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void handleRequests() throws IOException {
        System.out.println("Simple Http Server v1.0: Ready to handle incoming requests.");

        // repeatedly handle incoming requests
        while(true) {
            Socket socket = serverSocket.accept();
            Thread handlerThread = new Thread(new ClientConnectionHandler(socket));
            handlerThread.start();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
        try {
            FileServer server = new FileServer(port);
            server.handleRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


