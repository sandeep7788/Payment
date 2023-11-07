package me.ibrahimsn.smoothbottombar.activity.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import me.ibrahimsn.smoothbottombar.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import me.ibrahimsn.smoothbottombar.helper.ApiInterface;
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager;
import me.ibrahimsn.smoothbottombar.model.tab.CategoryDetail;
import me.ibrahimsn.smoothbottombar.model.tab.Fulltt;
import me.ibrahimsn.smoothbottombar.model.tab.ModelRequestdetails;
import me.ibrahimsn.smoothbottombar.model.tab.Romaing;
import me.ibrahimsn.smoothbottombar.model.tab.Topup;
import me.ibrahimsn.smoothbottombar.model.tab._2g;
import me.ibrahimsn.smoothbottombar.model.tab._3g4g;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargePlaneFragment extends Fragment {

    private View view;
    public ArrayList<CategoryDetail> TitleName = new ArrayList<>();
    RechargePlaneAdapter arrayAdapter;
    ListView alertdialog_Listview;
    int tabid;
    String operatorId = "0";
    String circleId = "0";

    public RechargePlaneFragment(int tabid,String operatorId, String circleId) {
        this.tabid=tabid;
        this.operatorId=operatorId;
        this.circleId=circleId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recharge_plane_fragment, container, false);

        arrayAdapter=new RechargePlaneAdapter(getActivity(), TitleName);
        alertdialog_Listview=view.findViewById(R.id.alertdialog_Listview);

        getBlogTab();

        return view;
    }

    private void getBlogTab() {
//        Local_data pref= new Local_data(getContext());
        ApiInterface apiInterface = new RetrofitManager().getInstance().create(ApiInterface.class);
        Call<ModelRequestdetails> call = apiInterface.GetPlandetails(operatorId,circleId);
        call.enqueue(new Callback<ModelRequestdetails>() {
            @Override
            public void onResponse(Call<ModelRequestdetails> call, Response<ModelRequestdetails> response) {


                if (response.isSuccessful()) {
                    ModelRequestdetails childResult = response.body();


                    for (int j = 0; j < 5; j++) {

                        if (response.body().getInfo().getFulltt() != null && tabid==0) {
                            for (int k = 0; k < response.body().getInfo().getFulltt().size(); k++) {

                                Fulltt data = response.body().getInfo().getFulltt().get(k);
                                CategoryDetail obj = new CategoryDetail();
                                obj.setDesc(data.getDesc());
                                obj.setRs(data.getRs());
                                obj.setValidity(data.getValidity());
                                TitleName.add(obj);
                            }
                        }
                        if (response.body().getInfo().getTopup() != null&& tabid==1) {
                            for (int k = 0; k < response.body().getInfo().getTopup().size(); k++) {

                                Topup data = response.body().getInfo().getTopup().get(k);
                                CategoryDetail obj = new CategoryDetail();
                                obj.setDesc(data.getDesc());
                                obj.setRs(data.getRs());
                                obj.setValidity(data.getValidity());
                                TitleName.add(obj);
                            }
                        }
                        if (response.body().getInfo().get3g4g() != null&& tabid==2) {
                            for (int k = 0; k < response.body().getInfo().get3g4g().size(); k++) {

                                _3g4g data = response.body().getInfo().get3g4g().get(k);
                                CategoryDetail obj = new CategoryDetail();
                                obj.setDesc(data.getDesc());
                                obj.setRs(data.getRs());
                                obj.setValidity(data.getValidity());
                                TitleName.add(obj);
                            }
                        }
                        if (response.body().getInfo().get2g() != null && tabid==3) {
                            for (int k = 0; k < response.body().getInfo().get2g().size(); k++) {

                                    _2g data = response.body().getInfo().get2g().get(k);
                                    CategoryDetail obj = new CategoryDetail();
                                    obj.setDesc(data.getDesc());
                                    obj.setRs(data.getRs());
                                    obj.setValidity(data.getValidity());
                                    TitleName.add(obj);
                                }
                            }





                        if (response.body().getInfo().getRomaing() != null&& tabid==4) {
                            for (int k = 0; k < response.body().getInfo().getRomaing().size(); k++) {

                                    Romaing data = response.body().getInfo().getRomaing().get(k);
                                    CategoryDetail obj = new CategoryDetail();
                                    obj.setDesc(data.getDesc());
                                    obj.setRs(data.getRs());
                                    obj.setValidity(data.getValidity());
                                    TitleName.add(obj);
                                }
                            }




//                        TitleName.addAll(Arrays.asList(response.body().getInfo().get2g().get()));
                    }


                    alertdialog_Listview.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                    }
                }
            @Override
            public void onFailure(Call<ModelRequestdetails> call, Throwable t) {

            }
        });
    }
}