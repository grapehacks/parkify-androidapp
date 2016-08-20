package com.grapeup.parkify.api.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.grapeup.parkify.api.dto.entity.User;

/**
 * @author Pavlo Tymchuk
 */
public class UserDto extends BaseDto implements Parcelable {
    public static final Creator<UserDto> CREATOR = new Creator<UserDto>() {
        @Override
        public UserDto createFromParcel(Parcel in) {
            return new UserDto(in);
        }

        @Override
        public UserDto[] newArray(int size) {
            return new UserDto[size];
        }
    };

    @SerializedName("items")
    @Expose
    private String message;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private User user;

    protected UserDto(Parcel in) {
        message = in.readString();
        token = in.readString();
        user = in.readParcelable(getClass().getClassLoader());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

        UserDto userDto = (UserDto) o;

        if (!message.equals(userDto.message)) return false;
        if (token != null ? !token.equals(userDto.token) : userDto.token != null) return false;
        return user != null ? user.equals(userDto.user) : userDto.user == null;

    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(token);
        dest.writeParcelable(user, flags);
    }
}
