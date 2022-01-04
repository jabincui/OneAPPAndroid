package com.oneapp.oneappandroidapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.oneapp.oneappandroidapp.OneAPP;
import com.oneapp.oneappandroidapp.R;


public class NotificationTestActivity extends AppCompatActivity {

    private final String TAG = "MTAG_NotificationTest";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);

        final String CHANNEL_ID = "SOME_CHANNEL_ID";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "name", importance);
        channel.setDescription("description");
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        findViewById(R.id.show_notification).setOnClickListener(v -> {
            Log.d(TAG, "onCreate: going");
            Context context = this;
            int requestCode = 0;
            Intent intent = new Intent(this, OneAPP.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            final int flags = PendingIntent.FLAG_ONE_SHOT;
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context, requestCode, intent, 0);

            NotificationCompat.Builder builder
                    = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.favicon)
                    .setContentTitle("标题")
                    .setContentText("内容")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的内容"))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
//            builder.notify();

            NotificationManagerCompat notificationManagerCompat
                    = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(187986, builder.build());
        });
    }
}