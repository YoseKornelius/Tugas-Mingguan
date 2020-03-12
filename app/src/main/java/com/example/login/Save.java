package com.example.login;

import android.content.Context;
import android.content.SharedPreferences;

public class Save {

    public static void  save(Context ctx, String Name, String value) {
        SharedPreferences s = ctx.getSharedPreferences( "clipcodes", Context.MODE_PRIVATE );
        SharedPreferences.Editor edt = s.edit();
        edt.putString( Name, value );
        edt.apply();
    }

    public static String read(Context ctx, String Name, String DefaultValue){
        SharedPreferences s = ctx.getSharedPreferences( "clipcodes", Context.MODE_PRIVATE );
        return s.getString( Name ,DefaultValue);

    }
}
