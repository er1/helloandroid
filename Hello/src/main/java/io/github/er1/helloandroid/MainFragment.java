package io.github.er1.helloandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment implements OnClickListener {
    Button btnHello;
    Button btnSwap;
    Button btnShare;
    TextView tvData;
    TextView tvResult;
    ListView lvList;
    String sharedText = "";

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

        MatrixCursor cur = new MatrixCursor(new String[]{"_id", "val", "desc"});

        int row = 0;
        for (String name : names) {
            cur.addRow(new Object[]{row, name, name.charAt(0) - 64 + ". " + name});
            row++;
        }

        return cur;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        if (rootView == null)
            return null;

        btnHello = (Button) rootView.findViewById(R.id.btnhello);
        btnSwap = (Button) rootView.findViewById(R.id.btnswap);
        btnShare = (Button) rootView.findViewById(R.id.btnshare);
        tvData = (TextView) rootView.findViewById(R.id.data);
        tvResult = (TextView) rootView.findViewById(R.id.result);

        lvList = (ListView) rootView.findViewById(R.id.list);

        btnHello.setOnClickListener(this);
        btnSwap.setOnClickListener(this);
        btnShare.setOnClickListener(this);

        tvData.addTextChangedListener(new TextProcessor(tvResult));
        tvData.setText(sharedText);

        SimpleCursorAdapter sca;

        sca = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2,
                getTestCursor(),
                new String[]{"val", "desc"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
        lvList.setAdapter(sca);

        return rootView;
    }

    public void hello() {
        Toast.makeText(getActivity(), R.string.hello_world, Toast.LENGTH_LONG).show();
    }

    public void swap() {
        CharSequence a = tvData.getText();
        CharSequence b = tvResult.getText();
        tvData.setText(b);
        tvResult.setText(a);
    }

    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, sharedText);
        intent.setType("text/plain");
        startActivity(intent);
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
            default:
        }
    }

    public void setSharedText(CharSequence sharedText) {
        this.sharedText = sharedText.toString();
        if (tvData != null) {
            tvData.setText(this.sharedText);
        }
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
            sharedText = s.toString();
            target.setText(rot13(s));
        }
    }
}
