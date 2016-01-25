package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by and on 23.01.16.
 */
public final class Looper extends Thread implements Runnable{
	private static Looper sINSTANCE;
	
    private final ExecutorService executor;
    private BlockingQueue<Runnable> mOperationsQueue;

    private Looper(BlockingQueue<Runnable> _operationQueue){
        mOperationsQueue = _operationQueue;
        executor = Executors.newFixedThreadPool(5);
    }
    
    public static Looper getInstance(BlockingQueue<Runnable> _operationQueue){
    	if(sINSTANCE == null){
    		sINSTANCE = new Looper(_operationQueue);
    	}
    	return sINSTANCE;
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