package io.github.er1.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public static class TextFragment extends Fragment {

        public final static String ARG_CONTENT = "content";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_text, container, false);
            if (rootView == null)
                return null;
            TextView text = (TextView) rootView.findViewById(R.id.text);
            text.setText(getArguments().getString(ARG_CONTENT));
            return rootView;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

            outState.putString(ARG_CONTENT, getArguments().getString(ARG_CONTENT));
        }
    }
}
