package me.ibrahimsn.smoothbottombar.activity.tab

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.annotation.SuppressLint
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonObject
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.databinding.ActivitySelectrechargePalneBinding
import me.ibrahimsn.smoothbottombar.databinding.ActivitySignInBinding
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.ApiInterface
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager
import me.ibrahimsn.smoothbottombar.model.tab.ModelRequestdetails
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SelectRecharge_Palne_Activity : AppCompatActivity() {
    private val mFragmentList: List<Fragment> =
        ArrayList()
    private val mFragmentTitleList: MutableList<String?> =
        ArrayList()
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var tabname: String? = null
    var tabid: String? = null
    private var adapter: TabAdapter? = null
    private var back: ImageView? = null
//    private var btn_user: ImageView? = null
    private var layout_empty_img: LinearLayout? = null
    private var progressbar: ProgressBar? = null

    var TAG="@@SelectRecharge_Palne_Activity"
    var pref = MyApplication

    var operatorId = "0";
    var circleId = "0";
    private lateinit var binding: ActivitySelectrechargePalneBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectrecharge_palne)
        binding = DataBindingUtil.setContentView(this@SelectRecharge_Palne_Activity, R.layout.activity_selectrecharge_palne)
        viewPager = findViewById(R.id.pager)
        back=findViewById(R.id.back)
//        btn_user=findViewById(R.id.btn_user)
        layout_empty_img=findViewById(R.id.layout_empty_img)
        progressbar=findViewById(R.id.progressbar)
        viewPager?.measure(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        tabLayout = findViewById(R.id.tabs)

        if (intent.extras != null) {
            operatorId = intent.getStringExtra("operatorId")
            circleId = intent.getStringExtra("circleId")
        }

        binding.toolbar.texttitle.text = "Select Plan";
        adapter = TabAdapter(supportFragmentManager)
//        blogTab
        setupData()





        //
        back!!.setOnClickListener { super.onBackPressed() }
//        btn_user!!.setVisibility(View.GONE)
        layout_empty_img!!.setVisibility(View.GONE)

    }

    fun setupData() {
        for (j in 0 until 5) {
            if (j==0) {
                tabname="FULLTT"
            } else if (j==1) {
                tabname="TOPUP"
            } else if (j==2) {
                tabname="3G/4G"
            } else if (j==3) {
                tabname="2G"
            } else if (j==4) {
                tabname="Romaing"
            } else {
                tabname="Other"
            }


            //childResult.data[j].category
            tabid = "tab-"+j //childResult.data[j].category
            mFragmentTitleList.add(tabname)
            adapter!!.addFragment(
                RechargePlaneFragment(
                    j , operatorId, circleId
                ), tabname
            )
        }
        viewPager!!.adapter = adapter
        viewPager!!.offscreenPageLimit = 5//childResult.data.size + 1
        tabLayout!!.setupWithViewPager(viewPager)
        for (i in mFragmentList.indices) {
            mFragmentTitleList.add("no title")
        }
        layout_empty_img!!.setVisibility(View.GONE)
        progressbar!!.setVisibility(View.GONE)
    }
//    private val blogTab: Unit
//        private get() {
//            val apiInterface =
//                RetrofitManager().instance!!.create(
//                    ApiInterface::class.java
//                )
//            Log.d(TAG, ": PREF_operator_code"+pref.ReadStringPreferences(ApiContants.PREF_operator_code))
//            val call = apiInterface.GetPlandetails(operatorId,circleId)
//            call.enqueue(object : Callback<ModelRequestdetails?> {
//                override fun onResponse(
//                    call: Call<ModelRequestdetails?>,
//                    response: Response<ModelRequestdetails?>
//                ) {
//
//
//                    progressbar!!.setVisibility(View.GONE)
//                    if (response.isSuccessful) {
//
//                        Log.d(TAG, "onResponse: " + response.body().toString())
//
//
////                        if (mJsonObject.has("Data") && !mJsonObject.isNull("Data")) {
//                        if (response.body()?.info!=null) {
//                            val childResult = response.body()
//                            for (j in 0 until 5) {
//                                if (j==0) {
//                                    tabname="FULLTT"
//                                } else if (j==1) {
//                                    tabname="TOPUP"
//                                } else if (j==2) {
//                                    tabname="3G/4G"
//                                } else if (j==3) {
//                                    tabname="2G"
//                                } else if (j==4) {
//                                    tabname="Romaing"
//                                } else {
//                                    tabname="Other"
//                                }
//
//
//                                //childResult.data[j].category
//                                tabid = "tab-"+j //childResult.data[j].category
//                                mFragmentTitleList.add(tabname)
//                                adapter!!.addFragment(
//                                    RechargePlaneFragment(
//                                        j
//                                    ), tabname
//                                )
//                            }
//                            viewPager!!.adapter = adapter
//                            viewPager!!.offscreenPageLimit = 5//childResult.data.size + 1
//                            tabLayout!!.setupWithViewPager(viewPager)
//                            for (i in mFragmentList.indices) {
//                                mFragmentTitleList.add("no title")
//                            }
//                            layout_empty_img!!.setVisibility(View.GONE)
//                        }
//                        else {
//                            layout_empty_img!!.setVisibility(View.VISIBLE)
//                        }
//                    } else {
//                        layout_empty_img!!.setVisibility(View.VISIBLE)
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<ModelRequestdetails?>,
//                    t: Throwable
//                ) {
//                    progressbar!!.setVisibility(View.GONE)
//                    layout_empty_img!!.setVisibility(View.VISIBLE)
//                }
//            })
//        }
}