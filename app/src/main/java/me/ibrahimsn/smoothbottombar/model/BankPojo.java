package me.ibrahimsn.smoothbottombar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankPojo {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bank_name")
    @Expose
    private String bank_name;

    public BankPojo(String id, String bank_name) {
        this.id = id;
        this.bank_name = bank_name;
    }

    public BankPojo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
