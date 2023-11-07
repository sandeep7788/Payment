package me.ibrahimsn.smoothbottombar.activity.moneytransfer;

import static com.example.awesomedialog.AwesomeDialogKt.title;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.awesomedialog.AwesomeDialog;

import java.util.ArrayList;

import me.ibrahimsn.smoothbottombar.R;
import me.ibrahimsn.smoothbottombar.helper.ApiContants;
import me.ibrahimsn.smoothbottombar.helper.MyApplication;
import me.ibrahimsn.smoothbottombar.model.tab.MoneyTransferPojo;


public class MoneyTransferAdapter extends RecyclerView.Adapter<MoneyTransferAdapter.ViewHolder>{




    ArrayList<MoneyTransferPojo> listarray=null;

    private LayoutInflater mInflater=null;
    OnDataPassListener2 onDataPassListener2;

    public MoneyTransferAdapter(Activity activty, ArrayList<MoneyTransferPojo> list,OnDataPassListener2 onDataPassListener2)

    {

        this.context=activty;

        mInflater = activty.getLayoutInflater();

        this.listarray=list;
        this.onDataPassListener2=onDataPassListener2;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_moneytransfer, parent, false);
        return new ViewHolder(view);
    }

    MoneyTransferPojo data;
    int pos;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        data = listarray.get(position);
        pos = position;
        holder.txtName.setText(data.getAccountHolderName());
        holder.txtAcNumber.setText(data.getAccountNo());
        holder.txtBankName.setText(data.getBankName());
        holder.txtIfscCode.setText(data.getIfsc());



        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataPassListener2.onDataPassed(data.getBenId(),pos,"delete",data);
            }
        });

        holder.txtTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataPassListener2.onDataPassed(data.getBenId(),pos,"transfer",data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listarray.size();
    }

    Context context=null;


    void deleteItem(int position) {
        listarray.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listarray.size());
//        holder.itemView.setVisibility(View.GONE);
    }

    public void deleteItem() {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Declaring view holder's
        // private member variables
        TextView txtName;
        TextView txtAcNumber;
        TextView txtBankName;
        TextView txtIfscCode;
        TextView txtDelete;
        TextView txtTransfer;
        CardView cardView2;

        // Constructor for ViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initializing the view holder's
            // private member variables
//            dsa = itemView.findViewById(R.id.dsa);



            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtDelete = (TextView) itemView.findViewById(R.id.txtDelete);
            txtAcNumber = (TextView) itemView.findViewById(R.id.txtAcNumber);
            txtBankName = (TextView) itemView.findViewById(R.id.txtBankName);
            txtIfscCode = (TextView) itemView.findViewById(R.id.txtIfscCode);
            txtTransfer = (TextView) itemView.findViewById(R.id.txtTransfer);
            cardView2 = itemView.findViewById(R.id.cardView2);


        }
    }

}