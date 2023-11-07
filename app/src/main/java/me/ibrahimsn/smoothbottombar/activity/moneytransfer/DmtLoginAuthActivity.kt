package me.ibrahimsn.smoothbottombar.activity.moneytransfer

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.google.gson.JsonObject
import me.ibrahimsn.smoothbottombar.MainActivity
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.databinding.ActivityDmtLoginAuthBinding
import me.ibrahimsn.smoothbottombar.databinding.ActivityMoneytransferBinding
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.ApiInterface
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.tab.CategoryDetail
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DmtLoginAuthActivity : AppCompatActivity() {

    lateinit var context: Activity
    lateinit var binding : ActivityDmtLoginAuthBinding
    private var progressDialog: SweetAlertDialog? = null
    private var TAG = "@@MoneyTransfer"
    var userId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dmt_login_auth)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dmt_login_auth)
        context = this@DmtLoginAuthActivity
        progressDialog = SweetAlertDialog(this@DmtLoginAuthActivity, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog!!.progressHelper.barColor = R.color.theme_color
        progressDialog!!.titleText = "Loading ..."
        progressDialog!!.setCancelable(false)
        setCard(1)

        binding.toolbarLayout.texttitle.text="DMT Authentication"
        userId = MyApplication.getUserId().toString()



        binding.toolbarLayout.back.setOnClickListener {
            finish()
        }

        binding.edtCalender.setOnClickListener {

            Utility.setDate(binding.edtCalender,context)
        }

        binding.btnProcess.setOnClickListener {
            binding.l1.setBackgroundResource(R.drawable.border)
            binding.l2.setBackgroundResource(R.drawable.border)
            binding.l3.setBackgroundResource(R.drawable.border)
            binding.l4.setBackgroundResource(R.drawable.border)
            binding.l5.setBackgroundResource(R.drawable.border)
            binding.l6.setBackgroundResource(R.drawable.border)

            when {
                 binding.edtfirstName.text.isEmpty() -> {
                    Utility.showSnackBar(context, "Please enter your first name")
                    binding.l1.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(context)
                }

                 binding.edtSecoundName.text.isEmpty() -> {
                    Utility.showSnackBar(context, "Please enter your secound name")
                    binding.l2.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(context)
                }
                binding.edtNumber2.text.isEmpty() -> {
                    Utility.showSnackBar(context, "Please enter your number")
                    binding.l3.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(context)
                }
                binding.edtCalender.text.isEmpty() -> {
                    Utility.showSnackBar(context, "Please select your DOB")
                    binding.l4.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(context)
                }
                binding.edtAddress.text.isEmpty() -> {
                    Utility.showSnackBar(context, "Please enter your address")
                    binding.l5.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(context)
                }
                binding.edtPinCode.text.isEmpty() -> {
                    Utility.showSnackBar(context, "Please enter your pincode")
                    binding.l6.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(context)
                }
                else -> {
                    Utility.hideKeyboard(context)

                    dmtRegisterAuth()
                }
            }
        }


        binding.btnContinue.setOnClickListener {
            binding.field1.setBackgroundResource(R.drawable.border)

            when {
                binding.edtMobileNumber.text.isEmpty() -> {
                    Utility.showSnackBar(context, "Please enter your Number")
                    binding.field1.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(context)
                }
                else -> {
                    Utility.hideKeyboard(context)

                    dmtLoginAuth()
                }
            }
        }

        binding.btnOtp.setOnClickListener {
            binding.fieldOtp.setBackgroundResource(R.drawable.border)

            when {
                binding.edtOtp.text.isEmpty() -> {
                    Utility.showSnackBar(context, "Please enter your OTP")
                    binding.fieldOtp.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(context)
                }

                else -> {
                    Utility.hideKeyboard(context)

                    dmtOtpVerifyAuth()
                }
            }
        }}

    fun setCard(value:Int) {

        if (value==1) {
            binding.card1.visibility=View.VISIBLE
            binding.card2.visibility=View.GONE
            binding.card3.visibility=View.GONE
        } else if (value == 2) {
            binding.card1.visibility=View.GONE
            binding.card2.visibility=View.VISIBLE
            binding.card3.visibility=View.GONE
        } else if (value == 3) {
            binding.card1.visibility=View.GONE
            binding.card2.visibility=View.GONE
            binding.card3.visibility=View.VISIBLE
        }

    }
    var token="";
    fun dmtLoginAuth()     {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnProcess.visibility = View.GONE
        progressDialog?.show()

        var apiInterface: ApiInterface = context?.let { RetrofitManager().instance }!!.create(
            ApiInterface::class.java)

        apiInterface.dmtLoginAuth(
            MyApplication.getUserId()?.trim(),
            binding.edtMobileNumber.text.toString()
        ).
        enqueue(object : Callback<JsonObject>
        {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(context," "+t.message.toString(), Toast.LENGTH_LONG).show()
                binding.mainProgress.visibility = View.GONE
                binding.btnProcess.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                binding.mainProgress.visibility = View.GONE
                binding.btnProcess.visibility = View.VISIBLE
                progressDialog?.dismiss()
                if(response.isSuccessful) {



                    Log.d(TAG, "onResponse: "+response.toString())
                    Log.d(TAG, "onResponse: "+response.body().toString())

                    val jsonObject= JSONObject(response.body().toString())

//                    Toast.makeText(context,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                    var msg = "";
                    if (jsonObject.getString("message") != null) {
                        msg = jsonObject.getString("message");
                    }
                    binding.edtNumber2.setText(binding.edtMobileNumber.text.toString())
                    if(jsonObject.getInt("status")==1){


//                        MyApplication.writeStringPreference(ApiContants.PREF_BEN_NUM,jsonObject.getString("mobile"))
                        MyApplication.writeStringPreference(ApiContants.PREF_REME,binding.edtMobileNumber.text.toString())

                        MyApplication.writeStringPreference(ApiContants.PREF_DMT_NAME,jsonObject.getString("name"))
                        MyApplication.writeStringPreference(ApiContants.PREF_DMT_TOTAL_LIMIT,jsonObject.getString("total_limit"))
                        MyApplication.writeStringPreference(ApiContants.PREF_DMT_AVAILABLE_LIMIT,jsonObject.getString("available_limit"))

//                        AwesomeDialog.build(context!!)
//                            .title(msg)
////                            .body(msg)
////                            .icon(R.drawable.ic_congrts)
////                            .background(R.drawable.green_button_background)
//                            .onPositive("Continue") {
//                                setCard(2)
//
//
//                            }

                        startActivity(
                            Intent(context,
                                MoneyTransfer::class.java)
                        )
                        finish()

                    } else {


                        AwesomeDialog.build(context!!)
                            .title(msg)
//                            .body(msg)
//                            .icon(R.drawable.ic_congrts)
//                            .background(R.drawable.green_button_background)
                            .onPositive("Continue") {
//                                startActivity(
//                                    Intent(context,
//                                        MainActivity::class.java)
//                                )
                                setCard(2)
                            }
                    }
                }else{

                    Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                }

            }

        })
    }



    fun dmtRegisterAuth()     {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnProcess.visibility = View.GONE
        progressDialog?.show()
        var apiInterface: ApiInterface = context?.let { RetrofitManager().instance }!!.create(
            ApiInterface::class.java)

        apiInterface.dmtRegisterAuth(
            MyApplication.getUserId()?.trim(),
            binding.edtfirstName.text.toString(),
            binding.edtSecoundName.text.toString(),
            binding.edtNumber2.text.toString(),
            binding.edtCalender.text.toString(),
            binding.edtAddress.text.toString(),
            binding.edtPinCode.text.toString(),
        ).
        enqueue(object : Callback<JsonObject>
        {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context," "+t.message.toString(), Toast.LENGTH_LONG).show()
                binding.mainProgress.visibility = View.GONE
                binding.btnProcess.visibility = View.VISIBLE
                progressDialog?.dismiss()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                binding.mainProgress.visibility = View.GONE
                binding.btnProcess.visibility = View.VISIBLE
                progressDialog?.dismiss()
                if(response.isSuccessful) {



                    Log.d(TAG, "onResponse: "+response.toString())
                    Log.d(TAG, "onResponse: "+response.body().toString())

                    val jsonObject= JSONObject(response.body().toString())

//                    Toast.makeText(context,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                    var msg = "";

                    if (jsonObject.getString("message") != null) {
                        msg = jsonObject.getString("message");

                    }
                    if(jsonObject.getInt("status")==1){
                        token = jsonObject.getString("token");
                        setCard(3)

                    } else {
                        AwesomeDialog.build(context!!)
                            .title(msg)
//                            .body(msg)
//                            .icon(R.drawable.ic_congrts)
//                            .background(R.drawable.green_button_background)
                            .onPositive("Go Back") {
                                startActivity(
                                    Intent(context,
                                        MainActivity::class.java)
                                )
                            }
                    }
                }else{

                    Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                }

            }

        })
    }
    fun dmtOtpVerifyAuth()     {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnProcess.visibility = View.GONE
        progressDialog?.show()
        var apiInterface: ApiInterface = context?.let { RetrofitManager().instance }!!.create(
            ApiInterface::class.java)

        apiInterface.dmtOtpVerifyAuth(
            MyApplication.getUserId()?.trim(),
            binding.edtOtp.text.toString(),
            token
        ).
        enqueue(object : Callback<JsonObject>
        {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(context," "+t.message.toString(), Toast.LENGTH_LONG).show()
                binding.mainProgress.visibility = View.GONE
                binding.btnProcess.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                binding.mainProgress.visibility = View.GONE
                binding.btnProcess.visibility = View.VISIBLE
                progressDialog?.dismiss()
                if(response.isSuccessful) {



                    Log.d(TAG, "onResponse: "+response.toString())
                    Log.d(TAG, "onResponse: "+response.body().toString())

                    val jsonObject= JSONObject(response.body().toString())

//                    Toast.makeText(context,jsonObject.getString("message"), Toast.LENGTH_LONG).show()

                    var msg = "";
                    if (jsonObject.getString("message") != null) {
                        msg = jsonObject.getString("message");
                    }
                    if(jsonObject.getInt("status")==1){


//                        MyApplication.writeStringPreference(ApiContants.PREF_BEN_NUM,jsonObject.getString("mobile"))
                        MyApplication.writeStringPreference(ApiContants.PREF_REME,binding.edtMobileNumber.text.toString())

                        AwesomeDialog.build(context!!)
                            .title("Congrats")
                            .body(msg)
                            .icon(R.drawable.ic_congrts)
//                            .background(R.drawable.green_button_background)
                            .onPositive("Continue") {
                                startActivity(
                                    Intent(context,
                                        MoneyTransfer::class.java)
                                )
                            }

                    } else {
                        AwesomeDialog.build(context!!)
                            .title(msg)
//                            .body(msg)
//                            .icon(R.drawable.ic_congrts)
//                            .background(R.drawable.green_button_background)
                            .onPositive("Try Again") {
//                                startActivity(
//                                    Intent(context,
//                                        MainActivity::class.java)
//                                )
//                                setCard(2)
                            }
                    }
                }else{

                    Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                }

            }

        })
    }

}