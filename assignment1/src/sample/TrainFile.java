package sample;

import java.util.*;
import java.io.*;

/**
 * Created by saif on 08/03/16.
 */

public class TrainFile {
    Countwords countwords = new Countwords();

    public Map<String, Integer> wordsCounter = null;
    public Map<String, Integer> trainSpamFreq = new TreeMap<>();
    public Map<String, Integer> trainHamFreq = new TreeMap<>();
    private File file_s, file_h, file_h1, file_t;
    double spam_Prob = 0;
    String dir;


    public  TrainFile(){
    }
    public void add_train(File file1, File file2, File file3){
        file_s = file1;
        file_h = file2;
        file_h1 = file3;
    }
    public void add_test(File file, String dir){
        this.file_t = file;
        this.dir = dir;
        activity_test();
    }

    public void activity_test(){
        try{
            processFile(file_t);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void activity(){
        try {
            countwords.processFile(file_h);
            countwords.processFile(file_h1);
            countwords.processFile(file_s);
            wordsCounter = countwords.wordCount;
            //processFile(file_s);
            //processFile(file_h);
            //processFile(file_h1);
            //System.out.println(spam_Prob);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void processFile(File file)throws IOException {
        if (file.isDirectory()) {
            File[] file_inDir = file.listFiles();
            for (int i = 0; i < file_inDir.length; i++) {
                processFile(file_inDir[i]);
            }
        } else if (file.exists()) {
            processFile_spam(file);
            processFile_ham(file);
            EvaluteFile evaluteFile = new EvaluteFile();
            evaluteFile.calculate_spam(trainSpamFreq, file_s.length());
            evaluteFile.calculate_ham(trainHamFreq, (file_h.length() + file_h1.length()));
            spam_Prob = evaluteFile.calculate_file(wordsCounter);
            TestFile testFile = new TestFile(file.toString(), spam_Prob, dir);
        }
    }

    public void processFile_spam(File file) throws IOException {
        //System.out.println("File name: " + file);
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isMatch(word)){
                    addMap_spam(word);
                    continue;
                }
            }
        }
    }

    public void processFile_ham(File file) throws IOException {
        //System.out.println("File name: " + file);
        if  (file.exists()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isMatch(word)) {
                    addMap_ham(word);
                    continue;
                }
            }
        }
    }

    private boolean isMatch(String str) {
        if(wordsCounter.containsKey(str)){
            return true;
        }
        return false;
    }

    private void addMap_spam(String word) {
        if (trainSpamFreq.containsKey(word)) {
            int oldCount = trainSpamFreq.get(word);
            trainSpamFreq.put(word, oldCount + 1);
        } else {
            trainSpamFreq.put(word, 1);
        }
    }

    private void addMap_ham(String word) {
        if (trainHamFreq.containsKey(word)) {
            int oldCount = trainHamFreq.get(word);
            trainHamFreq.put(word, oldCount + 1);
        } else {
            trainHamFreq.put(word, 1);
        }
    }

}