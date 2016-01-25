package com.example;

import java.util.Random;

/**
 * Created by and on 23.01.16.
 */
public final class Calculator {
	private Calculator(){}
    public static int calculate(int[] input) {
        int result = 0;
        try {
            for (int i : input){
                result += i;
                Thread.sleep(new Random().nextInt(1000));
            }
        } catch (InterruptedException ignored) {
            // Skip
        }

        return result;
    }
}
