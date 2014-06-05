package io.github.er1.helloandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFragment extends Fragment {

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
