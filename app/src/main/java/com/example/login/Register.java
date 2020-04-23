package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText inputPassword,inputEmail;
    Button  btLogin, btRegister;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        inputEmail = findViewById( R.id.inputEmail );
        inputPassword = findViewById( R.id.inputPassword );
        btLogin = findViewById( R.id.btLogin );
        btRegister = findViewById( R.id.btRegister );
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null){
            Intent Login = new Intent( Register.this, MainActivity.class);
            startActivity( Login );
        }

        btRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();

            }
        });

        btLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(Register.this, MainActivity.class);
                startActivity(in);
            }
        } );

    }

    public void insert(){

        try{
            String Email = inputEmail.getText().toString();
            String Password = inputPassword.getText().toString();

            if(inputEmail.getText().toString().trim().matches(emailPattern)) {
                fAuth.createUserWithEmailAndPassword( Email, Password )
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText( Register.this,
                                            "add firebase sukses", Toast.LENGTH_SHORT ).show();
                                    Intent Login = new Intent( Register.this, MainActivity.class);
                                    finish();
                                    startActivity( Login );
                                } else {
                                    Toast.makeText( Register.this,
                                            task.getException().getMessage(),Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } );
            }else{
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception ex){
            Toast.makeText(this,"Record Failed ",Toast.LENGTH_LONG).show();
        }
    }
}
