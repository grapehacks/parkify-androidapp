package com.grapeup.parkify.mvp.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grapeup.parkify.R;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.ButterKnife;

/**
 * Main fragment
 *
 * @author Pavlo Tymchuk
 */
public final class MainFragment extends Fragment implements MainContract.View {
    private MainContract.MainPresenter mMainPresenter;

    public static Fragment getInstance() {
        Bundle bundle = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ImageView dateIcon = (ImageView) view.findViewById(R.id.date_icon);
        dateIcon.setImageDrawable(new IconicsDrawable(getContext(), "faw-calendar-times-o"));

        TextView dateText = (TextView) view.findViewById(R.id.date_text);
        dateText.setText("23-08-2016");

        ButterKnife.bind(this, view);

        return view;
    }

}
