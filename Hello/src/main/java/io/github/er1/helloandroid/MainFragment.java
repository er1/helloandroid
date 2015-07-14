package io.github.er1.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

public class MainFragment extends Fragment implements OnClickListener {
    private static int[] BUTTONS = new int[]{
            R.id.btnswap,
            R.id.btnshare,
            R.id.btnlist,
    };
    TextView tvData;
    TextView tvResult;
    String sharedText = "";
    SharedPreferences prefs;

    public static String rot13(CharSequence in) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < in.length(); i++) {
            char ch = in.charAt(i);
            char c = Character.toUpperCase(ch);
            if (c >= 'A' && c <= 'M')
                ch += 13;
            if (c >= 'N' && c <= 'Z')
                ch -= 13;
            out.append(ch);
        }
        return out.toString();
    }

    public static Cursor getTestCursor() {
        final String[] names = {"Charlie", "Juliet", "Mike", "Oscar", "Romeo", "Victor"};

        MatrixCursor cur = new MatrixCursor(new String[]{"_id", "val", "desc", "bool"});

        int row = 0;
        for (String name : names) {
            cur.addRow(new Object[]{row + 256, name, name.charAt(0) - 64 + ". " + name, row % 2});
            row++;
        }

        return cur;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        if (rootView == null)
            return null;

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        for (int button : BUTTONS)
            rootView.findViewById(button).setOnClickListener(this);

        tvData = (TextView) rootView.findViewById(R.id.data);
        tvResult = (TextView) rootView.findViewById(R.id.result);

        tvData.addTextChangedListener(new TextProcessor(tvResult));
        tvData.setText(sharedText);

        return rootView;
    }

    private void swap() {
        CharSequence a = tvData.getText();
        CharSequence b = tvResult.getText();
        tvData.setText(b);
        tvResult.setText(a);
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(tvData.getText())).setType("text/plain");
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.app_name)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnswap:
                swap();
                return;
            case R.id.btnshare:
                share();
                return;
            case R.id.btnlist:
                list();
                return;
            default:
        }
    }

    private void list() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.hello_world)
                .setCursor(getTestCursor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor cur = getTestCursor();
                        cur.moveToPosition(which);
                        tvData.setText(cur.getString(Arrays.asList(cur.getColumnNames()).indexOf("val")));
                    }
                }, "val")
                .show();
    }

    private class TextProcessor implements TextWatcher {
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
            sharedText = String.valueOf(s);
            target.setText(rot13(s));
        }
    }
}
