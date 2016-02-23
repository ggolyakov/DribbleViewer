package com.woolf.dribbleviewer.rest.results;

import com.google.gson.annotations.SerializedName;

/**
 * Created by woolf on 12.02.16.
 */
public class RequestResult<T> {
    @SerializedName("data")
    protected T mData;
    @SerializedName("message")
    protected String mMessage;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
