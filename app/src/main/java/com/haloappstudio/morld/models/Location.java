package com.haloappstudio.morld.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by suheb on 27/5/16.
 */
public class Location implements Parcelable{

    @SerializedName("id")
    int mId;

    @SerializedName("name")
    String mName;

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public Location(int mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public Location(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {

        @Override
        public Location createFromParcel(Parcel parcel) {
            return new Location(parcel);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }

    };
}
