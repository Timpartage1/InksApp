package com.example.inksapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_NAME="myshared";
    private static final String KEY_EM="email";
    private static final String KEY_PASS="password";

    private SharedPrefManager(Context mCtx){
        this.mCtx=mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance==null){
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String  email, String password){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_EM,email);
        editor.putString(KEY_PASS,password);
        editor.apply();
        return true;

    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_EM,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getEmail(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EM,null);

    }


}
