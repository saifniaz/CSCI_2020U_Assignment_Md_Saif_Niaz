package sample;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Saif Niaz on 2016-03-25.
 */
public class FileClient extends Application {



    private static Socket sock;
    private static String fileName;
    private static BufferedReader stdin;
    private static PrintStream os;

    private File file = new File("Shared_Folder/");
    private File file2 = new File("Server_Folder/");
    private BorderPane layout;
    private ListView<String> table1, table2;

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("File Sharer v1.0");
        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(0, 0, 0, 0));
        editArea.setVgap(00);
        editArea.setHgap(00);

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = table2.getSelectionModel().getSelectedItem();
                String downName = "Server_Folder/" + name;
                os.println(2);
                receiveFile(downName);

            }
        });
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = table1.getSelectionModel().getSelectedItem();
                String uploadName = "Shared_Folder/" + name;
                os.println(1);
                sendFile(uploadName);

            }
        });
        editArea.add(downloadButton, 0, 0);
        editArea.add(uploadButton, 1, 0);

        table1 = new ListView<>();
        table1.getItems().addAll(file.list());
        table1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        table2 = new ListView<>();
        table2.getItems().addAll(file2.list());
        table2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        layout = new BorderPane();
        layout.setTop(editArea);
        layout.setLeft(table1);
        layout.setRight(table2);


        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {

        try {
            sock = new Socket("localhost", 1212);
            stdin = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            System.err.println("Cannot connect to the server, try again later.");
            System.exit(1);
        }
        os = new PrintStream(sock.getOutputStream());

        launch(args);

        sock.close();
    }

    public static void sendFile(String fileName) {
        try {
            File myFile = new File(fileName);
            byte[] bytes = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);


            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(bytes, 0, bytes.length);

            OutputStream os = sock.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(bytes.length);
            dos.write(bytes, 0, bytes.length);
            dos.flush();
            System.out.println("File "+fileName+" sent to Server.");
        } catch (Exception e) {
            System.err.println("File does not exist!");
            System.out.println(fileName);
        }
    }

    public static void receiveFile(String fileName) {
        try {
            int bytesRead;
            InputStream in = sock.getInputStream();

            DataInputStream clientData = new DataInputStream(in);

            fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(("received_from_server_" + fileName));
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            output.close();
            in.close();

            System.out.println("File "+fileName+" received from Server.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
