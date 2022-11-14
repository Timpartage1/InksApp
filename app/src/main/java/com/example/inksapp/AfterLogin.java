package com.example.inksapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AfterLogin  extends AppCompatActivity {
    ImageView goToMonitor;
    CardView goMonitor,goGuide,goSettings,goAbout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_login);
        goToMonitor=findViewById(R.id.go_to_monitor);
        goMonitor=findViewById(R.id.go_mo);
        goGuide=findViewById(R.id.go_guide);
        goSettings=findViewById(R.id.go_settings);
        goAbout=findViewById(R.id.go_about);

        goToMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AfterLogin.this,MainActivity.class));

            }
        });
        goMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AfterLogin.this,MainActivity.class));

            }
        });

        goGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AfterLogin.this,GuideActivity.class));

            }
        });

        goAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AfterLogin.this);
                builder.setCancelable(true);
                builder.setIcon(R.drawable.poultry_);
                builder.setTitle("About");
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("Call number",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });

                builder.setMessage("Inksapp has been developped by Timothee supervised by Dr ALfred UWITONZE as final year project, +250786554365");
                AlertDialog alert=builder.create();
                alert.show();
            }
        });


        goSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AfterLogin.this);
                builder.setCancelable(true);
                builder.setIcon(R.drawable.poultry_);
                builder.setTitle("Coming feature");
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("Upgrade",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
            }
        });




    }
}
