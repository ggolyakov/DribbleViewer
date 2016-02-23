package com.woolf.dribbleviewer;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class DribbleApplication extends Application {


    public static Context APP_CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        APP_CONTEXT = this;
        Fresco.initialize(this);
    }
}
