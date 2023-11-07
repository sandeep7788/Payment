
package me.ibrahimsn.smoothbottombar.model.tab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MoneyTransferPojo {

    @SerializedName("benId")
    @Expose
    private String benId;
    @SerializedName("account_holder_name")
    @Expose
    private String accountHolderName;
    @SerializedName("verifiedName")
    @Expose
    private Object verifiedName;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("ben_mobile")
    @Expose
    private String benMobile;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("is_verify")
    @Expose
    private String isVerify;
    @SerializedName("date")
    @Expose
    private String date;

    public MoneyTransferPojo(String benId, String accountHolderName, Object verifiedName, String accountNo, String benMobile, String bankName, String ifsc, String isVerify, String date) {
        this.benId = benId;
        this.accountHolderName = accountHolderName;
        this.verifiedName = verifiedName;
        this.accountNo = accountNo;
        this.benMobile = benMobile;
        this.bankName = bankName;
        this.ifsc = ifsc;
        this.isVerify = isVerify;
        this.date = date;
    }

    public String getBenId() {
        return benId;
    }

    public void setBenId(String benId) {
        this.benId = benId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Object getVerifiedName() {
        return verifiedName;
    }

    public void setVerifiedName(Object verifiedName) {
        this.verifiedName = verifiedName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBenMobile() {
        return benMobile;
    }

    public void setBenMobile(String benMobile) {
        this.benMobile = benMobile;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(String isVerify) {
        this.isVerify = isVerify;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
