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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_message_title)
        TextView messageTitle;

        @BindView(R.id.item_message_body)
        TextView messageBody;

        @BindView(R.id.item_message_layout)
        LinearLayout messageLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<MessageModel> mMessages;
    private Context mContext;

    public MessagesAdapter(Context mContext, List<MessageModel> mMessages) {
        this.mMessages = mMessages;
        this.mContext = mContext;
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
        MessageModel messageModel = mMessages.get(position);
        holder.messageTitle.setText(messageModel.getTitle());
        holder.messageBody.setText((messageModel.getBody()));
        switch (messageModel.getType()) {
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
