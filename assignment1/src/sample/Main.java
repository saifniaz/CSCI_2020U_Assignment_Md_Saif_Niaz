package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import java.io.*;

public class Main extends Application {

    private final File Dir = new File("/home/saif/IdeaProjects/assignment1/test/ham");
    private BorderPane layout;
    private TableView table;
    public TrainFile trainFile = new TrainFile();
    private TextField accField, preFeild;

    @Override
    public void start(Stage primaryStage) throws Exception{


        primaryStage.setTitle("Spam Master 3000");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("test"));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        process_train();

        if(mainDirectory.equals(Dir)) {
            process_test(Dir, "Ham");
        }else{
            process_test(mainDirectory, "Spam");
        }
        table = new TableView<>();
        table.setItems(DataSource.getAllFiles());
        table.setEditable(true);

       TableColumn<TestFile,String> fileColumn = null;
        fileColumn = new TableColumn<>("File");
        fileColumn.setMinWidth(400);
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));
        fileColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());
        fileColumn.setOnEditCommit((TableColumn.CellEditEvent<TestFile, String> event) -> {
            ((TestFile)event.getTableView().getItems().get(event.getTablePosition().getRow())).setFilename(event.getNewValue());
        });


        TableColumn<TestFile,Double> spamColumn = null;
        spamColumn = new TableColumn<>("Spam Probability");
        spamColumn.setMinWidth(220);
        spamColumn.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));


        TableColumn<TestFile,String> classColumn = null;
        classColumn = new TableColumn<>("Actual Class");
        classColumn.setMinWidth(120);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));

        table.getColumns().add(fileColumn);
        table.getColumns().add(classColumn);
        table.getColumns().add(spamColumn);


        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(10, 10, 10, 10));
        editArea.setVgap(10);
        editArea.setHgap(10);

        Label accLabel = new Label("Accuracy:");
        editArea.add(accLabel, 0, 1);
        TextField accField = new TextField();
        accField.setPromptText("accuracy");
        editArea.add(accField, 1, 1);

        Label preLabel = new Label("Precision:");
        editArea.add(preLabel, 0, 2);
        TextField preField = new TextField();
        preField.setPromptText("precision");
        editArea.add(preField, 1, 2);

        layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(editArea);

        Scene scene = new Scene(layout, 740, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void process_train(){
        File spam = new File("train/spam/");
        File ham1 = new File("train/ham2");
        File ham = new File("train/ham");
        trainFile.add_train(spam, ham, ham1 );
        trainFile.activity();
    }

    public void process_test(File file, String dir) throws  IOException{

        trainFile.add_test(file, dir);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
