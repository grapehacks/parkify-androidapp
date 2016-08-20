package com.grapeup.parkify.api.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.grapeup.parkify.api.dto.entity.User;

import java.util.Date;

/**
 * @author Pavlo Tymchuk
 */
public class PingDto extends BaseDto implements Parcelable{
    public static final Parcelable.Creator<PingDto> CREATOR = new Parcelable.Creator<PingDto>() {
        @Override
        public PingDto createFromParcel(Parcel in) {
            return new PingDto(in);
        }

        @Override
        public PingDto[] newArray(int size) {
            return new PingDto[size];
        }
    };

    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("user")
    @Expose
    private User user;

    protected PingDto(Parcel in) {
        date = new Date(in.readLong());
        user = in.readParcelable(getClass().getClassLoader());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PingDto pingDto = (PingDto) o;

        if (!date.equals(pingDto.date)) return false;
        return user != null ? user.equals(pingDto.user) : pingDto.user == null;

    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PingDto{" +
                "date=" + date +
                ", user=" + user +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date.getTime());
        dest.writeParcelable(user, flags);
    }
}
