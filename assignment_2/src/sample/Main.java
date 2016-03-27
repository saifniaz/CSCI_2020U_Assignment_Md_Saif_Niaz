package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;

public class Main extends Application {
    //private Canvas canvas;
   // private String hostName = "localhost";
    private BorderPane layout;
    private TableView table1, table2;

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("File Sharer v1.0");
        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(0, 0, 10, 0));
        editArea.setVgap(00);
        editArea.setHgap(00);

        Button downloadButton = new Button("Download");
        Button uploadButton = new Button("Upload");
        editArea.add(downloadButton, 0, 0);
        editArea.add(uploadButton, 1, 0);

        table1 = new TableView();
        table1.setEditable(true);

        table2 = new TableView();
        table2.setEditable(true);

        layout = new BorderPane();
        layout.setTop(editArea);
        layout.setLeft(table1);
        layout.setRight(table2);


        Scene scene = new Scene(layout, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);

    }
}
