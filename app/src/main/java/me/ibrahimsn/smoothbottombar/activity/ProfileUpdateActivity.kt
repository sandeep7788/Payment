package me.ibrahimsn.smoothbottombar.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.databinding.ActivityProfileUpdateBinding
import me.ibrahimsn.smoothbottombar.databinding.FragmentProfileBinding
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.UserPojo
import me.ibrahimsn.smoothbottombar.model.UserTwoPojo

class ProfileUpdateActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var binding : ActivityProfileUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_update)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_update)
        context = this@ProfileUpdateActivity

        binding.toolbarLayout.back.setOnClickListener {
            finish()
        }

        setData()
    }


    fun setData() {


        try {
            var prefData = MyApplication.ReadStringPreferences(ApiContants.PREF_USER);
            val gson = Gson()

            // Convert JSON to UserPojo

            // Convert JSON to UserPojo
            val pref: UserTwoPojo = gson.fromJson(prefData, UserTwoPojo::class.java)

            binding.toolbarLayout.texttitle.text="Profile Update"
            binding.edtName.setText(pref.name)
            binding.edtMail.setText(pref.email)
            binding.edtMobile.setText(pref.mobile)
            Utility.setImage(this@ProfileUpdateActivity!!,pref.profilePhoto,binding.userProfilePhoto)


        } catch (e:Exception) {

        }
    }

}