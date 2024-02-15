import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract any extra data passed to the receiver
        String extraData = intent.getStringExtra("extra_key"); // Replace with your extra key

        // Create an intent to launch the main activity when the notification is clicked
        Intent notificationIntent = new Intent(context, MainActivity.class); // Replace with your main activity
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create a notification with your desired content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Alarm Triggered");
        builder.setContentText("Extra Data: " + extraData); // Replace with your content
        builder.setSmallIcon(R.mipmap.ic_launcher); // Replace with your notification icon

        // Set the notification sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        builder.setSound(alarmSound);

        builder.setContentIntent(contentIntent);

        // Trigger the notification
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
