package org.example.FileOperations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Writer implements Runnable{

    private volatile String path;
    private volatile List<String> content;
    static AtomicBoolean isWriting;

    public Writer() {
    }

    public Writer(String path, List<String> content) {
        this.path = path;
        this.content = content;
        if(isWriting == null) isWriting = new AtomicBoolean(false);
    }

    private void irasytiEiluteIFaila(String line, String path){

        Random random = new Random();

        try {
            FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            System.out.println("Writing: "  + line + " Active thread: " + Thread.currentThread().getName());

            Thread.sleep(random.nextInt(500,2000));

            bufferedWriter.write(line);
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Nepavyko įrašyti į failą! Klaida: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static AtomicBoolean getIsWriting() {
        return isWriting;
    }

    @Override
    public synchronized void run() {
        isWriting.set(true);
        for(String line : content) irasytiEiluteIFaila(line,path);
        isWriting.set(false);
    }
}
