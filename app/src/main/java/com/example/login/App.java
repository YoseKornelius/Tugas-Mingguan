package com.example.login;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID = "channel1";



    @Override
    public void onCreate() {

        super.onCreate();
        createNotificartionChannels();
    }

    public void createNotificartionChannels() {

            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
             );
            channel1.setDescription( "WIFI State" );

            NotificationManager manager = getSystemService( NotificationManager.class );
            manager.createNotificationChannel( channel1 );

    }
}