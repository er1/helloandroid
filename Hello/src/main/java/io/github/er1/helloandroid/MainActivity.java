package io.github.er1.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_share:
                String data = ((EditText) findViewById(R.id.data)).getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, data);
                intent.setType("text/plain");
                startActivity(intent);
                return true;
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

    public void btnCopyDown(View view) {
        TextView tvdata = (TextView) findViewById(R.id.data);
        TextView tvresult = (TextView) findViewById(R.id.result);
        tvresult.setText(tvdata.getText());
    }

    public void btnCopyUp(View view) {
        TextView tvdata = (TextView) findViewById(R.id.data);
        TextView tvresult = (TextView) findViewById(R.id.result);
        tvdata.setText(tvresult.getText());
    }

    /**
     * A placeholder fragment containing a simple view.
     */
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
                target.setText(Rot13.rot13(s));
            }
        }

    }

}
