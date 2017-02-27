package com.xuan.study.study.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.xuan.study.study.R;
import com.xuan.study.study.activity.MainActivity;

/**
 * Created by dengzhaoxuan on 2017/2/27.
 */

public class WeatherService  extends Service {
    private static final int NOTIFY_ID = 123;
    private static final String BROADCAST_ACTION = "XUAN";


    @Override
    public void onCreate() {
        super.onCreate();
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCAST_ACTION);
        sendOrderedBroadcast(broadcastIntent,null);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.weather)
                .setContentTitle("2017年2月27日")
                .setContentText("今天天气不错啊，18~26度，晴");

        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder
                .getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final Notification notification = mBuilder.build();
        mNotifyMgr.notify(NOTIFY_ID,notification);
        startForeground(NOTIFY_ID,notification);

    }
}
