package com.example.inksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TextView logoutTextView;
    ImageView after;
    Button logoutButton;
    Fragment tempFragment=new MonitorTemp();
    Fragment lightFragment=new MonitorLight();
    Fragment waterFragment=new MonitorWater();
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //back

        after=findViewById(R.id.after);
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AfterLogin.class));
            }
        });



        toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        bottomNavigationView=findViewById(R.id.bnv);
        bottomNavigationView.setItemIconTintList(null);
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,tempFragment);
        transaction.addToBackStack(null);
        transaction.commit();


        logoutTextView=findViewById(R.id.farmer);

        logoutTextView.setText(SharedPrefManager.getInstance(getApplicationContext()).getEmail());
        logoutButton=findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(getApplicationContext(),SplashActivity.class));
                finish();
                finishAffinity();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_option_temp:
                        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,tempFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    case R.id.menu_option_light:
                        FragmentTransaction transaction_= getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,lightFragment);
                        transaction_.addToBackStack(null);
                        transaction_.commit();
                        return true;
                    case R.id.menu_option_water:
                        FragmentTransaction transaction__= getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,waterFragment);
                        transaction__.addToBackStack(null);
                        transaction__.commit();
                        return true;

                }
                return false;
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
