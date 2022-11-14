package com.example.inksapp;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.inksapp.arduino.heater.PutOffHeater;
import com.example.inksapp.arduino.heater.PutOnHeater;
import com.example.inksapp.arduino.heater.PutOnHeater100;
import com.example.inksapp.arduino.heater.PutOnHeater120;
import com.example.inksapp.arduino.heater.PutOnHeater140;
import com.example.inksapp.arduino.heater.PutOnHeater160;
import com.example.inksapp.arduino.heater.PutOnHeater180;
import com.example.inksapp.arduino.heater.PutOnHeater200;
import com.example.inksapp.arduino.heater.PutOnHeater220;
import com.example.inksapp.arduino.heater.PutOnHeater40;
import com.example.inksapp.arduino.heater.PutOnHeater60;
import com.example.inksapp.arduino.heater.PutOnHeater80;
import com.example.inksapp.arduino.heater.PutOnHeaterMax;
import com.example.inksapp.arduino.light.PutOffLight;
import com.example.inksapp.arduino.light.PutOnLight;
import com.example.inksapp.arduino.light.PutOnLight100;
import com.example.inksapp.arduino.light.PutOnLight120;
import com.example.inksapp.arduino.light.PutOnLight140;
import com.example.inksapp.arduino.light.PutOnLight160;
import com.example.inksapp.arduino.light.PutOnLight180;
import com.example.inksapp.arduino.light.PutOnLight200;
import com.example.inksapp.arduino.light.PutOnLight220;
import com.example.inksapp.arduino.light.PutOnLight40;
import com.example.inksapp.arduino.light.PutOnLight60;
import com.example.inksapp.arduino.light.PutOnLight80;
import com.example.inksapp.arduino.light.PutOnLightMax;
import com.example.inksapp.model.TempDataModel;
import com.example.inksapp.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonitorLight extends Fragment {
    private Handler mRepeatHandler;
    private Runnable mRepeatRunnable;
    private final static int UPDATE_INTERVAL=3000;

    SeekBar increaseLight;
    Switch switchLight;
    ImageView signalLight;

    public MonitorLight(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monitor_light, container, false);
        increaseLight=view.findViewById(R.id.increase_light);
        switchLight=view.findViewById(R.id.switch_lamp);
        signalLight=view.findViewById(R.id.light_status);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            increaseLight.setMin(0);
            increaseLight.setMax(255);
        }

        //status
        switchLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchLight.isChecked()){
                    signalLight.setImageResource(R.drawable.ic_baseline_brightness_1_24);
                }else{
                    signalLight.setImageResource(R.drawable.ic_baseline_brightness_1_24_red);
                    new PutOffLight().execute();
                }

                if(!switchLight.isChecked()){
                    new PutOffLight().execute();
                }
            }
        });
        switchLight.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(switchLight.isChecked()){
                    signalLight.setImageResource(R.drawable.ic_baseline_brightness_1_24);
                }else{
                    signalLight.setImageResource(R.drawable.ic_baseline_brightness_1_24_red);
                    new PutOffLight().execute();
                }
                return true;
            }
        });


        //controlling light


        //managing heater
        increaseLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress=0;


                if(switchLight.isChecked()){
                    progress=increaseLight.getProgress();

                }else{
                    increaseLight.setProgress(progress);
                }



                if(progress==0){
                    new PutOffLight().execute();
                }else if(progress<=20){
                    new PutOnLight().execute();
                }else if(progress<=40){
                    new PutOnLight40().execute();
                }else if(progress<=60){
                    new PutOnLight60().execute();
                }else if(progress<=80){
                    new PutOnLight80().execute();
                }else if(progress<=100){
                    new PutOnLight100().execute();
                }else if(progress<=120){
                    new PutOnLight120().execute();
                }else if(progress<=140){
                    new PutOnLight140().execute();
                }else if(progress<=160){
                    new PutOnLight160().execute();
                }else if(progress<=180){
                    new PutOnLight180().execute();
                }else if(progress<=200){
                    new PutOnLight200().execute();
                }else if(progress<=220){
                    new PutOnLight220().execute();
                }else if(progress<=240){
                }else{
                    new PutOnLightMax().execute();

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        return view;
    }




}