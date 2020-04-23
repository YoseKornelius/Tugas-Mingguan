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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    String email,password;
    Button button1;
    EditText editText; // login
    EditText editText2; //password
    FirebaseAuth fAuth;

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
                fAuth = FirebaseAuth.getInstance();
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
        fAuth.signInWithEmailAndPassword( email,password )
                .addOnCompleteListener( MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                                Toast.makeText( MainActivity.this, "LOGIN BERHASIL",
                                        Toast.LENGTH_SHORT).show();
                                Intent home = new Intent( MainActivity.this,  Home.class);
                                startActivity( home );
                                finish();

                        }
                        else {
                            Toast.makeText(MainActivity.this,
                                    "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                } );

    }


}
