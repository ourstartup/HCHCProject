package com.casic.sensorhub;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by admin on 2015/5/3.
 */
public class SensorHubApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .build());
    }
}
