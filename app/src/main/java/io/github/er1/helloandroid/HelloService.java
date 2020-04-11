package io.github.er1.helloandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class HelloService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int ret = super.onStartCommand(intent, flags, startId);
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (text == null)
            text = getString(R.string.hello_world);
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        stopSelf();
        return ret;
    }
}
