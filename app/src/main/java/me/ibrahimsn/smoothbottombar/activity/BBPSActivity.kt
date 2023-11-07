package me.ibrahimsn.smoothbottombar.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.gson.JsonObject
import me.ibrahimsn.smoothbottombar.MainActivity
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.databinding.ActivityBbpsBinding
import me.ibrahimsn.smoothbottombar.dialog.BottomSheetDialog1
import me.ibrahimsn.smoothbottombar.helper.ApiInterface
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.bbpsServiceModel.BbpsServiceModel
import me.ibrahimsn.smoothbottombar.model.bbpsServiceModel.Datum
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BBPSActivity : AppCompatActivity(),OnDataPassListener {
    lateinit var context: Context
    lateinit var binding : ActivityBbpsBinding
//    private var progressDialog: SweetAlertDialog? = null
    private var TAG = "@@SignInActivity"
    private var serviceId = ""
    private var service_name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbps)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bbps)
        context = this@BBPSActivity
//        progressDialog = SweetAlertDialog(this@BBPSActivity, SweetAlertDialog.PROGRESS_TYPE)
//        progressDialog!!.progressHelper.barColor = R.color.theme_color
//        progressDialog!!.titleText = "Loading ..."
//        progressDialog!!.setCancelable(false)

        if (intent.extras != null) {
            serviceId= intent.getStringExtra("serviceId")
            service_name= intent.getStringExtra("service_name")
        }

        binding.toolbarLayout.back.setOnClickListener {
            finish()
        }

        setData()

        binding.btnProcess.setOnClickListener {
            binding.l1.setBackgroundResource(R.drawable.edit_txtbg)
            binding.l2.setBackgroundResource(R.drawable.edit_txtbg)
//            binding.l3.setBackgroundResource(R.drawable.edit_txtbg)

            when {
                binding.edtOperator.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please select your operator")
                    binding.l1.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                binding.edtKNumber.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter your "+paramName)
                    binding.l2.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
//                binding.edtCustomerNumber.text.isEmpty() -> {
//                    Utility.showSnackBar(this, "Please enter your number")
//                    binding.l3.setBackgroundResource(R.drawable.edit_txt_error)
//                    Utility.hideKeyboard(this)
//                }
                else -> {
                    Utility.hideKeyboard(this)
                    serviceBillPayAuth()
                }
            }
        }

/*
        binding.edtCustomerNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 10) {
                    if ((binding.edtOperator.text.isNotEmpty() &&
                                binding.edtKNumber.text.isNotEmpty() &&
                                binding.edtKNumber.text.length > 4 &&
                                binding.edtCustomerNumber.text.length > 9
                            )
                    ) {
                        bbpsBillFetchAuth()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
*/

