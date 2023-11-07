package me.ibrahimsn.smoothbottombar.model.bbpsServiceModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("biller_id")
    @Expose
    private Integer billerId;
    @SerializedName("billerName")
    @Expose
    private String billerName;
    @SerializedName("billerAliasName")
    @Expose
    private String billerAliasName;
    @SerializedName("is_fetch")
    @Expose
    private Integer isFetch;

    public Integer getBillerId() {
        return billerId;
    }

    public void setBillerId(Integer billerId) {
        this.billerId = billerId;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public String getBillerAliasName() {
        return billerAliasName;
    }

    public void setBillerAliasName(String billerAliasName) {
        this.billerAliasName = billerAliasName;
    }

    public Integer getIsFetch() {
        return isFetch;
    }

    public void setIsFetch(Integer isFetch) {
        this.isFetch = isFetch;
    }

}