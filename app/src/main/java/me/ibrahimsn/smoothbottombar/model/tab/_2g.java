
package me.ibrahimsn.smoothbottombar.model.tab;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class _2g {

    @SerializedName("rs")
    @Expose
    private String rs;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
