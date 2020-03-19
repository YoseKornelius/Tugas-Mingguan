package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    String email,password;
    Button button1;
    EditText editText; // login
    EditText editText2; //password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from( this );

        editText = findViewById(R.id.editText);

        editText2 = findViewById(R.id.editText2);

        button1 = findViewById(R.id.button1);

        // LOGIN
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editText.getText().toString();
                password= editText2.getText().toString();

                String login = email + " - " + password;
                //test input field kosong atau tidak
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ){
                    Toast.makeText(MainActivity.this,
                            "Email atau Password salah", Toast.LENGTH_SHORT).show();
                }
                else {
                   login();
                   String bandel = editText.getText().toString();
                   Bundle extras = new Bundle();
                   extras.putString("KEY", "text" );
                }
            }
        });

        //membuat tulisan siqn up clickable
        TextView textview = findViewById(R.id.textView2);

        String text = "Belum punya akun gan? skuy SIGN UP";

        SpannableString ss = new SpannableString (text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //Toast.makeText(MainActivity.this, "sign up belum jadi", Toast.LENGTH_SHORT).show();
                Intent in =new Intent(MainActivity.this, Register.class);
                startActivity(in);
            }
        };

        ss.setSpan(clickableSpan1, 27,34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        textview.setText(ss);
        textview.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showToast(String text) {

        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void login(){
        String Email= editText.getText().toString();
        String Pass = editText2.getText().toString();

       if(null != checkUser(Email,Pass)){

            String userDB = checkUser( Email,Pass );

           Intent broadcastIntent = new Intent( "My_ACTION" );
           broadcastIntent.setComponent( new ComponentName( getPackageName(),
                   "com.example.login.MybroadcastReceiver") );

           getApplicationContext().sendBroadcast( broadcastIntent );

            Intent in =new Intent(MainActivity.this, Home.class);
            in.putExtra( "email", userDB);
            startActivity(in);
        }
        else {
            Toast.makeText
                    ( this,"Email atau Password kurang tepat",Toast.LENGTH_LONG ).show();
            editText.setText( "" );
            editText2.setText( "" );
            editText.requestFocus();
        }

    }

    public  String checkUser(String Email, String Pass){
        SQLiteDatabase db = openOrCreateDatabase
                ( "user", Context.MODE_PRIVATE, null );
        Cursor cursor = db.rawQuery( "select user,pass from user where user = ? and pass =?",
                new String[]{Email,Pass} );
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            String email = cursor.getString( 0 );
            String password = cursor.getString( 1 );
            Save.save( getApplicationContext(), "session","true" );
//            SharedPreferences.Editor sp = getSharedPreferences( "Email",MODE_PRIVATE ).edit();
//            sp.putString( "email", email );
//            sp.apply();
            cursor.close();
            System.out.println("berhasil");
            return email;

        }
        return null;

    }

}
