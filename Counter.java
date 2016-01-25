package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class Counter implements Runnable, Parser.Handler{
	private static Counter sINSTANCE;
    static {
        sINSTANCE = new Counter();
    }
	
    private BlockingQueue<Runnable> mOperationQueue;
    private BlockingQueue<Runnable> mLogQueue;
    private Parser mParser;
    private Looper mOperationLooper;
    private Logger mLogger;

    private Counter() {
        mParser = new Parser(this);
        mOperationQueue = new LinkedBlockingQueue<>(100);
        mLogQueue = new LinkedBlockingQueue<>(100);
        mOperationLooper = Looper.getInstance();
        mLogger = Logger.getInstance();
    }
    
    public static Counter getInstance(){
    	return sINSTANCE;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = Counter.getInstance();
        counter.init();
        counter.run();
    }

    private void init(){
        mOperationLooper.init(mOperationQueue);
        mLogger.init(mLogQueue);
        mOperationLooper.start();
        mLogger.start();
    }

    @Override
    public void run() {
        System.out.println("Enter enumeration of numbers here, use space to separate particular numbers: ");
        try {
            while(true){
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String inputString = bufferRead.readLine();
                mParser.parseString(inputString);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String _errorMessage) {
        mLogQueue.add(new Log(_errorMessage));
    }

    @Override
    public void onSuccess(int[] _intArray) {
        try {
            mOperationQueue.put(Operation.newInstance(_intArray, mLogQueue));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}