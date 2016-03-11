package sample;

import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Created by saif on 08/03/16.
 */

public class EvaluteFile {
    public Map<String, Integer> trainSpamFreq_e = null;
    public Map<String, Float> Prob_Spam = new TreeMap<>();
    public Map<String, Integer> trainHamFreq_e = null;
    public Map<String, Float> Prob_Ham = new TreeMap<>();
    public Map<String, Float> Prob_final = new TreeMap<>();
    float spam_file;

    public EvaluteFile() {}

    public void calculate_final(Map map) throws IOException{
        Map<String, Integer> temp = map;

        Set<String> SIndex = temp.keySet();
        Iterator<String> KeyIterator_t = SIndex.iterator();
        while(KeyIterator_t.hasNext()){
            String key_Spam = KeyIterator_t.next();
            float count_ham, count_Spam;
           if(Prob_Ham.containsKey(key_Spam) && Prob_Spam.containsKey(key_Spam)){
               count_ham = Prob_Ham.get(key_Spam);
               count_Spam = Prob_Ham.get(key_Spam);
            }else if(Prob_Ham.containsKey(key_Spam) && !Prob_Spam.containsKey(key_Spam)){
               count_ham = Prob_Ham.get(key_Spam);
               count_Spam = 0.0f;
           }else if(!Prob_Ham.containsKey(key_Spam) && Prob_Spam.containsKey(key_Spam)){
               count_ham = 0.0f;
               count_Spam = Prob_Spam.get(key_Spam);
           }else{count_ham = 0.0f; count_Spam = 0.0f;}

            float Prob;
            if(count_ham == 0 && count_Spam == 0) {
                Prob = 0.0f;
            } else if(count_Spam != 0 && count_ham == 0) {
                Prob = 1.0f;
            } else {
                Prob = (count_Spam) / (count_ham + count_Spam);
            }
            Prob_final.put(key_Spam, Prob);

        }
    }

    public Map<String, Float> calculate_ham(Map map, long filesize){
        trainHamFreq_e = map;
        Set<String> wIndex = trainHamFreq_e.keySet();
        Iterator<String> KeyIterator = wIndex.iterator();
        while(KeyIterator.hasNext()){
            String key = KeyIterator.next();
            float count =trainHamFreq_e.get(key);
            float Prob = (count*(key.length()))/((filesize));
            Prob_Ham.put(key, Prob);
        }
        return Prob_Ham;
    }

    public Map<String, Float> calculate_spam(Map map, long filesize){
        trainSpamFreq_e = map;
        Set<String> wIndex = trainSpamFreq_e.keySet();
        Iterator<String> KeyIterator = wIndex.iterator();
        while(KeyIterator.hasNext()){
            String key = KeyIterator.next();
            float count =trainSpamFreq_e.get(key);
            float Prob = (count*(key.length()))/(filesize);
            Prob_Spam.put(key, Prob);
        }
        return Prob_Spam;
    }

    public double calculate_file(Map map1) throws IOException{
        calculate_final(map1);
        //System.out.println(Prob_final.isEmpty());
        Set<String> fIndex = Prob_final.keySet();
        Iterator<String> keyIterator = fIndex.iterator();
        float n = 0.0f;
        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            double count = Prob_final.get(key);
            if((count) == 0){
                n =+ (float)(Math.log(1.0 - count) - 0);
            }else{
                n =+ (float)(0 - Math.log(count));
            }
        }

        spam_file = (float) ((1.0)/(1.0 + Math.pow(Math.E, n)));
        return spam_file;
    }


}
