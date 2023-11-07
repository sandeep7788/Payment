package me.ibrahimsn.smoothbottombar.activity.moneytransfer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.gson.Gson
import com.google.gson.JsonObject
import me.ibrahimsn.smoothbottombar.MainActivity
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.activity.OnDataPassListener
import me.ibrahimsn.smoothbottombar.databinding.ActivityAddBeneficiaryBinding
import me.ibrahimsn.smoothbottombar.databinding.ActivityChangePasswordBinding
import me.ibrahimsn.smoothbottombar.databinding.ActivityProfileUpdateBinding
import me.ibrahimsn.smoothbottombar.databinding.FragmentProfileBinding
import me.ibrahimsn.smoothbottombar.dialog.BottomSheetBankDialog
import me.ibrahimsn.smoothbottombar.dialog.BottomSheetDialog1
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.ApiContants.PREF_BEN_NUM
import me.ibrahimsn.smoothbottombar.helper.ApiContants.PREF_REME
import me.ibrahimsn.smoothbottombar.helper.ApiInterface
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.BankPojo
import me.ibrahimsn.smoothbottombar.model.OperatorPojo
import me.ibrahimsn.smoothbottombar.model.UserModel
import me.ibrahimsn.smoothbottombar.model.UserPojo
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBeneficiary : AppCompatActivity(), OnDataPassListener {
    lateinit var context: Context
    lateinit var binding : ActivityAddBeneficiaryBinding
    private var progressDialog: SweetAlertDialog? = null
    private var TAG = "@@SignInActivity"
    private var status_sakha = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_beneficiary)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_beneficiary)
        context = this@AddBeneficiary
        progressDialog = SweetAlertDialog(this@AddBeneficiary, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog!!.progressHelper.barColor = R.color.theme_color
        progressDialog!!.titleText = "Loading ..."
        progressDialog!!.setCancelable(false)

        binding.toolbarLayout1.back.setOnClickListener {
            finish()
        }

        setData()
        getDmtBankList();

        binding.btnProcess.setOnClickListener {
            binding.l1.setBackgroundResource(R.drawable.edit_txtbg)
            binding.l2.setBackgroundResource(R.drawable.edit_txtbg)
            binding.l3.setBackgroundResource(R.drawable.edit_txtbg)
            binding.l4.setBackgroundResource(R.drawable.edit_txtbg)

            when {
                binding.edtAcNumber.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter account number")
                    binding.l1.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                binding.edtBank.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please select your bank")
                    binding.l2.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                binding.edtBeneName.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter your Beneficiary Name")
                    binding.l3.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                binding.edtIfsc.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter your bank IFSC Code")
                    binding.l4.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                else -> {
                    Utility.hideKeyboard(this)
                    dmtBeneficiaryAuth()
                }
            }
        }

        binding.edtBank.setOnClickListener {
            val bottomSheet = BottomSheetBankDialog("bbps",opList,this)
            bottomSheet.show(supportFragmentManager, "tag")
        }
    }

    var opList = ArrayList<BankPojo>();
    fun getDmtBankList()     {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnProcess.visibility = View.GONE

        var apiInterface: ApiInterface = context?.let { RetrofitManager().instance }!!.create(ApiInterface::class.java)

        apiInterface.getDmtBankList().
        enqueue(object : Callback<JsonObject>
        {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context," "+t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {

                    binding.mainProgress.visibility = View.GONE
                    binding.btnProcess.visibility = View.VISIBLE

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
                                opList.add(
                                    BankPojo(json_Array.getJSONObject(i).getString("id"),
                                    json_Array.getJSONObject(i).getString("bank_name"))
                                );
                            }

                        }else{
//                            Toast.makeText(context,"Bad Response when get balance.", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(context,"Bad Response when get balance.", Toast.LENGTH_LONG).show()
                    }
                }else{

                    Toast.makeText(context,"Bad Response when get balance.", Toast.LENGTH_LONG).show()
                }

            }

        })
    }

    var BenNumber =MyApplication.ReadStringPreferences(PREF_BEN_NUM);
    var remitter_mobile =MyApplication.ReadStringPreferences(PREF_REME);

    private fun dmtBeneficiaryAuth() {
        progressDialog!!.show()
        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(this@AddBeneficiary)?.create(ApiInterface::class.java)

        apiInterface!!.dmtBeneficiaryAuth(
            userId?.trim(),
            binding.edtBeneName.text.toString(),
            BenNumber,
            binding.edtAcNumber.text.toString(),
            binding.edtIfsc.text.toString(),
            bankId,
            "1",
            remitter_mobile
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
                        if(jsonObject.getInt("status")==1){

                            AwesomeDialog.build(this@AddBeneficiary)
                                .title("Updated")
                                .body(msg)
                                .icon(R.drawable.ic_congrts)
//                            .background(R.drawable.green_button_background)
                                .onPositive("Go Back") {
//                                    this@AddBeneficiary?.startActivity(Intent(this@AddBeneficiary,MainActivity::class.java))
                                    var intent = Intent();
                                    intent.setData(Uri.parse("refresh"));
                                    setResult(RESULT_OK, intent)
                                    finish();
                                }

                        } else {
//                        msg = "Bad Response when get balance.";
                            AwesomeDialog.build(this@AddBeneficiary!!)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
//                val returnedResult: kotlin.String = data.getData().toString()
//                // OR
//                // String returnedResult = data.getDataString();
                getDmtBankList()
            }
        }}

    var userId=MyApplication.getUserId();

    fun setData() {


        try {
//            var prefData = MyApplication.ReadStringPreferences(ApiContants.PREF_USER);
//            val gson = Gson()
//
//            // Convert JSON to UserPojo
//
//            // Convert JSON to UserPojo
//            val pref: UserPojo = gson.fromJson(prefData, UserPojo::class.java)

            binding.toolbarLayout1.texttitle.text="Add Beneficiary"
            userId = MyApplication.getUserId().toString()



        } catch (e:Exception) {

        }
    }

    var bankId=""

    override fun onDataPassed(
        type: String,
        biller_id: String,
        billerName: String,
        billerAliasName: String,
        is_fetch: String
    ) {
        bankId=biller_id
        binding.edtBank.text = billerName
    }

}