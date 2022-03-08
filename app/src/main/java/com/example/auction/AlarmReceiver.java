package com.example.auction;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.auction.DestinationActivity;
import com.example.auction.R;

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;
//Class này để hiển thị thông báo khi báo thức đến
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, DestinationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivities(context,0, new Intent[]{i},0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"huynv")
                .setSmallIcon(R.drawable.ic_baseline_vpn_key_24)
                .setContentTitle("Huynv Báo thức")
                .setContentText("Dậy thôi")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        //Phát nhạc
        mediaPlayer = MediaPlayer.create(context,R.raw.nhacchuong);
        mediaPlayer.start();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat .from(context);
        notificationManagerCompat.notify(123,builder.build());
//        Intent intent1 = new Intent(context, ServiceAPP.class);
//        context.startService(intent1);
    }
}
