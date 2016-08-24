package com.grapeup.parkify.api.dto.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.grapeup.parkify.api.dto.BaseDto;

import java.util.Date;

public class Message extends BaseDto implements Parcelable {

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    private String text;
    private String topic;
    private int type;
    private boolean read;
    private Date date;

    public Message(){}

    protected Message(Parcel in) {
        text = in.readString();
        topic = in.readString();
        type = in.readInt();
        read = in.readByte() == 1;
        date = new Date(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(topic);
        parcel.writeInt(type);
        parcel.writeByte(read ? (byte) 1 : (byte) 0);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (type != message.type) return false;
        if (read != message.read) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        if (topic != null ? !topic.equals(message.topic) : message.topic != null) return false;
        return date != null ? date.equals(message.date) : message.date == null;

    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (read ? 1 : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", topic='" + topic + '\'' +
                ", type=" + type +
                ", read=" + read +
                ", date=" + date +
                '}';
    }
}
