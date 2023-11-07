package me.ibrahimsn.smoothbottombar

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.location.LocationManager
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onNegative
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.gson.Gson
import me.ibrahimsn.lib.SmoothBottomBar
import me.ibrahimsn.smoothbottombar.activity.ChangePasswordActivity
import me.ibrahimsn.smoothbottombar.activity.ProfileUpdateActivity
import me.ibrahimsn.smoothbottombar.activity.SplashScreen
import me.ibrahimsn.smoothbottombar.databinding.FragmentFourthBinding
import me.ibrahimsn.smoothbottombar.databinding.FragmentProfileBinding
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.StatePojo
import me.ibrahimsn.smoothbottombar.model.UserPojo
import me.ibrahimsn.smoothbottombar.model.UserTwoPojo


class FourthFragment : Fragment() {
    lateinit var thiscontext: Context
    lateinit var mainBinding : FragmentProfileBinding
//    var pref= Local_data(context)
    var editTextStatus=true
    var pDialog: SweetAlertDialog?=null
    var TAG="@@Profile_Fragment"
    var stateList:ArrayList<StatePojo> = java.util.ArrayList()
    var stateID=" "
    lateinit var locationManager :LocationManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? { mainBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)

        thiscontext=container!!.context
//        pref.setMyappContext(context)
        mainBinding.toolbarLayout.back.visibility=View.GONE
        mainBinding.toolbarLayout.toolbar.visibility=View.GONE

        pDialog=SweetAlertDialog(thiscontext, SweetAlertDialog.PROGRESS_TYPE)
        pDialog!!.progressHelper.barColor = Color.parseColor("#132752")
        pDialog!!.titleText = "Loading ..."
        pDialog!!.setCancelable(false)
        locationManager = thiscontext.getSystemService(LOCATION_SERVICE) as LocationManager
        var myFocusListener =
            OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    var l:SmoothBottomBar= activity?.findViewById<SmoothBottomBar>(R.id.bottomBar)!!
                    activity?.findViewById<SmoothBottomBar>(R.id.bottomBar)?.visibility=View.GONE
                } else {
                    activity?.findViewById<SmoothBottomBar>(R.id.bottomBar)?.visibility=View.VISIBLE
                }
            }

        Onclick()
        setData()

        mainBinding.toolbarLayout.texttitle.setText("Your Profile")



        return mainBinding.root


    }

    private fun Onclick() {

        mainBinding.layoutprofile.setOnClickListener {
            thiscontext?.startActivity(Intent(requireContext(), ProfileUpdateActivity::class.java))
        }
        mainBinding.layoutChangePassword.setOnClickListener {
            thiscontext?.startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
        }

        mainBinding.logout.setOnClickListener {
            AwesomeDialog.build(requireActivity())
                .title("Logout")
                .body("Are you sure you want to do logout")
                .icon(R.drawable.baseline_error_outline_24)
//                            .background(R.drawable.green_button_background)
                .onPositive("Logout") {
                    MyApplication.logout(true)
                    activity?.startActivity(Intent(thiscontext,SplashScreen::class.java))
                }
                .onNegative("Cancel") {

                }
        }
    }


    private fun disableEditText(editText: EditText) {
        editText.isFocusable = false
        editText.isEnabled = false
        editText.isCursorVisible = false
        editText.keyListener = null
        editText.getBackground().setColorFilter(
            getResources().getColor(R.color.gray_btn_bg_pressed_color),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    private fun enableEditText(editText: EditText) {
        editText.isFocusable = true
        editText.isEnabled = true
        editText.isCursorVisible = true
        editText.setFocusableInTouchMode(true)
        editText.setKeyListener(AppCompatEditText(thiscontext).getKeyListener())
        editText.getBackground().setColorFilter(
            getResources().getColor(R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    fun showDialog(title: String, type: String){
        Log.d("@@" + TAG, "showDialog: ")
        SweetAlertDialog(thiscontext, type.toInt())
            .setTitleText(title)
            .setConfirmText("OK")
            .setConfirmClickListener { sDialog ->
                sDialog.dismiss()
            }
            .show()
    }

    fun setData() {


     try {
         var prefData = MyApplication.ReadStringPreferences(ApiContants.PREF_USER);
         val gson = Gson()

         // Convert JSON to UserPojo

         // Convert JSON to UserPojo
         val pref: UserTwoPojo = gson.fromJson(prefData, UserTwoPojo::class.java)

         Log.e("##", MyApplication.ReadStringPreferences(ApiContants.PREF_L_name))
//        mainBinding.txtMemberName.setText(pref.ReadStringPreferences(ApiContants.PREF_MemberName))
         mainBinding.userProfileShortBio.setText("User Code: "+pref.userCode +"" )

         mainBinding.userProfileName.setText(pref.name)
//         mainBinding.edtEmail.setText(pref.email)
//         mainBinding.edtNumber.setText("+91 "+pref.mobile)
         mainBinding.edtwallet.setText(getDouble(""+pref.getWalletBalance()))
         mainBinding.edtEwallet.setText(getDouble(""+pref.geteWalletBalance()))

         Utility.setImage(thiscontext!!,pref.profilePhoto,mainBinding.userProfilePhoto)


     } catch (e:Exception) {

     }
    }


    fun getDouble(value: String): String {
        return value.trim().toDouble().toString();
    }
}
