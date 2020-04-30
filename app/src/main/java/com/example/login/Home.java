package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.security.PublicKey;

import static com.example.login.App.CHANNEL_1_ID;

public class Home extends AppCompatActivity {

    boolean session;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private  ViewPager viewPager;
    private WifiManager wifiManager;
    private NotificationManagerCompat notificationManagerCompat;
    public String textOn = "wifi sedang menyala";
    public String textOff = "wifi sedang mati";


    SharedPreferences pref;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged( newConfig );

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText( this,"ORIENTATION LANDSCAPE",Toast.LENGTH_SHORT ).show();
        }else {
            Toast.makeText( this,"ORIENTATION POTRAIT",Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        pref = getApplicationContext().getSharedPreferences(  " Mypref", MODE_PRIVATE );

        wifiManager = (WifiManager) getApplicationContext().getSystemService( Context.WIFI_SERVICE );
        notificationManagerCompat =NotificationManagerCompat.from( this );

        SharedPreferences.Editor editor= pref.edit();
        SESSION();
        editor.putString( "KEY1", "Test Shared References" );
        editor.commit();

        Log.d("test shared preferences", pref.getString( "KEY1",  null ));

        setContentView(R.layout.activity_home);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById( R.id.viewpager_id );
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new fragment1(), "Check Wifi");
        adapter.AddFragment( new fragment2(), "List Film" );
        adapter.AddFragment( new fragment3(), "Daftar" );
        adapter.AddFragment( new fragmentKamera(), "Kamera" );

        viewPager.setAdapter( adapter );
        tabLayout.setupWithViewPager( viewPager );

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String isiBundle;
        }

    }

    private void showToast(String text) {

        Toast.makeText(Home.this, text, Toast.LENGTH_SHORT).show();
    }

    public void SESSION (){
        session = Boolean.valueOf( Save.read( getApplicationContext(), "session", "false" ));
            if (!session){
                Toast.makeText( Home.this, "gak ada akun",Toast.LENGTH_SHORT ).show();
                Intent register = new Intent( getApplicationContext(), Register.class );
                startActivity( register );
                finish();
            }else{
                Toast.makeText( this, "You is Logged In", Toast.LENGTH_SHORT ).show();

            }

    }

    protected void onStart(){
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, intentFilter);
    }
    protected void onStop(){
        super.onStop();

        unregisterReceiver(wifiReceiver);
    }
    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            switch(wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    kirimNotifikasi();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    kirimNotifikasi();
                    break;
            }
        }
    };

    public void kirimNotifikasi(){
        String toastPesan;
        Intent activityIntent = new Intent(this, Home.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,activityIntent, 0);

        if (wifiManager.isWifiEnabled()){
            toastPesan = textOn;
            Intent broadcastIntent = new Intent(this, WifiChangerReceiver.class);
            broadcastIntent.putExtra("Wifi Changer",toastPesan);
            PendingIntent actionIntent = PendingIntent.getBroadcast(this,0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                    .setSmallIcon( R.drawable.ic_wifi )
                    .setContentTitle (  "WIFI STATE" )
                    .setContentText( textOn )
                    .setPriority( NotificationCompat.PRIORITY_HIGH )
                    .setCategory( NotificationCompat.CATEGORY_MESSAGE   )
                    .addAction(R.mipmap.ic_launcher,"Toast Wifi Status",actionIntent)
                    .build();

            notificationManagerCompat.notify( 1, notification );


        }
        else{
            Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                    .setSmallIcon( R.drawable.ic_wifi )
                    .setContentTitle (  "WIFI STATE" )
                    .setContentText( textOff )
                    .setPriority( NotificationCompat.PRIORITY_HIGH )
                    .setCategory( NotificationCompat.CATEGORY_MESSAGE   )
                    .build();

            notificationManagerCompat.notify( 1, notification );
        }

    }
}
