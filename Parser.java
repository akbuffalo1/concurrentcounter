package com.example;

/**
 * Created by and on 23.01.16.
 */
public class Parser {

    private Handler mHandler;

    public Parser(Handler _handler) {
        mHandler = _handler;
    }

    public void parseString(String _str){
        if(mHandler != null){
            if(!_str.isEmpty()){
                String[] strResult = _str.split(" ");

                int[] result = new int[strResult.length];

                try {
                    for (int i = 0; i < strResult.length; i++) {
                        result[i] = Integer.parseInt(strResult[i]);
                    }
                    mHandler.onSuccess(result);
                } catch (NumberFormatException nfe) {
                    mHandler.onError("There is no correct numbers enumeration..");
                }
            } else {
                mHandler.onError("Empty line detected, please type appropriate numbers..");
            }
        }
    }

    public interface Handler {
        void onError(String _errorMessage);
        void onSuccess(int[] intArray);
    }
}