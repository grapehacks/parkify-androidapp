package com.grapeup.parkify.api.dto.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.grapeup.parkify.api.dto.BaseDto;

/**
 * @author Pavlo Tymchuk
 */
public class User extends BaseDto implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("numberOfAttempts")
    @Expose
    private int numberOfAttempts;
    @SerializedName("numberOfWins")
    @Expose
    private int numberOfWins;
    @SerializedName("unreadMsgCounter")
    @Expose
    private int unreadMsgCounter;
    @SerializedName("removed")
    @Expose
    private boolean removed;
    @SerializedName("rememberLastChoice")
    @Expose
    private boolean rememberLastChoice;
    @SerializedName("participate")
    @Expose
    private int participate;
    @SerializedName("type")
    @Expose
    private int type;

    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        name = in.readString();
        id = in.readString();
        numberOfAttempts = in.readInt();
        numberOfWins = in.readInt();
        unreadMsgCounter = in.readInt();
        removed = in.readByte() == 1;
        rememberLastChoice = in.readByte() == 1;
        participate = in.readInt();
        type = in.readInt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnreadMsgCounter() {
        return unreadMsgCounter;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(int numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public void setUnreadMsgCounter(int unreadMsgCounter) {
        this.unreadMsgCounter = unreadMsgCounter;
    }

    public boolean isRememberLastChoice() {
        return rememberLastChoice;
    }

    public void setRememberLastChoice(boolean rememberLastChoice) {
        this.rememberLastChoice = rememberLastChoice;
    }

    public int getParticipate() {
        return participate;
    }

    public void setParticipate(int participate) {
        this.participate = participate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeInt(numberOfAttempts);
        dest.writeInt(numberOfWins);
        dest.writeInt(unreadMsgCounter);
        dest.writeByte(removed ? (byte) 1: (byte) 0);
        dest.writeByte(rememberLastChoice ? (byte) 1: (byte) 0);
        dest.writeInt(participate);
        dest.writeInt(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (numberOfAttempts != user.numberOfAttempts) return false;
        if (numberOfWins != user.numberOfWins) return false;
        if (unreadMsgCounter != user.unreadMsgCounter) return false;
        if (removed != user.removed) return false;
        if (rememberLastChoice != user.rememberLastChoice) return false;
        if (participate != user.participate) return false;
        if (type != user.type) return false;
        if (!id.equals(user.id)) return false;
        if (!email.equals(user.email)) return false;
        return name.equals(user.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + numberOfAttempts;
        result = 31 * result + numberOfWins;
        result = 31 * result + unreadMsgCounter;
        result = 31 * result + (removed ? 1 : 0);
        result = 31 * result + (rememberLastChoice ? 1 : 0);
        result = 31 * result + participate;
        result = 31 * result + type;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", numberOfAttempts=" + numberOfAttempts +
                ", numberOfWins=" + numberOfWins +
                ", unreadMsgCounter=" + unreadMsgCounter +
                ", removed=" + removed +
                ", rememberLastChoice=" + rememberLastChoice +
                ", participate=" + participate +
                ", type=" + type +
                '}';
    }
}
