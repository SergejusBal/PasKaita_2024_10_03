package org.example;


import org.example.Fifth.Counter;
import org.example.FileOperations.Reader;
import org.example.FileOperations.Writer;
import org.example.Search.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Main {

    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {

        /////////////////////////////////////////////////////////////
        //////////////////////// 07 problem ////////////////////////

        System.out.println();
        System.out.println("07 problem");
        System.out.println();

        boolean winner = true;
        for (int i = 1; i < 5 ; i++){
            Thread temp = new Thread(new Counter(),"Runner: " + i);
            temp.start();
        }

        Thread.sleep(2000);
        /////////////////////////////////////////////////////////////
        //////////////////////// 08 problem ////////////////////////

        System.out.println();
        System.out.println("08 problem");
        System.out.println();



        String path = "C:\\Users\\Sergejus\\IdeaProjects\\PasKaita_2024_10_03\\src\\main\\resources\\text.txt";

        List<String> list = new ArrayList<>();

        Random random = new Random();
        for(int i= 0; i < 5; i++)  list.add("This is new line with ID: " + random.nextInt(1000,9999));


        Reader reader = new Reader(path,rwLock);
        Writer writer = new Writer(path,list);

        Thread thread01 = new Thread(reader);
        Thread thread02 = new Thread(reader);
        Thread thread03 = new Thread(writer,"Thread writer 1");
        Thread thread04 = new Thread(writer,"Thread writer 2");


        thread01.start();
        thread02.start();

        Thread.sleep(2000);

        thread03.start();
        thread04.start();



        /////////////////////////////////////////////////////////////
        //////////////////////// 09 problem ////////////////////////

        thread01.join();
        thread02.join();
        thread03.join();
        thread04.join();

        System.out.println();
        System.out.println("09 problem");
        System.out.println();

        String path1 = "C:\\Users\\Sergejus\\IdeaProjects\\PasKaita_2024_10_03\\src\\main\\resources\\FistFile.txt";
        String path2 = "C:\\Users\\Sergejus\\IdeaProjects\\PasKaita_2024_10_03\\src\\main\\resources\\SecoundFile.txt";
        String path3 = "C:\\Users\\Sergejus\\IdeaProjects\\PasKaita_2024_10_03\\src\\main\\resources\\ThirdFile.txt";

        String word = "Paradox";


        Search file01 = new Search(word,path1);
        Search file02 = new Search(word,path2);
        Search file03 = new Search(word,path3);

        Thread thread05 = new Thread(file01);
        Thread thread06 = new Thread(file02);
        Thread thread07 = new Thread(file03);

        thread05.start();
        thread06.start();
        thread07.start();

        thread05.join();
        thread06.join();
        thread07.join();

        System.out.println("\"" + word + "\" count is: " + file01.getWordCount());




    }
}