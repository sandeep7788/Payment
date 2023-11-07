package me.ibrahimsn.smoothbottombar.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.databinding.ActivitySplashScreenBinding
import me.ibrahimsn.smoothbottombar.MainActivity
import me.ibrahimsn.smoothbottombar.activity.slider.MyIntro
import me.ibrahimsn.smoothbottombar.activity.tab.SelectRecharge_Palne_Activity
import me.ibrahimsn.smoothbottombar.helper.ApiInterface
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.UserModel
import me.ibrahimsn.smoothbottombar.model.UserTwoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    private var TAG = "@@SignInActivity"
    private lateinit var context: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)
        context = this@SplashScreen

//        userDetail()

        if(MyApplication.ReadStringPreferences(ApiContants.login).equals("true",true)){
//                val mainIntent = Intent(this@SplashScreen, MainActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                this@SplashScreen.startActivity(mainIntent)
//                this@SplashScreen.finish()

            userDetail()
        }else {

            Handler().postDelayed(Runnable {
                val mainIntent = Intent(this@SplashScreen, MyIntro::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                this@SplashScreen.startActivity(mainIntent)
                this@SplashScreen.finish()

            },3000)
//                val mainIntent = Intent(this@SplashScreen, SelectRecharge_Palne_Activity::class.java)

        }



    }

    private fun userDetail() {
        binding.progressDialog!!.visibility = View.VISIBLE
        val apiInterface: ApiInterface? =
            RetrofitManager().instanceNew(context)?.create(ApiInterface::class.java)

        apiInterface!!.userDetail(
            MyApplication.getUserId()
        ).enqueue(object :
            Callback<UserTwoModel> {
            override fun onFailure(call: Call<UserTwoModel>, t: Throwable) {

                if (Utility.isNetworkAvailable(context)) {
                    Toast.makeText(
                        context,
                        " " + resources.getString(R.string.error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    Log.e(TAG, "onFailure: " + t.message)
                }
                binding.progressDialog!!.visibility = View.GONE
            }

            override fun onResponse(call: Call<UserTwoModel>, response: Response<UserTwoModel>) {
                binding.progressDialog!!.visibility = View.GONE
                var msg: String? = null;
                try {
                    if (response.isSuccessful)



                        try {
                            val gson = Gson()

                            // Convert UserPojo to JSON

                            // Convert UserPojo to JSON
                            response.body()?.message?.let {
                                Utility.showSnackBar(this@SplashScreen,
                                    it
                                )
                            }

                            if (response.body()?.status == 1 && response.body()?.userDetail != null) {
                                var user = response.body()?.userDetail
                                val json = gson.toJson(response.body()?.userDetail)
                                MyApplication.writeStringPreference(ApiContants.login,"true")
                                MyApplication.setUserId(user!!.userID)
                                MyApplication.writeStringPreference(ApiContants.PREF_USER,json);

                                val mainIntent = Intent(this@SplashScreen, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                this@SplashScreen.startActivity(mainIntent)
                                this@SplashScreen.finish()

                            } else {
                                MyApplication.clearPrefrences()
                                MyApplication.writeStringPreference(ApiContants.login,"false")
                                startActivity(Intent(context, SplashScreen::class.java))
                                finish()
                            }

                        } catch (e:Exception) {
                            MyApplication.writeStringPreference(ApiContants.login, "false")
                            MyApplication.clearPrefrences()
                            startActivity(Intent(context, SplashScreen::class.java))
                            finish()
                        }





                } catch (e: Exception) {
                    MyApplication.clearPrefrences()
                    startActivity(Intent(context, SplashScreen::class.java))
                    finish()
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

}