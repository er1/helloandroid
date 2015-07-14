package io.github.er1.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class HelloActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(getActivity(), R.string.hello_world, Toast.LENGTH_LONG).show();
        new AlertDialog.Builder(this)
                .setTitle(R.string.hello)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotificationManager nm = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
                        PendingIntent pendingIntent = PendingIntent.getService(HelloActivity.this, 0, new Intent(HelloActivity.this, HelloService.class), 0);
                        nm.notify(1, new NotificationCompat.Builder(HelloActivity.this)
                                .setContentTitle("\u03c0")
                                .setContentText(getResources().getText(R.string.hello_world))
                                .setSmallIcon(R.drawable.ic_notification)
                                .setWhen(0)
                                .setOngoing(true)
                                .setContentIntent(pendingIntent)
                                .build());
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotificationManager nm = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
                        nm.cancel(1);
                        finish();
                    }
                })
                .setMessage(R.string.hello_world)
                .show();
    }

    public static class HelloService extends Service {
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            int ret = super.onStartCommand(intent, flags, startId);
            Toast.makeText(this, R.string.hello_world, Toast.LENGTH_LONG).show();
            stopSelf();

            return ret;
        }
    }
}
