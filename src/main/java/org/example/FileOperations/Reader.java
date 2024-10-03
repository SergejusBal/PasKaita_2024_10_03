package org.example.FileOperations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reader implements Runnable {
    private static ReentrantReadWriteLock.ReadLock readLock;
    private static ReentrantReadWriteLock.WriteLock writeLock;


    String path;

    public Reader(String path, ReentrantReadWriteLock rwLock) {
       readLock = rwLock.readLock();
       writeLock = rwLock.writeLock();
       this.path = path;
    }

    private void nuskaitytiFaila(String path) {
        String line;
        Random random = new Random();

   //    writeLock.lock();

        try {
            FileReader fileReader = new FileReader(path);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                Thread.sleep(random.nextInt(500,1000));
                waitForWriter();
                System.out.println(line);

            }

            fileReader.close();
            bufferedReader.close();

        } catch (IOException e) {
            System.err.println("Nepavyko nuskaityti failą! Klaida: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

     //  writeLock.unlock();

    }

    private void waitForWriter(){
        if(Writer.getIsWriting().get()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            waitForWriter();
        }

    }


    @Override
    public void run() {
        nuskaitytiFaila(path);
    }
}
