package com.sumit.services1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;

import android.content.Intent;

import android.os.Build;
import android.os.IBinder;


import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.util.Objects;

public class FileOperationService extends Service {
    private String name= " ";

 private final Runnable task = () -> saveTofile();
    public FileOperationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();
        showNotificationAndStartForeGround();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            name = intent.getStringExtra("name");
        }
        launchBackgroundthread();
        return super.onStartCommand(intent, flags, startId);

    }

    private void launchBackgroundthread() {
        Thread thread = new Thread(task);
        thread.start();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void saveTofile(){
        try {
         File dir = new File(getFilesDir()+ File.separator + "Sumit");
         if(!dir.exists()){
             dir.mkdir();
         }
         File file = new File(dir, "name.txt");
         if(!file.exists()){

                 file.createNewFile();
             }
             FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            OutputStreamWriter  writer = new OutputStreamWriter(fileOutputStream);
            writer.append(name + "\n");
            writer.close();




            Intent intent = new Intent("com.sumit.services1");
            intent.putExtra("data", "write done");
            sendBroadcast(intent);
         }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showNotificationAndStartForeGround() {
        createChannel();

        NotificationCompat.Builder notificationBuilder;
        notificationBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setContentTitle("Service is running : yaay")
                .setContentText("name is android")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Notification notification;
        notification = notificationBuilder.build();
        startForeground(1, notification);
    }


    /*
Create noticiation channel if OS version is greater than or eqaul to Oreo
*/
    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "Nimit", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notifications");
            Objects.requireNonNull(this.getSystemService(NotificationManager.class)).createNotificationChannel(channel);
        }
    }

}