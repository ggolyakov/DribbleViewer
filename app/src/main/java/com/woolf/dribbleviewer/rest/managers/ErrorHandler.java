package com.woolf.dribbleviewer.rest.managers;

import android.util.Log;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

public class ErrorHandler {

    private int mErrorCode;
    private String mMessage;


    public ErrorHandler(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            Log.e("REST", "UnknownHostException");
        } else if (throwable instanceof SocketTimeoutException) {
            Log.e("REST", "SocketTimeoutException");
        } else if (throwable instanceof HttpException) {
            mErrorCode = ((HttpException) throwable).code();
            try {
                String json = ((HttpException) throwable).response().errorBody().string();
                Log.e("REST",json);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
