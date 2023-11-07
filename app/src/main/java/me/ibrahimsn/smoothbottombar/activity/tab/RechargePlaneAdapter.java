package me.ibrahimsn.smoothbottombar.activity.tab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.ibrahimsn.smoothbottombar.R;
import me.ibrahimsn.smoothbottombar.helper.ApiContants;
import me.ibrahimsn.smoothbottombar.helper.MyApplication;
import me.ibrahimsn.smoothbottombar.model.tab.CategoryDetail;
import me.ibrahimsn.smoothbottombar.model.tab.Fulltt;


public class RechargePlaneAdapter extends BaseAdapter{


    Context ctx=null;

    ArrayList<CategoryDetail> listarray=null;

    private LayoutInflater mInflater=null;

    public RechargePlaneAdapter(Activity activty, ArrayList<CategoryDetail> list)

    {

        this.ctx=activty;

        mInflater = activty.getLayoutInflater();

        this.listarray=list;

    }

    @Override

    public int getCount() {



        return listarray.size();

    }


    @Override

    public Object getItem(int arg0) {

        return null;

    }


    @Override

    public long getItemId(int arg0) {

        return 0;

    }

    Context context=null;
    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {

        final ViewHolder holder;

        if (convertView == null ) {
//            context = convertView.getContext();

            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.operator_plane_adapter, null);




            holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
            holder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
            holder.txt_description = (TextView) convertView.findViewById(R.id.txt_description);
            holder.layout_item = convertView.findViewById(R.id.layout_item);
            convertView.setTag(holder);

        }

        else {

            holder = (ViewHolder) convertView.getTag();
        }

        Log.e(">>",listarray.get(position).getRs().toString()+" _"+String.valueOf(position));
        String datavalue=listarray.get(position).getRs().toString();


        holder.txt_amount.setText("₹  "+listarray.get(position).getRs().toString());
        holder.txt_title.setText(listarray.get(position).getDesc().toString());
        holder.txt_description.setText("Validity :- "+listarray.get(position).getValidity().toString());
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ctx, Rech.class);
//                intent.putExtra("amt",holder.txt_amount.getText().toString());
//                Local_data pref = new Local_data(ctx);
                MyApplication obj = new MyApplication();
                obj.getSharedPreferences(ctx);

                String amount="";
                if (holder.txt_amount.getText().toString().contains("₹")) {
                    amount = (holder.txt_amount.getText().toString().replace("₹","")).trim();
                } else {
                    amount = holder.txt_amount.getText().toString().trim();
                }


                obj.writeStringPreference(ApiContants.PREF_TAB,amount);
////                intent.putExtra("op_code",listarray.get(position).get.toString());
//                ctx.startActivity(intent);
                ((Activity)ctx).finish();
            }
        });

/*        Glide.with(ctx)
                .load(listarray.get(position).getPrice())
                .thumbnail(0.5f)
                .into(holder.icon);*/

        Log.e(">>url",listarray.get(position).getRs()+" _");

        return convertView;

    }



    private static class ViewHolder {

        TextView txt_amount;
        TextView txt_title;
        TextView txt_description;
        LinearLayout layout_item;

    }

}