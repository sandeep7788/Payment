package me.ibrahimsn.smoothbottombar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorPojo {


    @SerializedName("operator_id")
    @Expose
    private String operator_id;
    @SerializedName("operator_name")
    @Expose
    private String operator_name;

    public OperatorPojo(String operator_id, String operator_name) {
        this.operator_id = operator_id;
        this.operator_name = operator_name;
    }

    public OperatorPojo() {

    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }
}
