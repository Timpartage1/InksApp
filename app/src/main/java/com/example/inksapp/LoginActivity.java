package com.example.inksapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.inksapp.model.User;
import com.example.inksapp.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button registerButton,loginButton;
    EditText emailEdit,passwordEdit;
    ProgressDialog progressDialog;
    ConstraintLayout mainLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        // Get your layout set up, this is just an example
        mainLayout = (ConstraintLayout) findViewById(R.id.loglayout);

        // Then just use the following:
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

        //hiding keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //sharePreferences
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();;
            startActivity(new Intent(LoginActivity.this,AfterLogin.class));
            return;
        }

        emailEdit=findViewById(R.id.username_login);
        passwordEdit=findViewById(R.id.pass_login);

        registerButton=findViewById(R.id.go_to_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });


        progressDialog=new ProgressDialog(this);

        progressDialog.setMessage("Loading...");



        loginButton=findViewById(R.id.log_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                String email=emailEdit.getText().toString().trim();
                String password=passwordEdit.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Fill all the fields please", Toast.LENGTH_SHORT).show();
                }else{
                    Call<User> userCall=APIClient.apIinterface().userLogin(email,password);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            progressDialog.dismiss();
                            if(response.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(email,password);
                                    startActivity(new Intent(LoginActivity.this,AfterLogin.class));
                                    finish();

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Please provide correct credentials", Toast.LENGTH_SHORT).show();
                        }
                    });

                }





            }
        });
    }
}
