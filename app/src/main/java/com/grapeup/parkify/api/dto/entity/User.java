package com.grapeup.parkify.api.dto.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Pavlo Tymchuk
 */
public class User implements Parcelable {
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

    private String id;
    private String email;
    private String name;
    private String password;
    private int unreadMessageCounter;
    private boolean rememberLastChoice;
    private int participate;
    private int type;

    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        name = in.readString();
        id = in.readString();
        password = in.readString();
        unreadMessageCounter = in.readInt();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUnreadMessageCounter() {
        return unreadMessageCounter;
    }

    public void setUnreadMessageCounter(int unreadMessageCounter) {
        this.unreadMessageCounter = unreadMessageCounter;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (unreadMessageCounter != user.unreadMessageCounter) return false;
        if (rememberLastChoice != user.rememberLastChoice) return false;
        if (participate != user.participate) return false;
        if (type != user.type) return false;
        if (!id.equals(user.id)) return false;
        if (!email.equals(user.email)) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + unreadMessageCounter;
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
                ", password='" + password + '\'' +
                ", unreadMessageCounter=" + unreadMessageCounter +
                ", rememberLastChoice=" + rememberLastChoice +
                ", participate=" + participate +
                ", type=" + type +
                '}';
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
        dest.writeString(password);
        dest.writeInt(unreadMessageCounter);
        dest.writeByte(rememberLastChoice ? (byte) 1: (byte) 0);
        dest.writeInt(participate);
        dest.writeInt(type);
    }
}
