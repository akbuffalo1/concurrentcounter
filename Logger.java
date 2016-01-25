package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by and on 24.01.16.
 */
public final class Logger extends Thread implements Runnable {
	private static Logger sINSTANCE;
	
    private BlockingQueue<Runnable> mLogQueue;
    private ExecutorService executor;

    private Logger(BlockingQueue<Runnable> _logQueue){
        mLogQueue = _logQueue;
        executor = Executors.newFixedThreadPool(5);
    }
    
    public static Logger getInstance(BlockingQueue<Runnable> _logQueue){
    	if(sINSTANCE == null){
    		sINSTANCE = new Logger(_logQueue);
    	}
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
