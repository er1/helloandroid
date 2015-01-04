package io.github.er1.helloandroid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            MainFragment mainfragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container, mainfragment).commit();
        }
    }
}
