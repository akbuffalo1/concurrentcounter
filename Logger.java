package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by and on 24.01.16.
 */
public final class Logger extends Thread implements Runnable {
	private static final Logger sINSTANCE;
    static{
        sINSTANCE = new Logger();
    }
	
    private BlockingQueue<Runnable> mLogQueue;
    private ExecutorService executor;

    private Logger(){
        executor = Executors.newFixedThreadPool(5);
    }

    public void init(BlockingQueue<Runnable> _logQueue){
        if(mLogQueue == null)
            mLogQueue = _logQueue;
    }
    
    public static Logger getInstance(){
    	return sINSTANCE;
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
