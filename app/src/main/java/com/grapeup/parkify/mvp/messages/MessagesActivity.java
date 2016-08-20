package com.grapeup.parkify.mvp.messages;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.grapeup.parkify.R;
import com.grapeup.parkify.mvp.SingleFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    protected Fragment createFragment() {
        return MessagesFragment.getInstance(getIntent().getStringExtra("token"));
    }
}
