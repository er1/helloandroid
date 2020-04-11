package io.github.er1.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class HelloActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        Intent intent = new Intent(this, HelloService.class);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startService(intent);
        finish();
    }
}
