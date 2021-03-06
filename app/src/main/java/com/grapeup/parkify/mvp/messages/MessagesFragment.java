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
import com.grapeup.parkify.api.dto.entity.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesFragment extends Fragment implements MessagesContract.View {

    @BindView(R.id.messagesRecyclerView)
    RecyclerView messagesRecyclerView;

    MessagesContract.MessagesPresenter messagesPresenter;
    private MessagesAdapter mMessagesAdapter;

    public static Fragment getInstance() {
        Bundle bundle = new Bundle();
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messagesPresenter = new MessagesPresenterImpl();
        messagesPresenter.attachView(this);
        messagesPresenter.attachApplication(getActivity().getApplication());
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO this recycler view should be refreshed
        messagesPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        messagesPresenter.detachView();
        messagesPresenter.detachApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, view);

        mMessagesAdapter = new MessagesAdapter(getActivity(), messagesPresenter);
        messagesRecyclerView.setAdapter(mMessagesAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onMessagesReceived(List<Message> messages) {
        if (!messages.isEmpty()) {
            mMessagesAdapter.setMessages(messages);
        }
    }

    @Override
    public void onMessagesReceiveError(String message) {

    }

    @Override
    public void onMessagesReceiveCompleted() {
        mMessagesAdapter.notifyDataSetChanged();
    }
}
