package io.github.er1.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    MainFragment mainfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if (savedInstanceState == null) {
            mainfragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container, mainfragment).commit();
        }

        if (Intent.ACTION_SEND.equals(intent.getAction())) {
            mainfragment.setSharedText(intent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }
}
