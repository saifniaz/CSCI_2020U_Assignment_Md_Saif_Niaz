package sample;


import java.io.*;
import java.net.Socket;

/**
 * Created by Saif Niaz on 2016-03-25.
 */
public class FileClient {
    private String hostname = "localhost";
    private int Port = 2222;
    private static int bytesRead;
    private static int current = 0;
    private static FileOutputStream fos = null;
    private static BufferedOutputStream bos = null;

    public FileClient(){

    }

    public static void main(String[] args) throws IOException{
        try {
            Socket sock = new Socket("localhost", 2222);
            System.out.println("Connecting...");

            // receive file*
            byte [] mybytearray  = new byte [8*1024];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream("Shared_Folder/temp.tx");
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + "Shared_Folder/temp.txt"
                    + " downloaded (" + current + " bytes read)");
            //if (sock != null) sock.close();
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();

        }

    }


   /* public void run() throws IOException{
        Socket socket = new Socket(this.hostname, this.Port);
        File file = new File("Shared_Folder/temp1.txt");
        long lenght = file.length();
        byte[] bytes = new byte[8*1024];
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        InputStreamReader ir = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(ir);
        String read = br.readLine();
        System.out.println(read);
        /*String[] temp = read.split(" ");
        int i = 0;
        while(i < temp.length) {
            System.out.println(temp[i]);
            i++;
        }

        in.close();
        socket.close();


    }*/



}
