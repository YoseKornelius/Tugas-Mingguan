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

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText inputPassword,inputEmail;
    Button  btLogin, btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        inputEmail = findViewById( R.id.inputEmail );
        inputPassword = findViewById( R.id.inputPassword );
        btLogin = findViewById( R.id.btLogin );
        btRegister = findViewById( R.id.btRegister );

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

            SQLiteDatabase db = openOrCreateDatabase
                    ( "user", Context.MODE_PRIVATE, null );
            db.execSQL( "Create TABLE IF NOT EXISTS user" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, user VARCHAR,pass VARCHAR)" );
            if(inputEmail.getText().toString().trim().matches(emailPattern)) {
                String sql = "insert into user(user,pass) values (?,?)";
                SQLiteStatement statement = db.compileStatement( sql );
                statement.bindString( 1, Email );
                statement.bindString( 2, Password );
                statement.execute();
                Toast.makeText( this, "Record added", Toast.LENGTH_LONG ).show();
            }else{
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            }
            inputPassword.setText( "" );
            inputEmail.setText( "" );
            inputEmail.requestFocus();

        }
        catch (Exception ex){
            Toast.makeText(this,"Record Failed ",Toast.LENGTH_LONG).show();
        }
    }
}
