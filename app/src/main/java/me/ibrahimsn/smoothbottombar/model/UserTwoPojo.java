package me.ibrahimsn.smoothbottombar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTwoPojo {

    @SerializedName("account_id")
    @Expose
    private String accountId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_code")
    @Expose
    private String userCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;
    @SerializedName("e_wallet_balance")
    @Expose
    private Integer eWalletBalance;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("fcm_id")
    @Expose
    private String fcmId;
    @SerializedName("is_recharge_active")
    @Expose
    private Integer isRechargeActive;
    @SerializedName("is_transfer_active")
    @Expose
    private Integer isTransferActive;
    @SerializedName("is_aeps_active")
    @Expose
    private Integer isAepsActive;
    @SerializedName("is_bbps_live_active")
    @Expose
    private Integer isBbpsLiveActive;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Integer geteWalletBalance() {
        return eWalletBalance;
    }

    public void seteWalletBalance(Integer eWalletBalance) {
        this.eWalletBalance = eWalletBalance;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public Integer getIsRechargeActive() {
        return isRechargeActive;
    }

    public void setIsRechargeActive(Integer isRechargeActive) {
        this.isRechargeActive = isRechargeActive;
    }

    public Integer getIsTransferActive() {
        return isTransferActive;
    }

    public void setIsTransferActive(Integer isTransferActive) {
        this.isTransferActive = isTransferActive;
    }

    public Integer getIsAepsActive() {
        return isAepsActive;
    }

    public void setIsAepsActive(Integer isAepsActive) {
        this.isAepsActive = isAepsActive;
    }

    public Integer getIsBbpsLiveActive() {
        return isBbpsLiveActive;
    }

    public void setIsBbpsLiveActive(Integer isBbpsLiveActive) {
        this.isBbpsLiveActive = isBbpsLiveActive;
    }

}
