package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by and on 24.01.16.
 */
public class Operation implements Runnable {
    private BlockingQueue<Runnable> mLogQueue;
    private int[] mArray;
    private static volatile AtomicInteger sTotalSumm = new AtomicInteger(0);
    private static volatile AtomicInteger sCounter = new AtomicInteger(0);
    private int count;
    public Operation(int[] _array, BlockingQueue<Runnable> _loggerQueue){
        mArray = _array;
        mLogQueue = _loggerQueue;
        count = sCounter.addAndGet(1);
    }

    public static Operation newInstance(int[] _array, BlockingQueue<Runnable> _loggerQueue){
        return new Operation(_array, _loggerQueue);
    }
    @Override
    public void run() {
        int result = Calculator.calculate(mArray);
        sTotalSumm.getAndAdd(result);
        mLogQueue.add(new Log(String.format(Consts.MESSAGE_TEMPLATE, count, result, sTotalSumm)));
    }
}