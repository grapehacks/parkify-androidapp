package com.grapeup.parkify.mvp.choice;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grapeup.parkify.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoiceActivity extends AppCompatActivity {

    @BindView(R.id.activity_choice)
    RelativeLayout layout;

    @BindView(R.id.buttonYes)
    Button buttonYes;

    @BindView(R.id.textViewQuestion)
    TextView question;

    @BindView(R.id.checkBoxChoice)
    CheckBox choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonNo)
    public void onClickNo() {
        layout.setBackgroundResource(R.drawable.gradient_red);
        buttonYes.setTextColor(ContextCompat.getColor(this, R.color.red_light));
        Toast.makeText(this, "red", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonYes)
    void onClickYes() {
        layout.setBackgroundResource(R.drawable.gradient_green);
        buttonYes.setTextColor(ContextCompat.getColor(this, R.color.green_light));
        Toast.makeText(this, "green", Toast.LENGTH_SHORT).show();
    }
}
