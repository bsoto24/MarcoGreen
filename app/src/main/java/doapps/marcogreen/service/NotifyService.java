package doapps.marcogreen.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import java.util.Calendar;

import doapps.marcogreen.R;
import doapps.marcogreen.activity.MainActivity;

/**
 * Created by Bryam Soto on 17/08/2016.
 */
public class NotifyService extends Service {
    private int NOTIFICATION_ID = 465023;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        counterTrack();
        return super.onStartCommand(intent, flags, startId);
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


    /**
     * Thread
     **/
    private void counterTrack() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 18 && Calendar.getInstance().get(Calendar.MINUTE) == 24 && Calendar.getInstance().get(Calendar.SECOND) == 0) {
                            notification();
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void notification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(getBaseContext().getString(R.string.app_name));
        String text = getBaseContext().getString(R.string.notify_message);
        builder.setContentText(text);

        Bitmap profilePicture = BitmapFactory.decodeResource(
                getBaseContext().getResources(),
                R.mipmap.ic_launcher
        );
        builder.setLargeIcon(profilePicture);

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(notificationSound);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManagerCompat.from(getBaseContext()).notify(NOTIFICATION_ID + 3, notification);
    }
}
