package com.grapeup.parkify.mvp.messages;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.grapeup.parkify.R;
import com.grapeup.parkify.api.dto.entity.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_message_icon)
        ImageView icon;

        @BindView(R.id.item_message_title)
        TextView messageTitle;

        @BindView(R.id.item_message_body)
        TextView messageBody;

        @BindView(R.id.item_message_date)
        TextView messageDate;

        @BindView(R.id.item_message_layout)
        RelativeLayout messageLayout;

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
        holder.messageLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_lighter));

        holder.messageLayout.setOnClickListener(view -> {
            mPresenter.readMessage(message, new MessagesContract.MessageReadResultHandler() {
                @Override
                public void success() {
                    TypedArray array = mContext.getTheme().obtainStyledAttributes(new int[] {
                            android.R.attr.colorBackground,
                    });
                    int backgroundColor = array.getColor(0, 0xFF00FF);
                    view.setBackgroundColor(backgroundColor);
                }

                @Override
                public void failed(Throwable e) {
                    holder.messageLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_lighter));
                }
            });
        });

        int color = getColorByMessageType(message.getType());

        TextDrawable drawable = TextDrawable.builder().buildRound(message.getTopic().substring(0, 1), color);
        holder.icon.setImageDrawable(drawable);
    }

    private int getColorByMessageType(int messageType) {
        int color = 0;
        switch (messageType) {
            case 0:
                color = getContext().getResources().getColor(R.color.green);
                break;
            case 1:
                color = getContext().getResources().getColor(R.color.red);
                break;
            case 2:
                color = getContext().getResources().getColor(R.color.green_light);
                break;
            case 3:
                color = getContext().getResources().getColor(R.color.red_light);
                break;
        }
        return color;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }


}
