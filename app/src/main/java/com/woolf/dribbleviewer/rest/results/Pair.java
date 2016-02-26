package com.woolf.dribbleviewer.rest.results;


public class Pair<T> {

    private T mValue;
    private String mMessage;

    public Pair(T value, String message) {
        mValue = value;
        mMessage = message;
    }

    public Object getValue() {
        return mValue;
    }

    public void setValue(T value) {
        mValue = value;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
