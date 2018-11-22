package cse.job.asif.job4cse.broadcasts;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import cse.job.asif.job4cse.R;
import cse.job.asif.job4cse.database.DatabaseHelper;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private DatabaseHelper db;

    @Override
    public void onReceive(Context context, Intent intent) {

        long id = intent.getLongExtra("id",-1);
        String title = intent.getStringExtra("title");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"Channel Two")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Deadline is near")
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        Notification notification = mBuilder.build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int)id,notification);

    }
}