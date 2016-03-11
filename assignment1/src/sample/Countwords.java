package sample;

import java.util.*;
import java.io.*;

/**
 * Created by saif on 10/03/16.
 */
public class Countwords {
    public Map<String, Integer> wordCount;

    public Countwords(){
        wordCount = new TreeMap<>();
    }

    public void processFile(File file) throws IOException {
        if (file.isDirectory()) {
            // process all of the files recursively
            File[] filesInDir = file.listFiles();
            for (int i = 0; i < filesInDir.length; i++) {
                processFile(filesInDir[i]);
            }
        } else if (file.exists()) {
            // load all of the data, and process it into words
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    countWord(word);
                }
            }
        }
    }

    private boolean isWord(String str){
        String pattern = "^[a-zA-Z]*$";
        if (str.matches(pattern)){
            return true;
        }
        return false;
    }

    private void countWord(String word) {
        if (wordCount.containsKey(word)) {
            int oldCount = wordCount.get(word);
            wordCount.put(word, oldCount + 1);
        } else {
            wordCount.put(word, 1);
        }
    }

}
