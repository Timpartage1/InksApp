package com.example.inksapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.inksapp.model.Coop;
import com.example.inksapp.network.APIClient;

import org.json.JSONObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    ConstraintLayout mainLayout;
     Button gotoLoginButton;
     Button registerButton;
     TextView backtoLoginButton;
     EditText firstNameEdit;
     EditText lastNameEdit;
     EditText emailEdit;
     EditText phoneEdit;
     EditText passwordEdit;
     EditText streetNumberEdit;
     EditText poultryWidthEdit;
     EditText poultryHeightEdit;
     EditText poultryNameEdit;
     Spinner genderEdit;
     Spinner provinceEdit;
     Spinner districtEdit;

    String firstName,lastName,email,password,gender,province,district,poultryName,cdate;
    String  phone,poultrySize,streetNumber,poultryHeighSize,poultryWidthSize;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        //hidding  keyboard
        // Get your layout set up, this is just an example
        mainLayout = (ConstraintLayout) findViewById(R.id.reglayout);
        // Then just use the following:
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

        //getting progress
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing...");


        //retrieving inputs information
        firstNameEdit=findViewById(R.id.first_name_edit);
        lastNameEdit=findViewById(R.id.last_name_edit);
        emailEdit=findViewById(R.id.email_edit);
        phoneEdit=findViewById(R.id.phone_edit);
        passwordEdit=findViewById(R.id.pass);
        genderEdit=findViewById(R.id.gender_edit);
        provinceEdit=findViewById(R.id.province_edit);
        districtEdit=findViewById(R.id.district_edit);
        streetNumberEdit=findViewById(R.id.street_number_edit);
        poultryHeightEdit=findViewById(R.id.poultry_h_edit);
        poultryWidthEdit=findViewById(R.id.poultry_w_edit);
        poultryNameEdit=findViewById(R.id.poultry_name_edit);
        registerButton=findViewById(R.id.reg_save);

        backtoLoginButton=findViewById(R.id.back_login);

        backtoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();

            }
        });







        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //displaying progress dialog
                progressDialog.show();
                progressDialog.dismiss();

                //taking values from input
                firstName=firstNameEdit.getText().toString();
                lastName=lastNameEdit.getText().toString();
                email=emailEdit.getText().toString();
                phone=phoneEdit.getText().toString();
                password=passwordEdit.getText().toString();
                gender=genderEdit.getSelectedItem().toString();
                province= provinceEdit.getSelectedItem().toString();
                district=districtEdit.getSelectedItem().toString();
                streetNumber=streetNumberEdit.getText().toString();
                poultryHeighSize=poultryHeightEdit.getText().toString();
                poultryWidthSize=poultryHeightEdit.getText().toString();
                poultryName=poultryNameEdit.getText().toString();
                cdate=String.valueOf(new Date().getDay())+"-"+String.valueOf(new Date().getMonth())+"-"+String.valueOf(new Date().getYear());

                //validating input
                if(firstName.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill firstname", Toast.LENGTH_SHORT).show();
                    return;
                }else if(lastName.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill lastname", Toast.LENGTH_SHORT).show();
                    return;
                }else if(email.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill email", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!email.contains("@")){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }else if(phone.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill phone number", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill password ", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.length()<8){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill password at least 8 char", Toast.LENGTH_SHORT).show();
                    return;
                } else if(streetNumber.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill street number", Toast.LENGTH_SHORT).show();
                    return;
                }else if(poultryHeighSize.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill poultry height", Toast.LENGTH_SHORT).show();
                    return;
                }else if(poultryWidthSize.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill poultry width", Toast.LENGTH_SHORT).show();
                    return;
                }else if(poultryName.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill poultry name", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                        int size=Integer.valueOf(poultryHeighSize)*Integer.valueOf(poultryWidthSize);
                    Call<Coop> coopCall= APIClient.apIinterface().registerCoop(firstName,lastName,email,Integer.valueOf(phone),password,gender,province,
                            district,Integer.valueOf(streetNumber),size,poultryName,cdate);

                    coopCall.enqueue(new Callback<Coop>() {
                        @Override
                        public void onResponse(Call<Coop> call, Response<Coop> response) {
                            progressDialog.show();
                            if(response.isSuccessful()){
                                progressDialog.dismiss();
                                JSONObject jsonObject=new JSONObject();


                                Toast.makeText(RegisterActivity.this, response.body()+  firstName+" registered successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            }else{
                                Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Coop> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }







            }
        });


    }
}
