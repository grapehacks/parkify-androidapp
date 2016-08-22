package com.grapeup.parkify.mvp.messages;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grapeup.parkify.R;
import com.grapeup.parkify.api.dto.entity.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_message_title)
        TextView messageTitle;

        @BindView(R.id.item_message_body)
        TextView messageBody;

        @BindView(R.id.item_message_date)
        TextView messageDate;

        @BindView(R.id.item_message_layout)
        LinearLayout messageLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Message> mMessages = new ArrayList<>();
    private Context mContext;
    private MessagesContract.MessagesPresenter mPresenter;

    public MessagesAdapter(Context mContext, MessagesContract.MessagesPresenter presenter) {
        this.mContext = mContext;
        mPresenter = presenter;
    }

    public void setMessages(List<Message> messages) {
        mMessages = messages;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View messageView = inflater.inflate(R.layout.item_message, parent, false);

        ViewHolder viewHolder = new ViewHolder(messageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.messageTitle.setText(message.getTopic());
        holder.messageBody.setText(message.getText());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = df.format(message.getDate());
        holder.messageDate.setText(stringDate);
        switch (message.getType()) {
            case 0:
                holder.messageLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                break;
            case 1:
                holder.messageLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                break;
            case 2:
                holder.messageLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_light));
                break;
            case 3:
                holder.messageLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_light));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}
