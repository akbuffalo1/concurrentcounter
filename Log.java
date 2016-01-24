package com.example;

/**
 * Created by and on 24.01.16.
 */
public class Log implements Runnable {
    private String message;
    public Log(String _message){
        message = _message;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " >> " +message);
    }
}
