package com.microntek.radio;

import android.app.Application;
import com.microntek.CrashHandler;

public class RadioApp extends Application {
    public void onCreate() {
        CrashHandler.handleCrashes(getApplicationContext());
        super.onCreate();
    }
}
