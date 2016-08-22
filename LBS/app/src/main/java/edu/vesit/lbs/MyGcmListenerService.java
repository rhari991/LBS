package edu.vesit.lbs;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService
{
    @Override
    public void onMessageReceived(String from, Bundle data)
    {
        super.onMessageReceived(from, data);
        final String message = data.getString("message");
        Log.e("Received string : ", message);
        createNotification();
    }

    public void createNotification()
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(MainScreenActivity.mContext)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Reminder")
                        .setContentText("You are late in returning a book !");
        Intent resultIntent = new Intent(MainScreenActivity.mContext, LoginActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        MainScreenActivity.mContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(1, mBuilder.build());

    }
}