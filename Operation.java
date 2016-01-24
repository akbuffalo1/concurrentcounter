package com.example;

import java.util.concurrent.BlockingQueue;

/**
 * Created by and on 24.01.16.
 */
public class Operation implements Runnable {
    private BlockingQueue<Runnable> mLogQueue;
    private int[] mArray;
    private static volatile int mTotalSumm = 0;
    private static volatile int mCounter = 0;
    public Operation(int[] _array, BlockingQueue<Runnable> _loggerQueue){
        mArray = _array;
        mLogQueue = _loggerQueue;
    }

    public static Operation newInstance(int[] _array, BlockingQueue<Runnable> _loggerQueue){
        return new Operation(_array, _loggerQueue);
    }
    @Override
    public void run() {
        int result = Calculator.calculate(mArray);
        mTotalSumm += result;
        mLogQueue.add(new Log(String.format(Consts.MESSAGE_TEMPLATE, ++mCounter, result, mTotalSumm)));
    }
}
