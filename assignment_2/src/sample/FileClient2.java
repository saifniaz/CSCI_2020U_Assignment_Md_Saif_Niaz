package sample;


import javafx.application.Application;
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
import java.util.Scanner;

/**
 * Created by Saif Niaz on 2016-03-25.
 */

public class FileClient2 extends Application {

    private static Socket socket;
    private static PrintStream out;

    private static ObjectOutputStream output;
    private static Scanner scanner = null;

    private File file = new File("Shared_Folder/");
    private static File file2; // = new File("Server_Folder/");
    private BorderPane layout;
    private ListView<String> table1, table2;

    @Override
    public void start(Stage primaryStage) throws IOException{

        primaryStage.setTitle("File Sharer v1.0");
        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(0, 0, 0, 0));
        editArea.setVgap(00);
        editArea.setHgap(00);

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event1) {
                String name = table2.getSelectionModel().getSelectedItem();
                String downName = "Server_Folder/" + name;
                out.println("download");
                downloadFile(downName);
                try {
                    RefreshFile(primaryStage);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                String name = table1.getSelectionModel().getSelectedItem();
                String uploadName = "Shared_Folder/" + name;
                out.println("upload");
                uploadFile(uploadName);
                try {
                    RefreshFile(primaryStage);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        editArea.add(downloadButton, 0, 0);
        editArea.add(uploadButton, 1, 0);

        table1 = new ListView<>();
        table1.getItems().addAll(file.list());
        table1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        table2 = new ListView<>();
        table2.getItems().addAll(file2.list());
        table2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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
            socket = new Socket("localhost", 1212);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out = new PrintStream(socket.getOutputStream());
        addServerFile();
        output = new ObjectOutputStream(socket.getOutputStream());

        launch(args);
        output.close();
        out.close();
    }

    public void RefreshFile(Stage primaryStage) throws IOException{
        primaryStage.close();
        start(primaryStage);
    }

    public static void addServerFile()throws IOException{
        scanner = new Scanner(socket.getInputStream());
        String name = scanner.next();
        //System.out.println(name);
        file2 = new File(name);
        //in.close();
    }


    public static void uploadFile(String fileName) {
        try {
            File myFile = new File(fileName);
            byte[] bytes = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);


            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(bytes, 0, bytes.length);

            OutputStream os = socket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(bytes.length);
            dos.write(bytes, 0, bytes.length);
            dos.flush();
            System.out.println("Uploaded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String fileName) {
        try {

            out.println(fileName);
            int bytesRead;
            //InputStream in = sock.getInputStream();

            DataInputStream clientData = new DataInputStream(socket.getInputStream());

            String fileName1 = clientData.readUTF();
            OutputStream output = new FileOutputStream(("Shared_Folder/" + fileName1));
            long size = clientData.readLong();
            byte[] buffer = new byte[8*1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) > -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            output.close();
            System.out.println("Download");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
