package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by and on 23.01.16.
 */
public class Looper extends Thread implements Runnable{
    private final ExecutorService executor;
    private BlockingQueue<Runnable> mOperationsQueue;

    Looper(BlockingQueue<Runnable> _operationQueue){
        mOperationsQueue = _operationQueue;
        executor = Executors.newFixedThreadPool(5);
    }

    @Override
    public void run() {
        try {
            while (true){
                Operation operation = (Operation) mOperationsQueue.take();
                if(operation != null){
                    executor.execute(operation);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}