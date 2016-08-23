package com.grapeup.parkify.mvp.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grapeup.parkify.R;
import com.grapeup.parkify.mvp.messages.MessagesDataReceiver;
import com.grapeup.parkify.mvp.messages.MessagesService;
import com.grapeup.parkify.tools.UserDataHelper;
import com.mikepenz.iconics.IconicsDrawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.style.Theme_Black_NoTitleBar_Fullscreen;

/**
 * Main fragment
 *
 * @author Pavlo Tymchuk
 */
public final class MainFragment extends Fragment implements MainView, MessagesDataReceiver.Receiver {
    private MainPresenter mMainPresenter;
    private Dialog dialog;
    private MessagesDataReceiver mMessagesReceiver;

    @BindView(R.id.date_icon) ImageView dateIcon;
    @BindView(R.id.register_btn) CircleButton registerBtn;
    @BindView(R.id.date_text) TextView dateText;
    @BindView(R.id.is_user_registered_btn) Button isUserRegistered;

    public View.OnClickListener REGISTER_BTN_LISTENER = (v) -> {
        String token = UserDataHelper.getToken(getActivity());
        // Get the root inflator.
        LayoutInflater baseInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = baseInflater.inflate(R.layout.activity_choice, null);
        RelativeLayout layout = (RelativeLayout) dialogView.findViewById(R.id.activity_choice);
        TextView textView = (TextView) dialogView.findViewById(R.id.textViewQuestion);
        CheckBox rememberLastChoice = (CheckBox) dialogView.findViewById(R.id.checkBoxChoice);

        boolean isUserRegistered = UserDataHelper.isUserRegistered(getActivity());
        layout.setBackgroundResource(isUserRegistered ? R.drawable.gradient_red : R.drawable.gradient_green);
        String text = getResources().getString(isUserRegistered ? R.string.dont_want_to_participate : R.string.want_to_participate);
        textView.setText(text);
        text = getResources().getString(isUserRegistered ? R.string.unregistered_for_ever : R.string.always_want_to_be_registered);
        rememberLastChoice.setText(text);

        Button buttonYes = (Button) dialogView.findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener((butYes) -> {
            boolean isRemember = rememberLastChoice.isChecked();
            UserDataHelper.setRememberLastChoice(getActivity(), isRemember);
            if (!isUserRegistered) {
                mMainPresenter.register(token, isRemember);
            } else {
                mMainPresenter.unregister(token, isRemember);
            }
        });
        Button buttonNo = (Button) dialogView.findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener((butNo) -> {
            dialog.dismiss();
        });

        dialog.setContentView(dialogView);
        dialog.show();
    };

    public static Fragment getInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.BUNDLE_DATE, date);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.attachView(this);

        startMessagesService();
    }

    private void startMessagesService() {
        mMessagesReceiver = new MessagesDataReceiver(new Handler(), this);
        Intent intent = MessagesService.createIntent(getActivity(), mMessagesReceiver);
        getActivity().startService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        dialog = new Dialog(getActivity(), Theme_Black_NoTitleBar_Fullscreen);

        IconicsDrawable fawCalendarTimesO = new IconicsDrawable(getContext(), "faw_calendar_times_o");
        fawCalendarTimesO.sizeDp(36);
        fawCalendarTimesO.colorRes(R.color.black);
        dateIcon.setImageDrawable(fawCalendarTimesO);

        Date date = (Date) getArguments().getSerializable(MainActivity.BUNDLE_DATE);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = df.format(date);
        dateText.setText(stringDate);

        initializeRegisterBtn();
        initializeIsRegisteredBtn();

        return view;
    }

    private void initializeIsRegisteredBtn() {
        Drawable backgroundIsUserRegistered;
        if (UserDataHelper.isUserRegistered(getActivity())) {
            backgroundIsUserRegistered = getResources().getDrawable(R.drawable.button_saved);
        } else {
            backgroundIsUserRegistered = getResources().getDrawable(R.drawable.button_notsaved);
        }
        isUserRegistered.setBackground(backgroundIsUserRegistered);
    }

    private void initializeRegisterBtn() {
        int color;
        if (UserDataHelper.isUserRegistered(getActivity())) {
            color = getResources().getColor(R.color.green);
        } else {
            color = getResources().getColor(R.color.red);
        }
        registerBtn.setColor(color);
        IconicsDrawable fawPowerOff = new IconicsDrawable(getActivity(), "faw_power_off");
        fawPowerOff.colorRes(R.color.white);
        fawPowerOff.sizeDp(60);
        registerBtn.setImageDrawable(fawPowerOff);
        registerBtn.setOnClickListener(REGISTER_BTN_LISTENER);
    }

    @Override
    public void userRegistered() {
        registerBtn.setColor(getResources().getColor(R.color.green));
        Drawable backgroundIsUserRegistered = getResources().getDrawable(R.drawable.button_saved);
        isUserRegistered.setBackground(backgroundIsUserRegistered);
        UserDataHelper.setUserIsRegistered(getActivity(), true);
        dialog.dismiss();
    }

    @Override
    public void userUnregistered() {
        registerBtn.setColor(getResources().getColor(R.color.red));
        Drawable backgroundIsUserRegistered = getResources().getDrawable(R.drawable.button_notsaved);
        isUserRegistered.setBackground(backgroundIsUserRegistered);
        UserDataHelper.setUserIsRegistered(getActivity(), false);
        dialog.dismiss();
    }

    @Override
    public void onRegisterFailed(String message) {
        dialog.dismiss();
    }

    @Override
    public void onUnRegisterFailed(String message) {
        dialog.dismiss();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

    }
}
