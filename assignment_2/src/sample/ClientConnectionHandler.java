package sample;

import java.io.*;
import java.net.*;


/**
 * Created by Saif Niaz on 2016-03-23.
 */


public class ClientConnectionHandler implements Runnable {

    private Socket clientSocket;
    private SocketAddress address;
    private ObjectInputStream input;
    private BufferedReader in = null;
    private static PrintStream os;
    private File file = new File("Server_Folder/");

    public ClientConnectionHandler(Socket socket) {
        this.clientSocket = socket;
        address = clientSocket.getLocalSocketAddress();

    }

    @Override
    public void run() {

        try {
            returnFIle();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            input = new ObjectInputStream(clientSocket.getInputStream());
            String index;
            while((index = in.readLine()) != null) {
                switch (index) {
                    case "upload":
                        uploadFile();
                        break;
                    case "download":
                        String outGoingFileName = in.readLine();
                        downloadFile(outGoingFileName);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnFIle() throws IOException{
        os = new PrintStream( clientSocket.getOutputStream());
        os.println(this.file.getName());
    }

    public void uploadFile() {
        try {
            int bytesR;

            DataInputStream clientData = new DataInputStream(clientSocket.getInputStream());

            String fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(("Server_Folder/" + fileName));
            long size = clientData.readLong();
            byte[] buffer = new byte[8*1024];
            while (size > 0 && (bytesR = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) > -1) {
                output.write(buffer, 0, bytesR);
                size -= bytesR;
            }
            output.close();


            System.out.println("Uploaded to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(String fileName) {
        try {
            //handle file read
            File file = new File(fileName);
            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(bytes, 0, bytes.length);

            //handle file send over socket
            OutputStream os = clientSocket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(file.getName());
            dos.writeLong(bytes.length);
            dos.write(bytes, 0, bytes.length);
            dos.flush();
            System.out.println("Downloaded by Server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
