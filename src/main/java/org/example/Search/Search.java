package org.example.Search;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Search implements Runnable{


    private StringBuilder content;
    private String path;
    private String word;
    static AtomicInteger wordCount;

    public Search(String word,String path) {
        this.content = new StringBuilder();
        this.path = path;
        this.word = word;
        if(wordCount == null) wordCount = new AtomicInteger();
    }

    public void nuskaitytiFaila(String path){
        String line;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                content.append(line);
            }

            fileReader.close();
            bufferedReader.close();
        }catch (IOException e) {
            System.err.println("Nepavyko nuskaityti failą! Klaida: " + e.getMessage());
        }

    }

    public int getWordCount() {
        return wordCount.get();
    }

    private void countWord(){

        String stringContent = content.toString();
        String[] wordArray = stringContent.split(" ");
        int count = 0;
        for(String s : wordArray){
            if(s.equals(word)) {
                count++;
                System.out.println("+1");
            }
        }
        wordCount.getAndAdd(count);
    }

    @Override
    public void run() {
        nuskaitytiFaila(path);
        countWord();
    }
}
