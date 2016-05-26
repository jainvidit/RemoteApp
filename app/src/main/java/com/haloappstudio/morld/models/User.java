package com.haloappstudio.morld.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by suheb on 26/5/16.
 */
public class User implements Parcelable{

    @SerializedName("user_id")
    int mId;

    @SerializedName("user_email")
    String mEmail;

    @SerializedName("user_name")
    String mName;

    public int getmId() {
        return mId;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmName() {
        return mName;
    }

    public User(int id, String name, String email ) {
        this.mId = id;
        this.mEmail = email;
        this.mName = name;

    }
    public User(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mEmail = parcel.readString();
        this.mName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mEmail);
        parcel.writeString(this.mName);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }

    };

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mEmail='" + mEmail + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
