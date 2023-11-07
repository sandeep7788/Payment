package me.ibrahimsn.smoothbottombar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CirclePojo {


    @SerializedName("circle_id")
    @Expose
    private String circle_id;
    @SerializedName("circle_name")
    @Expose
    private String circle_name;

    public CirclePojo(String circle_id, String circle_name) {
        this.circle_id = circle_id;
        this.circle_name = circle_name;
    }

    public String getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(String circle_id) {
        this.circle_id = circle_id;
    }

    public String getCircle_name() {
        return circle_name;
    }

    public void setCircle_name(String circle_name) {
        this.circle_name = circle_name;
    }
}
