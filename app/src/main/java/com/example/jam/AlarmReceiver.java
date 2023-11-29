package com.example.jam;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager alarmNotificationManager;
    private String NOTIFICATION_CHANNEL_ID = "Jam";
    private String NOTIFICATION_CHANNEL_NAME = "Jam";
    private int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        playAudio(context);
        sendNotification(context);
    }

    private void playAudio(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.start();
    }

    private void sendNotification(Context context) {
        String notifTitle = "Alarm!";
        String notifContent = "Alarm";
        alarmNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent newIntent = new Intent(context, MainActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context, 0, newIntent, PendingIntent.FLAG_IMMUTABLE
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance
            );
            alarmNotificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notifBuilder.setContentTitle(notifTitle);
        notifBuilder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
        notifBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notifBuilder.setContentText(notifContent);
        notifBuilder.setAutoCancel(true);
        notifBuilder.setContentIntent(contentIntent);

        alarmNotificationManager.notify(NOTIFICATION_ID, notifBuilder.build());
    }
}
