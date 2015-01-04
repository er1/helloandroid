package io.github.er1.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TextActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            TextFragment textfragment = new TextFragment();

            Bundle b = new Bundle();
            Intent intent = getIntent();
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            b.putString(TextFragment.ARG_CONTENT, text);

            textfragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().add(R.id.container, textfragment).commit();
        }
    }
}
