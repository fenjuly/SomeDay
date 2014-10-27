package com.example.liurongchan.traingdemo.demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by liurongchan on 14/10/26.
 */
public class App extends Application{

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
