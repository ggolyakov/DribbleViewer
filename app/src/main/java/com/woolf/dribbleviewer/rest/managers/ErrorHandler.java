package com.woolf.dribbleviewer.rest.managers;

import android.util.Log;

import com.woolf.dribbleviewer.DribbleApplication;
import com.woolf.dribbleviewer.R;

import org.json.JSONException;
import org.json.JSONObject;

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
            mErrorCode = 1;
            mMessage = DribbleApplication.APP_CONTEXT.getString(R.string.error_connect);
        } else if (throwable instanceof SocketTimeoutException) {
            Log.e("REST", "SocketTimeoutException");
            mErrorCode = 2;
            mMessage = DribbleApplication.APP_CONTEXT.getString(R.string.error_timeout);
        } else if (throwable instanceof HttpException) {
            mErrorCode = ((HttpException) throwable).code();
            try {
                String json = ((HttpException) throwable).response().errorBody().string();
                JSONObject object = new JSONObject(json);
                if (object.has("message") && object.isNull("message")) {
                    mMessage = object.getString("message");
                }
            } catch (IOException | JSONException e) {
                mMessage = DribbleApplication.APP_CONTEXT.getString(R.string.error_default);
            }

        } else {
            mErrorCode = 0;
            mMessage = DribbleApplication.APP_CONTEXT.getString(R.string.error_default);
        }
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
