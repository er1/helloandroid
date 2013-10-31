package io.github.er1.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private ShareActionProvider mShareActionProvider;
    protected String sharedText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Intent intent = getIntent();
        String action = intent.getAction();

        if (Intent.ACTION_SEND.equals(action)) {
            sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if (mShareActionProvider != null) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, sharedText);
            intent.setType("text/plain");
            mShareActionProvider.setShareIntent(intent);
        }

        return true;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnHello(View view) {
        Toast t = Toast.makeText(getApplicationContext(), R.string.hello_world, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.NO_GRAVITY, 0, 0);
        t.show();
    }

    public void btnSwap(View view) {
        TextView data = (TextView) findViewById(R.id.data);
        TextView result = (TextView) findViewById(R.id.result);
        CharSequence a = data.getText();
        CharSequence b = result.getText();
        data.setText(b);
        result.setText(a);
    }

    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            TextView tvdata = (TextView) getView().findViewById(R.id.data);
            TextView tvresult = (TextView) getView().findViewById(R.id.result);
            tvdata.addTextChangedListener(new TextProcessor(tvresult));
            tvdata.setText(((MainActivity) getActivity()).sharedText);
        }

        class TextProcessor implements TextWatcher {
            TextView target;

            public TextProcessor(TextView target) {
                this.target = target;
            }

            @Override
            public void afterTextChanged(Editable e) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String data = s.toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, data);
                intent.setType("text/plain");

                ((MainActivity) getActivity()).setShareIntent(intent);
                target.setText(Rot13.rot13(s));
            }
        }
    }
}
