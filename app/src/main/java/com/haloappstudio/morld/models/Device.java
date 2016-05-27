package com.haloappstudio.morld.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by suheb on 27/5/16.
 */
public class Device {
    @SerializedName("id")
    int mId;

    @SerializedName("name")
    String mName;

    @SerializedName("status")
    Boolean mStatus;

    public Device(int mId, String mName, Boolean mStatus) {
        this.mId = mId;
        this.mName = mName;
        this.mStatus = mStatus;
    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public Boolean getmStatus() {
        return mStatus;
    }
}
