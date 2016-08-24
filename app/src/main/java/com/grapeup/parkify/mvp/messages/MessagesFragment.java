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
import com.grapeup.parkify.tools.UserDataHelper;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, view);

        //TODO this recycler view should be refreshed

        String token = UserDataHelper.getToken(getActivity());
        int unreadCount = UserDataHelper.getUnreadCount(getActivity());
        messagesPresenter = new MessagesPresenterImpl();
        messagesPresenter.attachView(this);
        messagesPresenter.attachApplication(getActivity().getApplication());
        messagesPresenter.setToken(token);
        messagesPresenter.setUnreadCount(unreadCount);
        messagesPresenter.start();

        mMessagesAdapter = new MessagesAdapter(getActivity(), messagesPresenter);
        messagesRecyclerView.setAdapter(mMessagesAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onMessagesReceived(List<Message> messages) {
        //TODO remove
        //messages = UserDataHelper.generateMessages();
        if (!messages.isEmpty()) {
            mMessagesAdapter.setMessages(messages);
            Message lastMessage = messages.get(messages.size() - 1);
            UserDataHelper.setLastMessageTime(getActivity().getApplication(), lastMessage.getDate().getTime());
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
