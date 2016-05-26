package com.haloappstudio.morld.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by suheb on 26/5/16.
 */
public class LoginResponse {

    @SerializedName("success")
    Boolean mSuccess;
    @SerializedName("details")
    User mUser;

    public LoginResponse(Boolean mSuccess, User user) {
        this.mSuccess = mSuccess;
        this.mUser = user;
    }

    public Boolean getmSuccess() {
        return mSuccess;
    }

    public User getmUser() {
        return mUser;
    }
}
