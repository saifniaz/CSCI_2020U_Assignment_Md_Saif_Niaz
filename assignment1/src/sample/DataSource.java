package sample;

import javafx.beans.Observable;
import javafx.collections.*;

import java.util.ArrayList;

/**
 * Created by saif on 10/03/16.
 */
public class DataSource {
    private static String filename;
    private static double spamProbability;
    private static String actualClass;
    public DataSource(String filename, double spamProbability, String actualClass){
        this.filename = filename;
        this.spamProbability = spamProbability;
        this.actualClass = actualClass;
    }

    public static ObservableList<TestFile>  getAllFiles(){
        ObservableList<TestFile> files = FXCollections.observableArrayList();

        files.add(new TestFile(filename, spamProbability, actualClass));
        return files;
    }
}