/*        binding.edtKNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 10) {
                    if ((binding.edtOperator.text.isNotEmpty() &&
                                binding.edtKNumber.text.isNotEmpty() &&
                                binding.edtKNumber.text.length > 4
//                                binding.edtCustomerNumber.text.length > 9
                                )
                    ) {
                        bbpsBillFetchAuth()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })*/

        binding.btnFetch.setOnClickListener {
            binding.l2.setBackgroundResource(R.drawable.edit_txtbg)
            when {
                binding.edtKNumber.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter your "+paramName)
                    binding.l2.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                else -> {
                    Utility.hideKeyboard(this)
                    bbpsBillFetchAuth()
                }
            }
        }

        binding.edtOperator.setOnClickListener {
            val bottomSheet = BottomSheetDialog1("bbps",list,this,service_name)
            bottomSheet.show(supportFragmentManager, "tag")
        }

        getBbpsServiceOperator()
    }

    fun setData() {


        try {
//            var prefData = MyApplication.ReadStringPreferences(ApiContants.PREF_USER);
//            val gson = Gson()
//
//            // Convert JSON to UserPojo
//
//            // Convert JSON to UserPojo
//            val pref: UserPojo = gson.fromJson(prefData, UserPojo::class.java)


//            disableEditText(binding.edtKNumber)
//            disableEditText(binding.edtCustomerNumber)
            binding.l2.visibility=View.GONE
//            binding.l3.visibility=View.GONE
            userId = MyApplication.getUserId().toString()
            binding.toolbarLayout.texttitle.text=service_name
            binding.edtKNumber.setHint("Please Enter "+paramName)


            if (serviceId == "11") {
                binding.image10.setImageResource(R.drawable.icon_gas)
            } else if (serviceId == "4") {
                binding.image10.setImageResource(R.drawable.electricity)
            } else if (serviceId == "8") {
                binding.image10.setImageResource(R.drawable.icon_boardband)
            } else if (serviceId == "9") {
                binding.image10.setImageResource(R.drawable.icon_cabeltv)
            } else if (serviceId == "22") {
                binding.image10.setImageResource(R.drawable.icon_creditcard)
            } else if (serviceId == "6") {
                binding.image10.setImageResource(R.drawable.icon_pipedgas)
            } else if (serviceId == "5") {
                binding.image10.setImageResource(R.drawable.icon_insurance)
            } else if (serviceId == "2") {
                binding.image10.setImageResource(R.drawable.icon_ladline)
            } else if (serviceId == "17") {
                binding.image10.setImageResource(R.drawable.icon_loan)
            } else if (serviceId == "21") {
                binding.image10.setImageResource(R.drawable.icon_water)
            } else if (serviceId == "12") {
                binding.image10.setImageResource(R.drawable.fasttage)
            } else if (serviceId == "3") {
                binding.image10.setImageResource(R.drawable.postpaid)
            }

        } catch (e:Exception) {

        }
    }


    private fun getBbpsServiceFormParams() {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnFetch.visibility = View.INVISIBLE
//        binding.btnProcess.visibility = View.VISIBLE

        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(this@BBPSActivity)?.create(ApiInterface::class.java)

        apiInterface!!.getBbpsServiceFormParams(
            biller_id,
            serviceId
        ).enqueue(object :
            Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                binding.btnFetch.visibility = View.VISIBLE
                if (Utility.isNetworkAvailable(context)) {
                    Toast.makeText(
                        context,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    Log.e(TAG, "onFailure: " + t.message)
                }
                binding.mainProgress.visibility = View.GONE
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                binding.mainProgress.visibility = View.GONE
//                binding.btnProcess.visibility = View.VISIBLE

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
//                            binding.btnFetch.visibility = View.GONE
                            var data = jsonObject.getJSONArray("data").getJSONObject(0);
                            fieldKey = data.getString("fieldKey");
                            paramName = data.getString("paramName");

                            var intent = Intent(
                                this@BBPSActivity,
                                BBPSBillDetailsActivity::class.java
                            )

                            intent.putExtra("fieldKey",fieldKey)
                            intent.putExtra("paramName",paramName)

                            binding.edtKNumber.setHint(paramName+"")
//                            startActivity(
//                                intent
//                            )
                            overridePendingTransition(
                                me.ibrahimsn.smoothbottombar.R.anim.slide_out_bottom,
                                me.ibrahimsn.smoothbottombar.R.anim.slide_in_bottom
                            )

                            binding.l2.visibility=View.VISIBLE
//                    binding.l3.visibility=View.VISIBLE
                            binding.cardMsg.visibility=View.GONE
                            binding.btnFetch.visibility=View.VISIBLE

                        } else {
//                        msg = "Bad Response when get balance.";
                            AwesomeDialog.build(this@BBPSActivity!!)
                                .title("Something Wrong")
                                .body(msg)
//                            .background(R.drawable.edit_txt_error)
//                            .icon(R.drawable.baseline_error_outline_24)
                                .onPositive("Retry") {
                                    Log.d("TAG", "positive ")
                                }
                        }





                    } else {
                        binding.btnFetch.visibility = View.VISIBLE
                        Utility.showDialog(
                            context,
                            SweetAlertDialog.WARNING_TYPE,
                            resources.getString(R.string.error)
                        )
                    }



                } catch (e: Exception) {
                    binding.btnFetch.visibility = View.VISIBLE
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

    private fun bbpsBillFetchAuth() {
        Utility.hideKeyboard(this)
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnFetch.visibility = View.INVISIBLE
        binding.btnProcess.visibility = View.GONE

        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(this@BBPSActivity)?.create(ApiInterface::class.java)

        val dynamicFields: MutableMap<String, String> = HashMap()
        dynamicFields["user_id"] = userId
        dynamicFields["service_id"] = serviceId
        dynamicFields["biller_id"] = biller_id
        dynamicFields[fieldKey] = binding.edtKNumber.text.toString()

// Call the bbpsBillFetchAuth method with the dynamic fields

// Call the bbpsBillFetchAuth method with the dynamic fields
//        val call: Call<JsonObject> = bbpsBillFetchAuth(dynamicFields)

        apiInterface!!.bbpsBillFetchAuth(
            dynamicFields
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
                binding.mainProgress.visibility = View.GONE
                binding.btnFetch.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                binding.mainProgress.visibility = View.GONE
                binding.btnFetch.visibility = View.VISIBLE

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

                            var amt = jsonObject.getDouble("amount")
                            var name = jsonObject.getString("accountHolderName")
                            var date = jsonObject.getString("dueDate")
                            amount =amt.toString();

                            binding.txtAmount.text="₹"+amt.toString()
                            binding.txtName.text=name
                            binding.txtDate.text=date

                            binding.cardDetails.visibility= View.VISIBLE
                            binding.cardDetails1.visibility= View.VISIBLE

                            binding.btnProcess.visibility= View.VISIBLE

                        } else {
//                        msg = "Bad Response when get balance.";
                            AwesomeDialog.build(this@BBPSActivity!!)
                                .title("Something Wrong")
                                .body(msg)
//                            .background(R.drawable.edit_txt_error)
//                            .icon(R.drawable.baseline_error_outline_24)
                                .onPositive("Retry") {
                                    Log.d("TAG", "positive ")
                                }
                        }





                    } else {
                        binding.btnFetch.visibility = View.VISIBLE
                        Utility.showDialog(
                            context,
                            SweetAlertDialog.WARNING_TYPE,
                            resources.getString(R.string.error)
                        )
                    }

                } catch (e: Exception) {
                    binding.btnFetch.visibility = View.VISIBLE
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

    private fun serviceBillPayAuth() {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnProcess.visibility = View.GONE

        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(this@BBPSActivity)?.create(ApiInterface::class.java)

        val dynamicFields: MutableMap<String, String> = HashMap()
        dynamicFields["user_id"] = userId
        dynamicFields["service_id"] = serviceId
        dynamicFields["biller_id"] = biller_id
        dynamicFields[fieldKey] = binding.edtKNumber.text.toString()
        dynamicFields["amount"] = amount

// Call the bbpsBillFetchAuth method with the dynamic fields

// Call the bbpsBillFetchAuth method with the dynamic fields
//        val call: Call<JsonObject> = bbpsBillFetchAuth(dynamicFields)

        apiInterface!!.serviceBillPayAuth(
            dynamicFields
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
                binding.mainProgress.visibility = View.GONE
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                binding.mainProgress.visibility = View.GONE
                binding.btnProcess.visibility = View.VISIBLE

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
                            AwesomeDialog.build(this@BBPSActivity)
                                .title("Payed")
                                .body(msg)
                                .icon(R.drawable.ic_congrts)
//                            .background(R.drawable.green_button_background)
                                .onPositive("Continue") {
                                    Log.d("TAG", "positive ")
                                    this@BBPSActivity?.startActivity(Intent(this@BBPSActivity,
                                        MainActivity::class.java))
                                    finish()
                                }


                        } else {
//                        msg = "Bad Response when get balance.";
                            AwesomeDialog.build(this@BBPSActivity!!)
                                .title("Something Wrong")
                                .body(msg)
//                            .background(R.drawable.edit_txt_error)
//                            .icon(R.drawable.baseline_error_outline_24)
                                .onPositive("try again") {
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

    var fieldKey =""
    var paramName =""

    var biller_id = "0";
    var billerName = "0";
    var billerAliasName = "0";
    var is_fetch = "0";

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
////        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
//            biller_id = data?.getStringExtra("biller_id").toString()
//            billerName = data?.getStringExtra("billerName").toString()
//            billerAliasName = data?.getStringExtra("billerAliasName").toString()
//            is_fetch = data?.getStringExtra("is_fetch").toString()
//
//            binding.edtOperator.text = billerName
////        }
//    }

    var list:ArrayList<Datum> =ArrayList()
    private var onDataPassListener: OnDataPassListener? = null

    private fun getBbpsServiceOperator() {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnFetch.visibility = View.INVISIBLE

        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(this@BBPSActivity)?.create(ApiInterface::class.java)

        apiInterface!!.getBbpsServiceOperator(
            serviceId,
        ).enqueue(object :
            Callback<BbpsServiceModel> {
            override fun onFailure(call: Call<BbpsServiceModel>, t: Throwable) {

                if (Utility.isNetworkAvailable(context)) {
                    Toast.makeText(
                        context,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    Log.e(TAG, "onFailure: " + t.message)
                }
                binding.mainProgress.visibility = View.GONE
            }

            override fun onResponse(call: Call<BbpsServiceModel>, response: Response<BbpsServiceModel>) {
                binding.mainProgress.visibility = View.GONE
//                binding.btnProcess.visibility = View.VISIBLE
                var msg: String? = null;
                try {
                    if(response.isSuccessful) {

                        var body = response.body();
                        var data = response.body()?.data;

//                    Toast.makeText(context,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                        var msg = "";
                        if (body?.message != null) {
                            msg = body?.message;
                        }
                        if(body?.status ==1){

                            data?.forEach {
                                list.add(it)
                            }


                        } else {
//                        msg = "Bad Response when get balance.";
                            AwesomeDialog.build(this@BBPSActivity!!)
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

    var userId=""
    var amount=""


    override fun onDataPassed(
        type1: String,
        biller_id1: String,
        billerName1: String,
        billerAliasName1: String,
        is_fetch1: String
    ) {
        biller_id = biller_id1
        billerName = billerName1
        billerAliasName = billerAliasName1
        is_fetch = is_fetch1

        binding.edtOperator.text = billerName1



        getBbpsServiceFormParams()

//        enableEditText(binding.edtKNumber)
//        enableEditText(binding.edtCustomerNumber)

    //        binding.l2.visibility=View.VISIBLE
//        binding.l3.visibility=View.VISIBLE
//        binding.cardMsg.visibility=View.GONE

    }
    private fun enableEditText(edtCustomerNumber: EditText) {
        edtCustomerNumber.visibility=View.VISIBLE
//        edtCustomerNumber.setFocusableInTouchMode(true)
//        edtCustomerNumber.setFocusable(true)
//        edtCustomerNumber.setEnabled(true)
    }

    private fun disableEditText(mEditText: EditText) {
        mEditText.visibility=View.GONE
//        mEditText.setEnabled(false)
//        mEditText.setFocusable(false)
//        mEditText.setFocusableInTouchMode(false)
    }
}
