package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Counter implements Parser.Handler{
    private BlockingQueue<Runnable> mOperationQueue;
    private BlockingQueue<Runnable> mLogQueue;
    private Parser mParser;
    private Looper mOperationLooper;
    private Logger mLogger;

    private Counter() throws InterruptedException {
        mParser = new Parser(this);
        mOperationQueue = new LinkedBlockingDeque<>(100);
        mLogQueue = new LinkedBlockingDeque<>(100);
        mOperationLooper = new Looper(mOperationQueue);
        mOperationLooper.start();
        mLogger = new Logger(mLogQueue);
        mLogger.start();
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        counter.init();
    }

    private void init(){
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