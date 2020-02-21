package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    String text;

    Button button1;
    TextView textView2;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button1 = findViewById(R.id.button1);
        editText = findViewById( R.id.editText );
        textView2 = findViewById( R.id.textView2 );


        button1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                text = editText.getText().toString();
                if (TextUtils.isEmpty(text)){
                    Toast.makeText(Home.this,
                            "input kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    textView2.setText(text);

                }
            }
        });

    }

    private void showToast(String text) {

        Toast.makeText(Home.this, text, Toast.LENGTH_SHORT).show();
    }
}
