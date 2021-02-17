package com.admin.ecosense.Alarm;

/**
 * Created by parag on 13/09/17.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.admin.ecosense.FCM.NotificationUtils;
import com.admin.ecosense.geofence_21.MapFragment;


/**
 * Created by sonu on 10/04/17.
 */

public class AlarmNotificationService extends IntentService {
    private NotificationManager alarmNotificationManager;

    //Notification ID for Alarm
    public static final int NOTIFICATION_ID = 1;

    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        //Send notification
        sendNotification();
    }

    //handle notification
    private void sendNotification() {
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.playNotificationSound();
        Intent resultIntent = new Intent(this, MapFragment.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        showNotificationMessage(this, "Wash hands", "Please wash hands with soap or sanitizer", "00:00:00", resultIntent);
    }
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        NotificationUtils notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }


}