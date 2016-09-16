package doapps.marcogreen.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import java.util.Calendar;

import doapps.marcogreen.R;
import doapps.marcogreen.activity.MainActivity;
import doapps.marcogreen.session.SessionManager;
import doapps.marcogreen.settings.Constants;

/**
 * Created by Bryam Soto on 17/08/2016.
 */
public class NotifyService extends Service {
    private static Thread thread;
    private int NOTIFICATION_ID = 465023;

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void run() {
                while (true) {
                    Calendar calendar = Calendar.getInstance();
                    if (calendar.get(Calendar.HOUR_OF_DAY) == Constants.NOTIF_HOUR
                            && calendar.get(Calendar.MINUTE) == Constants.NOTIF_MINUTE
                            && calendar.get(Calendar.SECOND) == Constants.NOTIF_SECOND) {
                        notification();
                    }
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                        if (powerManager.isInteractive()) {
                            SessionManager.getInstance(getBaseContext()).addSecondActive();
                        }
                    } else {
                        SessionManager.getInstance(getBaseContext()).addSecondActive();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startService() {
        try {
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(getBaseContext().getString(R.string.app_name));
        String text = getBaseContext().getString(R.string.notify_message);
        builder.setContentText(text);

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(notificationSound);
        builder.setAutoCancel(true);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        android.support.v4.app.TaskStackBuilder stackBuilder = android.support.v4.app.TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        stackBuilder.addNextIntent(new Intent());

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManagerCompat.from(getBaseContext()).notify(NOTIFICATION_ID + 3, notification);
    }
}
