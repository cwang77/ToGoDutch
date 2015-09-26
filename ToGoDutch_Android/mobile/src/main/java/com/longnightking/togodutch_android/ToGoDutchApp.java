package com.longnightking.togodutch_android;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by longNightKing on 5/5/15.
 */
public class ToGoDutchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "SNbRK3FPkqp6VWF0W23CUYUsO5L0Nxy74O7gHKGV", "Qpojkp918XJuMlJBS5FAxJ4RihIv1CXQsTjqhBHB");
    }
}
