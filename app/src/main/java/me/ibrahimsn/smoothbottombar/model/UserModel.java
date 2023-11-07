package me.ibrahimsn.smoothbottombar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("is_otp")
    @Expose
    private Integer isOtp;
    @SerializedName("user_detail")
    @Expose
    private UserTwoPojo userDetail;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIsOtp() {
        return isOtp;
    }

    public void setIsOtp(Integer isOtp) {
        this.isOtp = isOtp;
    }

    public UserTwoPojo getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserTwoPojo userDetail) {
        this.userDetail = userDetail;
    }


}