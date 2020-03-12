package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String email,password;
    Button button1;
    EditText editText;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    //proses mencocokkan apakah input email valid atau tidak
                    if ( true ) {
                        showToast(login);
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    }
                    Intent in =new Intent(MainActivity.this, Home.class);
                    startActivity(in);

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

}
