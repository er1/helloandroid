package io.github.er1.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainFragment extends Fragment implements OnClickListener {
    private static int[] BUTTONS = new int[]{
            R.id.btnhello,
            R.id.btnswap,
            R.id.btnshare,
            R.id.btnshow,
            R.id.btnlist,
            R.id.btnnotify
    };
    TextView tvData;
    TextView tvResult;
    ListView lvList;
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
        lvList = (ListView) rootView.findViewById(R.id.list);

        tvData.addTextChangedListener(new TextProcessor(tvResult));
        tvData.setText(sharedText);

        SimpleCursorAdapter sca;

        sca = new SimpleCursorAdapter(getActivity(),
//                android.R.layout.simple_list_item_2,
                R.layout.list_main,
                getTestCursor(),
                new String[]{"val", "desc"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
        lvList.setAdapter(sca);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString(TextFragment.ARG_CONTENT, Long.toString(id));
                Fragment f = new TextFragment();
                f.setArguments(b);
                FragmentActivity fa = getActivity();
                fa.getSupportFragmentManager().beginTransaction().replace(R.id.container, f).addToBackStack(null).commit();
            }
        });

        return rootView;
    }

    private void hello() {
        Toast.makeText(getActivity(), R.string.hello_world, Toast.LENGTH_LONG).show();
    }

    private void swap() {
        CharSequence a = tvData.getText();
        CharSequence b = tvResult.getText();
        tvData.setText(b);
        tvResult.setText(a);
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, tvData.getText());
        intent.setType("text/plain");
        startActivity(intent);
    }

    private void show() {
        StringBuilder sb = new StringBuilder();
        sb.append(tvData.getText());
        Bundle b = new Bundle();
        b.putString(TextFragment.ARG_CONTENT, sb.toString());
        Fragment f = new TextFragment();
        f.setArguments(b);
        FragmentActivity fa = getActivity();
        fa.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, f)
                .addToBackStack(null)
                .commit();
    }

    private void notification() {
        StringBuilder sb = new StringBuilder();
        sb.append(tvData.getText());

        NotificationManager nm = (NotificationManager) getActivity().getSystemService(Activity.NOTIFICATION_SERVICE);
        if (sb.length() == 0)
            nm.cancel(1);
        else
            nm.notify(1, new NotificationCompat.Builder(getActivity())
                    .setContentText(sb.toString())
                    .setContentTitle("\u03c0")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setWhen(0)
                    .build());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnhello:
                hello();
                return;
            case R.id.btnswap:
                swap();
                return;
            case R.id.btnshare:
                share();
                return;
            case R.id.btnshow:
                show();
                return;
            case R.id.btnlist:
                list();
                return;
            case R.id.btnnotify:
                notification();
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

    public void setSharedText(CharSequence sharedText) {
        this.sharedText = sharedText.toString();
        if (tvData != null) {
            tvData.setText(this.sharedText);
        }
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
            StringBuilder sb = new StringBuilder();
            sharedText = sb.append(s).toString();
            target.setText(rot13(s));
        }
    }
}
