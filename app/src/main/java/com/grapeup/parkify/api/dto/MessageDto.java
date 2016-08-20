package com.grapeup.parkify.api.dto;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.grapeup.parkify.api.dto.entity.Message;

import java.util.Date;

public class MessageDto extends BaseDto implements Parcelable{
    protected MessageDto(Parcel in) {
        messages = (Message[])in.readParcelableArray(Message.class.getClassLoader());
    }

    public static final Creator<MessageDto> CREATOR = new Creator<MessageDto>() {
        @Override
        public MessageDto createFromParcel(Parcel in) {
            return new MessageDto(in);
        }

        @Override
        public MessageDto[] newArray(int size) {
            return new MessageDto[size];
        }
    };

    @Expose
    private Message[] messages;

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableArray(messages, i);
    }
}
