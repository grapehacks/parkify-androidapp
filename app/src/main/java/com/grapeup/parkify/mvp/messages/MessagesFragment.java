package com.grapeup.parkify.mvp.messages;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grapeup.parkify.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesFragment extends Fragment implements MessagesContract.View {

    @BindView(R.id.messagesRecyclerView)
    RecyclerView messagesRecyclerView;

    List<MessageModel> mMessages = new ArrayList<>();
    MessagesContract.MessagesPresenter messagesPresenter;

    private void createMessages() {
        for (int i=0; i<30; i++) {
            mMessages.add(new MessageModelImpl(String.valueOf(i*10), String.valueOf(i), i%4));
        }
    }


    public static Fragment getInstance(String token) {
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        ButterKnife.bind(this, view);

        createMessages();
        String token = getArguments().getString("token");
        messagesRecyclerView.setAdapter(new MessagesAdapter(getActivity(), mMessages));
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        messagesPresenter = new MessagesPresenterImpl();
        messagesPresenter.attachView(this);
        messagesPresenter.setToken(token);
        messagesPresenter.start();
        return view;
    }

    @Override
    public void onMessagesReceived() {

    }
}
