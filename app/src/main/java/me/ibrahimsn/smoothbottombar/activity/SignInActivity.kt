package me.ibrahimsn.smoothbottombar.activity


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import me.ibrahimsn.smoothbottombar.MainActivity
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.databinding.ActivitySignInBinding
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.ApiInterface
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivitySignInBinding
    private var progressDialog: SweetAlertDialog? = null
    private var TAG = "@@SignInActivity"
    private var status_sakha = false
    private lateinit var context:Activity


    private fun signIn() {
        progressDialog!!.show()
        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(context)?.create(ApiInterface::class.java)

        apiInterface!!.loginAuth(
            binding.edtMemberId.text.toString().trim(),
            binding.edtPassword.text.toString().trim(),
        ).enqueue(object :
            Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {

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

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                progressDialog!!.dismiss()
                var msg: String? = null;
                try {
                    if (response.isSuccessful)




                    /*{

                                            Log.d(TAG, "onResponse: " + response.body().toString())
                                            val jsonObject =
                                                JSONObject(response.body().toString()).getJSONObject("data")
                                            try {
                                                MyApplication.writeStringPreference(
                                                    ApiContants.PREF_STATUS,
                                                    JSONObject(response.body().toString()).getInt("status").toString()
                                                )
                                            }catch (e:Exception) {

                                            }
                                            if (jsonObject.has("response")) {
                                                msg = jsonObject.getString("response")
                                                Toast.makeText(
                                                    context,
                                                    " " + msg,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }

                                            if (jsonObject.has("username")) {

                                                var data = jsonObject

                                                MyApplication.writeStringPreference(
                                                    ApiContants.PREF_USER_NAME,
                                                    data.getString("username")
                                                )
                                                MyApplication.writeIntPreference(
                                                    ApiContants.PREF_USER_ID,
                                                    data.getInt("userid")
                                                )
                                                MyApplication.writeIntPreference(
                                                    ApiContants.PREF_USER_SHAKA,
                                                    data.getInt("shaka")
                                                )
                                                MyApplication.writeStringPreference(
                                                    ApiContants.PREF_role,
                                                    data.getString("role_name")
                                                )
                                                MyApplication.writeIntPreference(
                                                    ApiContants.PREF_nager,
                                                    data.getInt("nager")
                                                )
                                                MyApplication.writeStringPreference(ApiContants.login, "true")
                                                MyApplication.writeBoolPreference(ApiContants.isMskUser, data.getInt("shaka") > 0)



                                                startActivity(Intent(context, MainActivity::class.java))
                                                finish()
                                            } else {
                                                Utility.showDialog(
                                                    context,
                                                    SweetAlertDialog.WARNING_TYPE,
                                                    "कोई उपयोगकर्ता नहीं मिला"
                                                )
                                            }
                                        }*/{

//                        var json: String? = gson.toJson(response.body()?.userDetail)


                     try {
                         val gson = Gson()

                         // Convert UserPojo to JSON

                         // Convert UserPojo to JSON
                         response.body()?.message?.let {
                             Utility.showSnackBar(this@SignInActivity,
                                 it
                             )
                         }

                         if (response.body()?.status == 1) {
                             var user = response.body()?.userDetail
//                             val json = gson.toJson(response.body()?.userDetail)
                             MyApplication.writeStringPreference(ApiContants.login,"true")
                             MyApplication.setUserId(user!!.userId)
//                             MyApplication.writeStringPreference(ApiContants.PREF_USER,json);
                             startActivity(Intent(context, SplashScreen::class.java))
                             finish()
                         }

                     } catch (e:Exception) {
                         MyApplication.writeStringPreference(ApiContants.login,"false")
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


    lateinit var layout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        context = this@SignInActivity
/*
        val widthDp = resources.displayMetrics.run { widthPixels / density }
        val heightDp = resources.displayMetrics.run { heightPixels / density }

        var width:Int= widthDp.roundToInt()
        binding.l1.setMargins(top = (widthDp+100).roundToInt())*/

/*        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(this.binding.l1.getWidth(), this.binding.l1.getHeight())
        params.setMargins(left, top, right, bottom)
        this.binding.l1.setLayoutParams(params)*/

        progressDialog = SweetAlertDialog(this@SignInActivity, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog!!.progressHelper.barColor = R.color.theme_color
        progressDialog!!.titleText = "Loading ..."
        progressDialog!!.setCancelable(false)

//        clickListener()
//        setSakhaList(binding.spinnerSakha)

        layout = findViewById(R.id.root)

        binding.c1.visibility=View.VISIBLE
        binding.c2.visibility=View.GONE

        binding.btnContinue.setOnClickListener {
            binding.edtMemberId.setBackgroundResource(R.drawable.edit_txtbg)
//            binding.l2.setBackgroundResource(R.drawable.edit_txtbg)
            when {
                binding.edtMemberId.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter your member ID")
                    binding.edtMemberId.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                else -> {
                    Utility.hideKeyboard(this)
                    binding.c1.visibility=View.GONE
                    binding.c.visibility=View.GONE
                    binding.c2.visibility=View.VISIBLE

                    binding.titleTxt.setText("Enter your password to\nverify " + "("+binding.edtMemberId.text.toString()+")")
                }
            }
        }

        binding.btnSubmit.setOnClickListener {
            binding.edtPassword.setBackgroundResource(R.drawable.edit_txtbg)
//            binding.l2.setBackgroundResource(R.drawable.edit_txtbg)
            when {
                binding.edtPassword.text.isEmpty() -> {
                    Utility.showSnackBar(this, "Please enter your password")
                    binding.edtPassword.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(this)
                }
                else -> {
                    binding.btnContinue.visibility=View.GONE
                    binding.btnProgress.visibility=View.VISIBLE
                    Utility.hideKeyboard(this)
//                    Utility.showSnackBar(this, "Login Successfully ...")

                    signIn()

//                    Handler().postDelayed(Runnable {
//                        val mainIntent = Intent(this@SignInActivity, MainActivity::class.java)
//                        this@SignInActivity.startActivity(mainIntent)
//                    },1000)


                }
            }
        }

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}