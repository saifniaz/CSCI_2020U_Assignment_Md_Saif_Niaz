package sample;

import java.io.*;
import java.net.*;

/**
 * Created by Saif Niaz on 2016-03-23.
 */
public class FileServer {

    private static ServerSocket serverSocket = null;
    private static File file = new File("Shared_Folder/temp");
    private static FileInputStream fin = null;
    private static BufferedInputStream bis = null;
    private static OutputStream out = null;


    /*public FileServer(int port){
        try{

            start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void start() throws IOException {

            //Thread handlerThread = new Thread(new ClientConnectionHandler(socket));
            //handlerThread.start();

           /* in = socket.getInputStream();
            out= new FileOutputStream("Shared_Folder/temp.txt");

            byte[] bytes = new byte[8*1024];

            int count;
            while((count = in.read(bytes)) >0){
                out.write(bytes, 0, count);
            }

            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println("Received File");



            out.close();
            in.close();
            socket.close();
            serverSocket.close();
        }
    }*/

    /*public static void main(String[] args) throws IOException {
        int port = 2222;
        //FileServer fileServer = new FileServer(port);

        serverSocket = new ServerSocket(port);

        while (true){
            Socket socket = serverSocket.accept();
            ObjectOutputStream oss new ObjectOutputStream(socket.getOutputStream());

        }





    }*/
}


