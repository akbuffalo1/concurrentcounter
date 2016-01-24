package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by and on 24.01.16.
 */
public class Logger extends Thread implements Runnable {
    private BlockingQueue<Runnable> mLogQueue;
    private ExecutorService executor;

    Logger(BlockingQueue<Runnable> _logQueue){
        mLogQueue = _logQueue;
        executor = Executors.newFixedThreadPool(5);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Log log = (Log) mLogQueue.take();
                if(log != null){
                    executor.execute(log);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
