package io.github.er1.helloandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MainService extends Service {
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
