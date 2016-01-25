package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by and on 23.01.16.
 */
public final class Looper extends Thread implements Runnable{
	private static Looper sINSTANCE;
    static{
        sINSTANCE = new Looper();
    }
	
    private final ExecutorService executor;
    private BlockingQueue<Runnable> mOperationsQueue;

    private Looper(){
        executor = Executors.newFixedThreadPool(5);
    }

    public void init(BlockingQueue<Runnable> _operationQueue){
        if(mOperationsQueue == null)
            mOperationsQueue = _operationQueue;
    }
    
    public static Looper getInstance(){
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