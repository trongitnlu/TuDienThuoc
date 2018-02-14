package com.tudien.tudienthuoc.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tudien.tudienthuoc.NotificationActivity;
import com.tudien.tudienthuoc.R;

public class FirebaseMsgService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage rm) {
        // Hiển thị thông báo
        createNotification(rm.getNotification().getBody());
    }

    // Xử lý hiển thị thông báo
    private void createNotification(String msg) {
        Intent i = new Intent(this, NotificationActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);

        Uri u = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder b = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_forum_black_24dp)
                .setContentTitle("Từ Điển Thuốc")
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(u)
                .setContentIntent(pi);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(0, b.build());
    }
}