package com.android.crazywheel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemDetailFragment extends Fragment {

    Element element;
    public static final String TAG = "detail";

    public ItemDetailFragment(Element element) {
        this.element = element;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);

        this.getActivity().supportInvalidateOptionsMenu();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        if (view != null) {
            ((TextView) view.findViewById(R.id.title)).setText(this.element.getTitle());
            ((TextView) view.findViewById(R.id.text)).setText(this.element.getText());
        }

        return view;
    }

    public Element getElement() {

        return this.element;

    }

}
