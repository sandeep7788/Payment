package me.ibrahimsn.smoothbottombar.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserPojo {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("role_id")
    @Expose
    private String roleId;
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
    private String eWalletBalance;
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
    @SerializedName("is_razorypay_active")
    @Expose
    private Integer isRazorypayActive;
    @SerializedName("user_aeps_status")
    @Expose
    private Integer userAepsStatus;
    @SerializedName("is_aeps_active")
    @Expose
    private Object isAepsActive;
    @SerializedName("user_icici_aeps_status")
    @Expose
    private Integer userIciciAepsStatus;
    @SerializedName("is_bbps_live_active")
    @Expose
    private Object isBbpsLiveActive;
    @SerializedName("razor_pay_key")
    @Expose
    private String razorPayKey;
    @SerializedName("razor_pay_secret")
    @Expose
    private String razorPaySecret;
    @SerializedName("news")
    @Expose
    private String news;
    @SerializedName("sliderData")
    @Expose
    private List<Object> sliderData;
    @SerializedName("successAmount")
    @Expose
    private String successAmount;
    @SerializedName("pendingAmount")
    @Expose
    private String pendingAmount;
    @SerializedName("failedAmount")
    @Expose
    private String failedAmount;
    @SerializedName("is_login")
    @Expose
    private Integer isLogin;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public String geteWalletBalance() {
        return eWalletBalance;
    }

    public void seteWalletBalance(String eWalletBalance) {
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

    public Integer getIsRazorypayActive() {
        return isRazorypayActive;
    }

    public void setIsRazorypayActive(Integer isRazorypayActive) {
        this.isRazorypayActive = isRazorypayActive;
    }

    public Integer getUserAepsStatus() {
        return userAepsStatus;
    }

    public void setUserAepsStatus(Integer userAepsStatus) {
        this.userAepsStatus = userAepsStatus;
    }

    public Object getIsAepsActive() {
        return isAepsActive;
    }

    public void setIsAepsActive(Object isAepsActive) {
        this.isAepsActive = isAepsActive;
    }

    public Integer getUserIciciAepsStatus() {
        return userIciciAepsStatus;
    }

    public void setUserIciciAepsStatus(Integer userIciciAepsStatus) {
        this.userIciciAepsStatus = userIciciAepsStatus;
    }

    public Object getIsBbpsLiveActive() {
        return isBbpsLiveActive;
    }

    public void setIsBbpsLiveActive(Object isBbpsLiveActive) {
        this.isBbpsLiveActive = isBbpsLiveActive;
    }

    public String getRazorPayKey() {
        return razorPayKey;
    }

    public void setRazorPayKey(String razorPayKey) {
        this.razorPayKey = razorPayKey;
    }

    public String getRazorPaySecret() {
        return razorPaySecret;
    }

    public void setRazorPaySecret(String razorPaySecret) {
        this.razorPaySecret = razorPaySecret;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public List<Object> getSliderData() {
        return sliderData;
    }

    public void setSliderData(List<Object> sliderData) {
        this.sliderData = sliderData;
    }

    public String getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(String successAmount) {
        this.successAmount = successAmount;
    }

    public String getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(String pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getFailedAmount() {
        return failedAmount;
    }

    public void setFailedAmount(String failedAmount) {
        this.failedAmount = failedAmount;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }
}
