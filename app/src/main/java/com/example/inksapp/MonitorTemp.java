package com.example.inksapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.inksapp.arduino.fan.PutOffFan;
import com.example.inksapp.arduino.fan.PutOnFan;
import com.example.inksapp.arduino.fan.PutOnFan100;
import com.example.inksapp.arduino.fan.PutOnFan120;
import com.example.inksapp.arduino.fan.PutOnFan140;
import com.example.inksapp.arduino.fan.PutOnFan160;
import com.example.inksapp.arduino.fan.PutOnFan180;
import com.example.inksapp.arduino.fan.PutOnFan200;
import com.example.inksapp.arduino.fan.PutOnFan220;
import com.example.inksapp.arduino.fan.PutOnFan40;
import com.example.inksapp.arduino.fan.PutOnFan60;
import com.example.inksapp.arduino.fan.PutOnFan80;
import com.example.inksapp.arduino.fan.PutOnFanMax;
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
import com.example.inksapp.model.TempDataModel;
import com.example.inksapp.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonitorTemp extends Fragment {
    private Handler mRepeatHandler;
    private Runnable mRepeatRunnable;
    private final static int UPDATE_INTERVAL=3000;
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    TextView displayTemp, displayHum,action;
    SeekBar increaseTemp,decreaseTemp;
    Switch switchHeater,switchFan;
    ImageView signalTemp,signalIR,signalFan;


    public MonitorTemp(){

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTempData();


    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.monitor_temp, container, false);

        //binding view
        displayHum=view.findViewById(R.id.display_hum);
        displayTemp=view.findViewById(R.id.display_temp);
        increaseTemp=view.findViewById(R.id.increase_temp);
        switchHeater=view.findViewById(R.id.switch_heater);
        decreaseTemp=view.findViewById(R.id.fan_seek);
        switchFan=view.findViewById(R.id.switch_fan);
        signalTemp=view.findViewById(R.id.signal_temp);
        signalIR=view.findViewById(R.id.signal_status_ir);
        signalFan=view.findViewById(R.id.signal_fan_status);
        action=view.findViewById(R.id.action);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            increaseTemp.setMin(0);
            increaseTemp.setMax(255);
       }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            decreaseTemp.setMin(0);
            decreaseTemp.setMax(255);
        }

        //status
        switchHeater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchHeater.isChecked()){
                    signalIR.setImageResource(R.drawable.ic_baseline_brightness_1_24);
                }else{
                    signalIR.setImageResource(R.drawable.ic_baseline_brightness_1_24_red);
                    //addition
                    new PutOffHeater().execute();
                }
            }
        });
        switchHeater.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(switchHeater.isChecked()){
                    signalIR.setImageResource(R.drawable.ic_baseline_brightness_1_24);
                }else{
                    signalIR.setImageResource(R.drawable.ic_baseline_brightness_1_24_red);

                }
                return true;
            }
        });

        switchFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchFan.isChecked()){
                    signalFan.setImageResource(R.drawable.ic_baseline_brightness_1_24);
                }else{
                    signalFan.setImageResource(R.drawable.ic_baseline_brightness_1_24_red);
                }
            }
        });

        switchFan.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(switchFan.isChecked()){
                    signalFan.setImageResource(R.drawable.ic_baseline_brightness_1_24);
                }else{
                    signalFan.setImageResource(R.drawable.ic_baseline_brightness_1_24_red);
                    new PutOffFan().execute();
                }
                return true;
            }
        });



      //managing heater
      increaseTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
             progress=0;

              if(switchHeater.isChecked()){
                  progress=increaseTemp.getProgress();

              }else{
                  increaseTemp.setProgress(progress);
              }

              if(!switchHeater.isChecked()){
                  new PutOffHeater().execute();
              }

             if(progress==0){
                new PutOffHeater().execute();
             }else if(progress<=20){
                 new PutOnHeater().execute();
              }else if(progress<=40){
                 new PutOnHeater40().execute();
             }else if(progress<=60){
                 new PutOnHeater60().execute();
             }else if(progress<=80){
                 new PutOnHeater80().execute();
             }else if(progress<=100){
                 new PutOnHeater100().execute();
             }else if(progress<=120){
                 new PutOnHeater120().execute();
             }else if(progress<=140){
                 new PutOnHeater140().execute();
             }else if(progress<=160){
                 new PutOnHeater160().execute();
             }else if(progress<=180){
                 new PutOnHeater180().execute();
             }else if(progress<=200){
                 new PutOnHeater200().execute();
             }else if(progress<=220){
                 new PutOnHeater220().execute();
             }else if(progress<=240){
             }else{
                 new PutOnHeaterMax().execute();

             }
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {

          }
      });

        //managing fan
        decreaseTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress=0;

                if(!switchFan.isChecked()){
                    new PutOffFan().execute();
                }

                if(switchFan.isChecked()){
                    progress=decreaseTemp.getProgress();

                }else{
                    decreaseTemp.setProgress(progress);
                }



                if(progress==0){
                    new PutOffFan().execute();
                }else if(progress<=20){
                    new PutOnFan().execute();
                }else if(progress<=40){
                    new PutOnFan40().execute();
                }else if(progress<=60){
                    new PutOnFan60().execute();
                }else if(progress<=80){
                    new PutOnFan80().execute();
                }else if(progress<=100){
                    new PutOnFan100().execute();
                }else if(progress<=120){
                    new PutOnFan120().execute();
                }else if(progress<=140){
                    new PutOnFan140().execute();
                }else if(progress<=160){
                    new PutOnFan160().execute();
                }else if(progress<=180){
                    new PutOnFan180().execute();
                }else if(progress<=200){
                    new PutOnFan200().execute();
                }else if(progress<=220){
                    new PutOnFan220().execute();
                }else if(progress<=240){
                }else{
                    new PutOnFanMax().execute();

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //display updating each 3 secondes
        mRepeatHandler=new Handler();
        mRepeatRunnable=new Runnable() {
            @Override
            public void run() {
                getTempData();



                mRepeatHandler.postDelayed(mRepeatRunnable,UPDATE_INTERVAL);
            }
        };
        mRepeatHandler.postDelayed(mRepeatRunnable,UPDATE_INTERVAL);
        return view;
    }
    //notification
    private void sendNotification(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("myChan","notChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        //uri
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //not
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(),"myChan")
                .setSmallIcon(R.drawable.poultry_)
                .setContentTitle("InksApp Temperature Alert")
                .setContentText("Please action required")
                .setSound(uri)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        //action while clicked

        Intent intent=new Intent(getContext(),MainActivity.class);
        intent.putExtra("Yes",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(getContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);

        //adding action

        //

        builder.addAction(R.drawable.ic_launcher_foreground,"Yes",pendingIntent);

        notification= builder.build();
        notificationManagerCompat=NotificationManagerCompat.from(getContext());


    }

    //gettting temperature data from database
    private void getTempData(){
        Call<TempDataModel> tempDataModelCallget= APIClient.apIinterface().getTempData();
        tempDataModelCallget.enqueue(new Callback<TempDataModel>() {
            @Override
            public void onResponse(Call<TempDataModel> call, Response<TempDataModel> response) {
                if(response.isSuccessful()){
                    float temp=response.body().getTemperature();
                displayTemp.setText(String.valueOf(temp));
                action.setText("Temperature normal-No action required");

                if(temp>25 || temp<20){
                    action.setText("Action is required on temperature");

                    //flag signal
                    signalTemp.setImageResource(R.drawable.ic_baseline_error_24);

                    //notify if temperature>30
                    sendNotification();
                    notificationManagerCompat.notify(1,notification);
                }

                displayHum.setText(String.valueOf(response.body().getHumidity()));

                }else{
                    Toast.makeText(getContext(), "Error processing request", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<TempDataModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
