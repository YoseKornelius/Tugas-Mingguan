package com.example.login;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WifiChangerReceiver extends BroadcastReceiver {
    private WifiManager wifiManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getStringExtra("Wifi Changer");
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}