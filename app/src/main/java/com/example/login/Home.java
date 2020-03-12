package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Home extends AppCompatActivity {

    boolean session;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private  ViewPager viewPager;

    SharedPreferences pref;

    @Override


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        pref = getApplicationContext().getSharedPreferences(  " Mypref", MODE_PRIVATE );
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
        adapter.AddFragment(new fragment1(), "Fragment_1");
        adapter.AddFragment( new fragment2(), "Fragment_2" );
        adapter.AddFragment( new fragment3(), "Fragment_3" );

         viewPager.setAdapter( adapter );
         tabLayout.setupWithViewPager( viewPager );



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
}
