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

public class Main extends Application {
    //private Canvas canvas;
   // private String hostName = "localhost";
    private File file = new File("Shared_Folder/");
    private File file2 = new File("Server_Folder/");
    private File[] list = file.listFiles();
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

            }
        });
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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


        //able1.getColumns().add(fileColumn);

        //table2 = new TableView();
        //table2.setEditable(true);

        layout = new BorderPane();
        layout.setTop(editArea);
        layout.setLeft(table1);
        layout.setRight(table2);


        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);

    }
}
