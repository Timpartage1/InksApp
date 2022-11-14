package com.example.inksapp;

import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.inksapp.model.TempDataModel;
import com.example.inksapp.model.WaterDataModel;
import com.example.inksapp.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonitorWater extends Fragment {

    private Handler mRepeatHandler;
    private Runnable mRepeatRunnable;
    private final static int UPDATE_INTERVAL=3000;

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    TextView level;

    public MonitorWater(){

    }

    //notification
    private void sendNotification(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("myChan","notChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }


        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(),"myChan")
                .setSmallIcon(R.drawable.poultry_)
                .setContentTitle("InksApp Water Alert")
                .setContentText("Please action reqired on water ")
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


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.monitor_water, container, false);
        level=view.findViewById(R.id.level);


        //display updating each 3 secondes
        mRepeatHandler=new Handler();
        mRepeatRunnable=new Runnable() {
            @Override
            public void run() {
               //runining


                Call<WaterDataModel> waterDataModelCall= APIClient.apIinterface().getWaterData();

                waterDataModelCall.enqueue(new Callback<WaterDataModel>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(Call<WaterDataModel> call, Response<WaterDataModel> response) {
                        if(response.isSuccessful()){
                            float wlevel=response.body().getWater_level();
                            level.setText(String.valueOf(wlevel));
                            if(wlevel==1){
                                level.setTextColor(R.color.teal_200);
                            }else if(wlevel==0){
                                level.setTextColor(R.color.purple_700);
                                sendNotification();
                                notificationManagerCompat.notify(1,notification);
                            }else{
                                level.setTextColor(R.color.black);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WaterDataModel> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



                mRepeatHandler.postDelayed(mRepeatRunnable,UPDATE_INTERVAL);
            }
        };
        mRepeatHandler.postDelayed(mRepeatRunnable,UPDATE_INTERVAL);

        return view;
    }




}
