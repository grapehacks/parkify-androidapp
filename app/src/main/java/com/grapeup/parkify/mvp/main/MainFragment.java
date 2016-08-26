package com.grapeup.parkify.mvp.main;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grapeup.parkify.R;
import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.mvp.login.PingPresenter;
import com.grapeup.parkify.mvp.login.PingPresenterImpl;
import com.grapeup.parkify.mvp.login.PingView;
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
public final class MainFragment extends Fragment implements MainView, PingView {
    private MainPresenter mMainPresenter;
    private PingPresenter mPingPresenter;
    private Dialog dialog;
    private Date date;

    @BindView(R.id.date_icon) ImageView dateIcon;
    @BindView(R.id.register_btn) CircleButton registerBtn;
    @BindView(R.id.date_text) TextView dateText;
    @BindView(R.id.is_user_registered_btn) Button isUserRegistered;
    @BindView(R.id.synchronized_image) ImageView synchronizedIcon;


    public View.OnClickListener REGISTER_BTN_LISTENER = (v) -> {
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
            toggleSynchronizedIcon(isRemember);
            if (!isUserRegistered) {
                mMainPresenter.register(isRemember);
            } else {
                mMainPresenter.unregister(isRemember);
            }
        });
        Button buttonNo = (Button) dialogView.findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener((butNo) -> {
            dialog.dismiss();
        });

        dialog.setContentView(dialogView);
        dialog.show();
    };

    private void toggleSynchronizedIcon(boolean isRemember) {
        if (isRemember) {
            synchronizedIcon.setVisibility(View.VISIBLE);
            synchronizedIcon.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
        } else {
            synchronizedIcon.setVisibility(View.GONE);
            synchronizedIcon.setAnimation(null);
        }
    }

    public static Fragment getInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.attachView(this);
        mMainPresenter.attachApplication(getActivity().getApplication());

        mPingPresenter = new PingPresenterImpl();
        mPingPresenter.attachView(this);
        mPingPresenter.attachApplication(getActivity().getApplication());

        triggerMessagesService();
    }

    private void triggerMessagesService() {
        Context context = getContext();
        if (UserDataHelper.isUserRegistered(getActivity())) {
            MessagesService.scheduleAlarmForReceivingMessages(context);
        } else {
            MessagesService.cancelAlarmForReceivingMessages(context);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainPresenter.start();
        mPingPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        dialog = new Dialog(getActivity(), Theme_Black_NoTitleBar_Fullscreen);

        initializeViews();

        return view;
    }

    private void initializeViews() {
        initializeDate();
        initializeSynchronizeIcon();
        initializeRegisterBtn();
        initializeIsRegisteredBtn();
    }

    private void initializeDate() {
        IconicsDrawable fawCalendarTimesO = new IconicsDrawable(getContext(), "faw_calendar_times_o");
        fawCalendarTimesO.sizeDp(36);
        fawCalendarTimesO.colorRes(R.color.black);
        dateIcon.setImageDrawable(fawCalendarTimesO);

        setDate(date);
    }

    private void setDate(Date date) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate;
        if (date != null) {
            stringDate = df.format(date);
        } else {
            stringDate = "";
        }
        dateText.setText(stringDate);
    }

    private void initializeSynchronizeIcon() {
        IconicsDrawable fawRefresh = new IconicsDrawable(getContext(), "faw_refresh");
        fawRefresh.sizeDp(48);
        fawRefresh.colorRes(R.color.black);
        synchronizedIcon.setImageDrawable(fawRefresh);
        toggleSynchronizedIcon(UserDataHelper.isRememberLastChoice(getActivity()));
    }

    private void initializeIsRegisteredBtn() {
        Drawable backgroundIsUserRegistered;
        if (UserDataHelper.isUserRegistered(getActivity())) {
            backgroundIsUserRegistered = getResources().getDrawable(R.drawable.button_saved);
            isUserRegistered.setText(getString(R.string.button_saved));

        } else {
            backgroundIsUserRegistered = getResources().getDrawable(R.drawable.button_notsaved);
            isUserRegistered.setText(getString(R.string.button_not_saved));

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

    private void refreshState() {
        initializeViews();
        dialog.dismiss();

        triggerMessagesService();
    }

    @Override
    public void userRegistered() {
        refreshState();
    }

    @Override
    public void userUnregistered() {
        refreshState();
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
    public void onPingFailed(String message) {
    }

    @Override
    public void tokenIsValid(User user) {
        refreshState();
    }

    @Override
    public void tokenIsInvalid() {
    }

    @Override
    public void onPingCompleted() {
    }

    @Override
    public void setNextDrawDate(Date date) {
        this.date = date;
        setDate(date);
    }

}
