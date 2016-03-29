package sample;

import java.io.*;
import java.net.*;


/**
 * Created by Saif Niaz on 2016-03-23.
 */


public class ClientConnectionHandler implements Runnable {

    private Socket clientSocket;
    private InetAddress address;
    private ObjectInputStream input;
    private int Port = 1212;
    private BufferedReader in = null;
    private static PrintStream os;
    private File file = new File("Server_Folder/");

    public ClientConnectionHandler(Socket socket) {
        this.clientSocket = socket;
        address = clientSocket.getInetAddress();

    }

    @Override
    public void run() {

        try {
            //returnFIle();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            input = new ObjectInputStream(clientSocket.getInputStream());
            String index;
            while((index = in.readLine()) != null) {

                switch (index) {
                    case "upload":
                        //if(clientSocket.isClosed()){
                           // clientSocket.connect(new InetSocketAddress(address,Port));
                            uploadFile();
                            //clientSocket.close();
                        //}else{
                            //clientSocket.connect(new InetSocketAddress(address,Port));
                            //uploadFile();
                            //clientSocket.close();
                        //}
                       //
                        break;
                    case "download":
                        String outGoingFileName = in.readLine();
                        //if(clientSocket.isClosed()){
                            //clientSocket.connect(new InetSocketAddress(address,Port));
                            sendFile(outGoingFileName);
                           // clientSocket.close();
                        //}else{
                            //clientSocket.connect(new InetSocketAddress(address,Port));
                            //sendFile(outGoingFileName);
                            //clientSocket.close();
                       // }
                        break;
                    default:
                        System.out.println("Incorrect command received.");
                        break;
                }
                //
            }
            //in.close();
            //input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void returnFIle() throws IOException{
        os = new PrintStream( clientSocket.getOutputStream());
        os.println(this.file.getName());
        os.close();
    }

    public void uploadFile() {
        try {
            int bytesRead;

            DataInputStream clientData = new DataInputStream(clientSocket.getInputStream());

            String fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(("Server_Folder/" + fileName));
            long size = clientData.readLong();
            byte[] buffer = new byte[8*1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            output.close();
            //clientData.close();

            System.out.println("File "+fileName+" received from client.");
        } catch (IOException ex) {
            System.err.println("Client error. Connection closed.");
        }
    }

    public void sendFile(String fileName) {
        try {
            //handle file read
            File file = new File(fileName);
            byte[] mybytearray = new byte[(int) file.length()];

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //bis.read(mybytearray, 0, mybytearray.length);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            //handle file send over socket
            OutputStream os = clientSocket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(file.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            System.out.println("File "+fileName+" sent to client.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
