
package me.ibrahimsn.smoothbottombar.model.tab;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Info {

    @SerializedName("FULLTT")
    @Expose
    private List<Fulltt> fulltt;
    @SerializedName("TOPUP")
    @Expose
    private List<Topup> topup;
    @SerializedName("3G/4G")
    @Expose
    private List<me.ibrahimsn.smoothbottombar.model.tab._3g4g> _3g4g;
    @SerializedName("2G")
    @Expose
    private List<me.ibrahimsn.smoothbottombar.model.tab._2g> _2g;
    @SerializedName("Romaing")
    @Expose
    private List<Romaing> romaing;

    public List<Fulltt> getFulltt() {
        return fulltt;
    }

    public void setFulltt(List<Fulltt> fulltt) {
        this.fulltt = fulltt;
    }

    public List<Topup> getTopup() {
        return topup;
    }

    public void setTopup(List<Topup> topup) {
        this.topup = topup;
    }

    public List<me.ibrahimsn.smoothbottombar.model.tab._3g4g> get3g4g() {
        return _3g4g;
    }

    public void set3g4g(List<me.ibrahimsn.smoothbottombar.model.tab._3g4g> _3g4g) {
        this._3g4g = _3g4g;
    }

    public List<me.ibrahimsn.smoothbottombar.model.tab._2g> get2g() {
        return _2g;
    }

    public void set2g(List<me.ibrahimsn.smoothbottombar.model.tab._2g> _2g) {
        this._2g = _2g;}




    public List<Romaing> getRomaing() {
        return romaing;
    }

    public void setRomaing(List<Romaing> romaing) {
        this.romaing = romaing;
    }

}
