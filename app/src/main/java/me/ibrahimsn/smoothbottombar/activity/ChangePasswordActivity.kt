package me.ibrahimsn.smoothbottombar.activity

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
import com.google.gson.Gson
import com.google.gson.JsonObject
import me.ibrahimsn.smoothbottombar.MainActivity
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.databinding.ActivityChangePasswordBinding
import me.ibrahimsn.smoothbottombar.databinding.ActivityProfileUpdateBinding
import me.ibrahimsn.smoothbottombar.databinding.FragmentProfileBinding
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.ApiInterface
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.UserModel
import me.ibrahimsn.smoothbottombar.model.UserPojo
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var binding : ActivityChangePasswordBinding
    private var progressDialog: SweetAlertDialog? = null
    private var TAG = "@@SignInActivity"
    private var status_sakha = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        context = this@ChangePasswordActivity
        progressDialog = SweetAlertDialog(this@ChangePasswordActivity, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog!!.progressHelper.barColor = R.color.theme_color
        progressDialog!!.titleText = "Loading ..."
        progressDialog!!.setCancelable(false)

        binding.toolbarLayout.back.setOnClickListener {
            finish()
        }

        setData()

        binding.btnContinue.setOnClickListener {
            binding.field1.setBackgroundResource(R.drawable.edit_txtbg)
            binding.field2.setBackgroundResource(R.drawable.edit_txtbg)
            binding.field3.setBackgroundResource(R.drawable.edit_txtbg)

            when {
                binding.edtOldPassword.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter old password")
                    binding.field1.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                binding.edtNewPassword.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter your new password")
                    binding.field2.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                binding.edtconfirmPassword.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter to confirm password")
                    binding.field3.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                !binding.edtconfirmPassword.text.toString().equals(binding.edtNewPassword.text.toString(),true) -> {
                    Utility.showSnackBar(this, "New and Confirm Password should be same.")
//                    binding.edtconfirmPassword.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                else -> {
                    Utility.hideKeyboard(this)
                    changePassword()
                }
            }
        }
    }


    private fun changePassword() {
        progressDialog!!.show()
        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(this@ChangePasswordActivity)?.create(ApiInterface::class.java)

        apiInterface!!.changePassword(
            userId.trim(),
            binding.edtOldPassword.text.toString().trim(),
            binding.edtNewPassword.text.toString().trim(),
            binding.edtconfirmPassword  .text.toString().trim(),
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

                            AwesomeDialog.build(this@ChangePasswordActivity)
                                .title("Updated")
                                .body(msg)
                                .icon(R.drawable.ic_congrts)
//                            .background(R.drawable.green_button_background)
                                .onPositive("Back To Home") {
                                    Log.d("TAG", "positive ")
                                    this@ChangePasswordActivity?.startActivity(Intent(this@ChangePasswordActivity,MainActivity::class.java))
                                    finish()
                                }

                        } else {
//                        msg = "Bad Response when get balance.";
                            AwesomeDialog.build(this@ChangePasswordActivity!!)
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

    fun setData() {


        try {
//            var prefData = MyApplication.ReadStringPreferences(ApiContants.PREF_USER);
//            val gson = Gson()
//
//            // Convert JSON to UserPojo
//
//            // Convert JSON to UserPojo
//            val pref: UserPojo = gson.fromJson(prefData, UserPojo::class.java)

            binding.toolbarLayout.toolbar.title="Change Password"
            userId = MyApplication.getUserId().toString()



        } catch (e:Exception) {

        }
    }

}