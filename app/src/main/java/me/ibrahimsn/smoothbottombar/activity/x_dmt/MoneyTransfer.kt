package me.ibrahimsn.smoothbottombar.activity.x_dmt


import android.R.attr
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onNegative
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.gson.JsonObject
import me.ibrahimsn.smoothbottombar.MainActivity
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.activity.OnDataPassListener
import me.ibrahimsn.smoothbottombar.databinding.ActivityMoneytransferBinding
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.ApiInterface
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.tab.MoneyTransferPojo
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoneyTransfer : AppCompatActivity(), OnDataPassListener2 {
    lateinit var context: Context
    lateinit var binding : ActivityMoneytransferBinding
    private var progressDialog: SweetAlertDialog? = null
    private var TAG = "@@MoneyTransfer"
    var list = ArrayList<MoneyTransferPojo>()
    var arrayAdapter: MoneyTransferAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moneytransfer)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_moneytransfer)
        context = this@MoneyTransfer
        progressDialog = SweetAlertDialog(this@MoneyTransfer, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog!!.progressHelper.barColor = R.color.theme_color
        progressDialog!!.titleText = "Loading ..."
        progressDialog!!.setCancelable(false)

        binding.toolbarLayout.back.setOnClickListener {
            finish()
        }

        setData()



//        arrayAdapter = MoneyTransferAdapter(this@MoneyTransfer, TitleName)



        binding.layoutAddBene.setOnClickListener {
            startActivityForResult(Intent(this@MoneyTransfer, AddBeneficiary::class.java),101)
        }


//        binding.alertdialogListview.setAdapter(arrayAdapter)
//        arrayAdapter!!.notifyDataSetChanged()

        arrayAdapter = MoneyTransferAdapter(this@MoneyTransfer, list,this)
        binding.alertdialogListview.adapter = arrayAdapter
        val layoutManager = LinearLayoutManager(this@MoneyTransfer, LinearLayoutManager.VERTICAL, false)

        binding.alertdialogListview.layoutManager=layoutManager;
//        val resId: Int = me.ibrahimsn.smoothbottombar.R.anim.layout_animation_fall_down
//        val animation: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, resId)
//        binding.alertdialogListview.setLayoutAnimation(animation)
//        arrayAdapter?.notifyDataSetChanged()



        dmtBeneficiaryList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 101) {
            if (resultCode === RESULT_OK) {
                dmtBeneficiaryList()
            }
        }
    }


    private fun dmtBeneficiaryDeleteAuth() {
//        progressDialog!!.show()
        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(this@MoneyTransfer)?.create(ApiInterface::class.java)

        apiInterface!!.xdmtBeneficiaryDeleteAuth(
            userId?.trim(),
            benId,
            MyApplication.ReadStringPreferences(ApiContants.PREF_REME),
        ).enqueue(object :
            Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                if (Utility.isNetworkAvailable(context)) {
                    Toast.makeText(
                        context,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    Log.e(TAG, "onFailure: " + t.message)
                }
                progressDialog!!.dismiss()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                progressDialog!!.dismiss()
                var msg: String? = null;
                try {
                    if(response.isSuccessful) {



                        val jsonObject= JSONObject(response.body().toString().trim())

//                    Toast.makeText(context,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                        var msg = "";
                        if (jsonObject.getString("message") != null) {
                            msg = jsonObject.getString("message");
                        }
                        Utility.showSnackBar(this@MoneyTransfer,msg);
                        if(jsonObject.getInt("status")==1){
                            arrayAdapter?.deleteItem(pos)


//                            AwesomeDialog.build(this@MoneyTransfer)
//                                .title("Updated")
//                                .body(msg)
//                                .icon(R.drawable.ic_congrts)
////                            .background(R.drawable.green_button_background)
//                                .onPositive("Back To Home") {
//                                    Log.d("TAG", "positive ")
//                                    this@MoneyTransfer?.startActivity(Intent(this@MoneyTransfer,MainActivity::class.java))
//                                    finish()
//                                }

                        } else {
//                        msg = "Bad Response when get balance.";
                            AwesomeDialog.build(this@MoneyTransfer!!)
                                .title("Something Wrong")
                                .body(msg)
//                            .background(R.drawable.edit_txt_error)
//                            .icon(R.drawable.baseline_error_outline_24)
                                .onPositive("Retry") {
                                    Log.d("TAG", "positive ")
                                }
                        }





                    } else {
                        Utility.showDialog(
                            context,
                            SweetAlertDialog.WARNING_TYPE,
                            resources.getString(R.string.error)
                        )
                    }

                } catch (e: Exception) {
                    Utility.showDialog(
                        context,
                        SweetAlertDialog.WARNING_TYPE,
                        resources.getString(R.string.error)
                    )

                    Toast.makeText(
                        context,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

        })
    }



    fun dmtBeneficiaryList()     {
        progressDialog?.show()
        list.clear()

        var apiInterface: ApiInterface = context?.let { RetrofitManager().instance }!!.create(ApiInterface::class.java)

        apiInterface.xdmtBeneficiaryList(
            userId?.toString(),
            MyApplication.ReadStringPreferences(ApiContants.PREF_REME)?.trim()
        ).
        enqueue(object : Callback<JsonObject>
        {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context," "+t.message.toString(), Toast.LENGTH_LONG).show()
                progressDialog?.dismiss()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                progressDialog?.dismiss()
                if(response.isSuccessful) {


                    Log.d(TAG, "onResponse: "+response.toString())
                    Log.d(TAG, "onResponse: "+response.body().toString())

                    val jsonObject= JSONObject(response.body().toString())

//                    Toast.makeText(context,jsonObject.getString("message"), Toast.LENGTH_LONG).show()
                    if(jsonObject.getInt("status")==1){

                        if(jsonObject.has("data") && !jsonObject.isNull("data") ) {
                            val json_Array: JSONArray = jsonObject.getJSONArray("data")

                            for (i in 0 until json_Array.length()) {
//                                data.operator_id = (json_Array.getJSONObject(i).getString("operator_id"))
//                                data.operator_name = (json_Array.getJSONObject(i).getString("operator_name"))
                                list.add(
                                    MoneyTransferPojo(
                                        json_Array.getJSONObject(i).getString("benId"),
                                        json_Array.getJSONObject(i).getString("account_holder_name"),
                                        json_Array.getJSONObject(i).getString("verifiedName"),
                                        json_Array.getJSONObject(i).getString("account_no"),
                                        json_Array.getJSONObject(i).getString("ben_mobile"),
                                        json_Array.getJSONObject(i).getString("bank_name"),
                                        json_Array.getJSONObject(i).getString("ifsc"),
                                        json_Array.getJSONObject(i).getString("is_verify"),
                                        json_Array.getJSONObject(i).getString("date"),
                                    )
                                );
                            }
                            arrayAdapter?.notifyDataSetChanged()

                        }else{
//                            Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
//                        Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                    }
                }else{

                    Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                }

            }

        })
    }


    var userId="";

    fun setData() {


        try {
//            var prefData = MyApplication.ReadStringPreferences(ApiContants.PREF_USER);
//            val gson = Gson()
//
//            // Convert JSON to UserPojo
//
//            // Convert JSON to UserPojo
//            val pref: UserPojo = gson.fromJson(prefData, UserPojo::class.java)

            binding.toolbarLayout.texttitle.text="Xpress Money Transfer"
            userId = MyApplication.getUserId().toString();

            binding.cardName.setText(MyApplication.ReadStringPreferences(ApiContants.PREF_DMT_NAME))
            binding.cardAvailable.setText(MyApplication.ReadStringPreferences(ApiContants.PREF_DMT_AVAILABLE_LIMIT))
            binding.cardTotalLimit.setText(MyApplication.ReadStringPreferences(ApiContants.PREF_DMT_TOTAL_LIMIT))
            binding.cardMobileNumber.setText(MyApplication.ReadStringPreferences(ApiContants.PREF_BEN_NUM))

            binding.cardLogout.setOnClickListener {
                AwesomeDialog.build(this@MoneyTransfer)
                    .title("Logout")
                    .body("Are you sure you want to do logout from DMT.")
                    .icon(R.drawable.baseline_error_outline_24)
//                            .background(R.drawable.green_button_background)
                    .onPositive("Logout") {

                        startActivity(Intent(this@MoneyTransfer,DmtLoginAuthActivity::class.java))
                        finish()
                    }
                    .onNegative("Cancel") {

                    }
            }


        } catch (e:Exception) {

        }
    }

    var benId="";
    var pos=0;
    override fun onDataPassed(id: String,pos:Int,type:String,data:MoneyTransferPojo) {

        if (type.equals("delete")) {
            benId=id;
            this.pos=pos;
            AwesomeDialog.build(this@MoneyTransfer)
                .title("Delete")
                .body("Are you sure you want to do Delete Beneficiary.")
//            .icon(R.drawable.baseline_error_outline_24)
//                            .background(R.drawable.green_button_background)
                .onPositive("Delete") {
                    dmtBeneficiaryDeleteAuth()
                }
                .onNegative("Cancel") {

                }
        } else {

            benId = data.benId;
            showDialog1(
                "Transfer To "+data.accountHolderName,
                "A/c: "+data.accountNo,
                "Bank: "+data.bankName,
                )
        }


    }
    private fun dmtTransferAuth(
        benId: String,
        amount: String
    ) {
        progressDialog!!.show()
        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(this@MoneyTransfer)?.create(ApiInterface::class.java)

        Log.e(TAG, "dmtTransferAuth: "+
                userId?.trim()+ " "+
            benId+ " "+
            amount+ " "+
            "1")
        apiInterface!!.xdmtTransferAuth(
            userId?.trim(),
            benId,
            amount,
            "1",
        ).enqueue(object :
            Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                if (Utility.isNetworkAvailable(context)) {
                    Toast.makeText(
                        context,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    Log.e(TAG, "onFailure: " + t.message)
                }
                progressDialog!!.dismiss()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                progressDialog!!.dismiss()
                var msg: String? = null;
                try {
                    if(response.isSuccessful) {



                        val jsonObject= JSONObject(response.body().toString().trim())

//                    Toast.makeText(context,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                        var msg = "";
                        if (jsonObject.getString("message") != null) {
                            msg = jsonObject.getString("message");
                        }
                        Utility.showSnackBar(this@MoneyTransfer,msg);
                        if(jsonObject.getInt("status")==1){


                            AwesomeDialog.build(this@MoneyTransfer)
                                .title("Transferred")
                                .body(msg)
                                .icon(R.drawable.ic_congrts)
//                            .background(R.drawable.green_button_background)
                                .onPositive("Ok") {
                                }

                        } else {
//                        msg = "Bad Response when get balance.";
                            AwesomeDialog.build(this@MoneyTransfer!!)
                                .title("Oops!")
                                .body(msg)
//                            .background(R.drawable.edit_txt_error)
//                            .icon(R.drawable.baseline_error_outline_24)
                                .onPositive("Retry") {
                                }
                        }





                    } else {
                        Utility.showDialog(
                            context,
                            SweetAlertDialog.WARNING_TYPE,
                            resources.getString(R.string.error)
                        )
                    }

                } catch (e: Exception) {
                    Utility.showDialog(
                        context,
                        SweetAlertDialog.WARNING_TYPE,
                        resources.getString(R.string.error)
                    )

                    Toast.makeText(
                        context,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

        })
    }


    private fun showDialog1(
        _txtTransferTo: String,
        _txtAcNo: String,
        _txtBankNo: String,
                           ) {
        val dialog = Dialog(this@MoneyTransfer)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_layout)

        val txtTransferTo = dialog.findViewById(R.id.txtTransferTo) as TextView
        val txtAcNo = dialog.findViewById(R.id.txtAcNo) as TextView
        val txtBankNo = dialog.findViewById(R.id.txtBankNo) as TextView
        val edtAmount = dialog.findViewById(R.id.edtAmount) as EditText

        txtTransferTo.setText(_txtTransferTo)
        txtAcNo.setText(_txtAcNo)
        txtBankNo.setText(_txtBankNo)

        val btn_continue = dialog.findViewById(R.id.btn_continue) as Button
        btn_continue.setOnClickListener {
            dialog.dismiss()

            edtAmount.setBackgroundResource(R.drawable.edit_txtbg)
            if (edtAmount.text.isEmpty()) {
                edtAmount.setBackgroundResource(R.drawable.edit_txt_error)
            } else {
                dmtTransferAuth(benId,edtAmount.text.toString())
            }


        }

        val imgClose = dialog.findViewById(R.id.imgClose) as ImageView
        imgClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}