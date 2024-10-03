package org.example.Fifth;


import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Counter implements Runnable{

    private static AtomicBoolean flag;

    public Counter() {
        flag = new AtomicBoolean(true);
    }

    @Override
    public void run() throws RuntimeException{
        Random random = new Random();
        int randomNumber = random.nextInt(1,100);
        int counter = 0;
        while(counter < 1000){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter += randomNumber;
        }

        if(flag.compareAndExchange(true,false)){
            System.out.println("Winner: " + Thread.currentThread().getName());
        }

    }
}
