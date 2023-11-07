package me.ibrahimsn.smoothbottombar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.smarteist.autoimageslider.SliderView
import me.ibrahimsn.smoothbottombar.activity.BBPSActivity
import me.ibrahimsn.smoothbottombar.activity.BBPSBillDetailsActivity
import me.ibrahimsn.smoothbottombar.activity.banner.SliderAdapter
import me.ibrahimsn.smoothbottombar.activity.moneytransfer.DmtLoginAuthActivity
import me.ibrahimsn.smoothbottombar.activity.moneytransfer.MoneyTransfer
import me.ibrahimsn.smoothbottombar.databinding.FragmentFirstBinding
import me.ibrahimsn.smoothbottombar.frag.RechargeFragment
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.UserTwoPojo


/**
 * A simple [Fragment] subclass.
 */
class FirstFragment : Fragment(R.layout.fragment_first) {

    lateinit var thiscontext: Context
    lateinit var binding : FragmentFirstBinding
    var serviceId = "4"

    fun setData() {


        try {
            var prefData = MyApplication.ReadStringPreferences(ApiContants.PREF_USER);
            val gson = Gson()

            // Convert JSON to UserPojo

            // Convert JSON to UserPojo
            val pref: UserTwoPojo = gson.fromJson(prefData, UserTwoPojo::class.java)

            Log.e("##", MyApplication.ReadStringPreferences(ApiContants.PREF_L_name))
//        mainBinding.txtMemberName.setText(pref.ReadStringPreferences(ApiContants.PREF_MemberName))
            binding.wallet.setText("₹" + getDouble(pref.walletBalance))
            binding.ewallet.setText("₹" + getDouble(pref.geteWalletBalance().toString()))


        } catch (e:Exception) {

        }
    }
    fun getDouble(value: String): String {
        return value.trim().toDouble().toString();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).setBadge(2)
        (requireActivity() as MainActivity).setBadge(0)
        thiscontext=container!!.context

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_first, container, false
        )
        binding.btnMobile.setOnClickListener {
            viewFragment(RechargeFragment(),"FRAGMENT_OTHER","mobile")
//
////            binding.bottomBar.setBadge(pos)
//            (requireActivity() as MainActivity).removeBadge(2)
//            (requireActivity() as MainActivity).setBadge(2)
        }
        binding.btnDth.setOnClickListener {
            viewFragment(RechargeFragment(),"FRAGMENT_OTHER","dth")
//
////            binding.bottomBar.setBadge(pos)
//            (requireActivity() as MainActivity).removeBadge(2)
//            (requireActivity() as MainActivity).setBadge(2)
        }
/*        binding.btnPostpaid.setOnClickListener {
            viewFragment(RechargeFragment(),"FRAGMENT_OTHER","postpaid")
        }*/

        binding.btnEleBil.setOnClickListener {
            gotoBbpsActivity("4","Electricity")
        }
        binding.btnBordband.setOnClickListener {
            gotoBbpsActivity("8","Broadband")
        }
        binding.btnCabeltv.setOnClickListener {
            gotoBbpsActivity("9","Cabel TV")
        }
        binding.btnCreditCard.setOnClickListener {
            gotoBbpsActivity("22","Credit Card")
        }
//        binding.btnDth.setOnClickListener {
//            gotoBbpsActivity("1","DTH")
//        }
        binding.btnPipedgas.setOnClickListener {
            gotoBbpsActivity("6","Piped Gas")
        }
        binding.btnInsurance.setOnClickListener {
            gotoBbpsActivity("5","Insurance")
        }
        binding.btnLadline.setOnClickListener {
            gotoBbpsActivity("2","Landline")
        }
        binding.btnLoan.setOnClickListener {
            gotoBbpsActivity("17","Loan Repayment")
        }
        binding.btnLpsgas.setOnClickListener {
            gotoBbpsActivity("11","LPG Gas")
        }
//        binding.btnPostpaid.setOnClickListener {
//            gotoBbpsActivity("3")
//        }
        binding.btnWaterbill.setOnClickListener {
            gotoBbpsActivity("21","Water Bill")
        }

        binding.btnFasttage.setOnClickListener {
            gotoBbpsActivity("12","Fast Tage")
        }

        binding.btnPostpaid.setOnClickListener {
//            viewFragment(RechargeFragment(),"FRAGMENT_OTHER","postpaid")
            gotoBbpsActivity("3","Postpaid")
        }

        ////////////////////////

        binding.btnMoneyTransfer.setOnClickListener {
            var intent = Intent(
                requireActivity(),
                DmtLoginAuthActivity::class.java
            )
            startActivity(
                intent
            )
        }
        binding.btnXpressDmt.setOnClickListener {
            var intent = Intent(
                requireActivity(),
                me.ibrahimsn.smoothbottombar.activity.x_dmt.DmtLoginAuthActivity::class.java
            )
            startActivity(
                intent
            )
        }

        banner()
        setData()
        setCard()

        binding.cardUtility.setOnClickListener {
            isUtility=true
            setCard()
        }
        binding.cardBcBanking.setOnClickListener {
            isUtility=false
            setCard()
        }

        return binding.root
    }

    var isUtility = true

    fun setCard() {

        if (isUtility) {

            binding.layoutUtilityService.visibility= View.VISIBLE
            binding.layoutBcService.visibility= View.GONE

            binding.cardUtility.setBackgroundResource(R.drawable.boder_selected)
            binding.cardBcBanking.setBackgroundResource(R.drawable.border4)
        } else {
            binding.cardUtility.setBackgroundResource(R.drawable.border4)
            binding.layoutUtilityService.visibility= View.GONE

            binding.layoutBcService.visibility= View.VISIBLE
            binding.cardBcBanking.setBackgroundResource(R.drawable.boder_selected)
        }

    }

    fun gotoBbpsActivity(service_Id:String,service_name:String) {
        var intent = Intent(
            requireActivity(),
            BBPSActivity::class.java
        )
        intent.putExtra("serviceId",service_Id)
        intent.putExtra("service_name",service_name)
        startActivity(
            intent
        )
//        activity?.overridePendingTransition(
//            me.ibrahimsn.smoothbottombar.R.anim.slide_out_bottom,
//            me.ibrahimsn.smoothbottombar.R.anim.slide_in_bottom
//        )
    }

    fun banner() {


        val urls = ArrayList<String>()
//        urls.add(SlideModel("https://images.livehindustan.com/uploadimage/library/2018/03/25/16_9/16_9_1/Jio__1521960343.jpg",""))
        urls.add("http://www.snpay.in/siteadmin/images/banner4.jpg")
        urls.add("http://www.snpay.in/siteadmin/images/banner2.jpg")
        urls.add("http://www.snpay.in/siteadmin/images/banner4.jpg")
//        urls.add(SlideModel("http://szzljy.com/images/mountain/mountain4.jpg",""))

        var sliderAdapter: SliderAdapter = SliderAdapter( urls)

        // on below line we are setting auto cycle direction
        // for our slider view from left to right.
        binding.banner.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // on below line we are setting adapter for our slider.
        binding.banner.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        binding.banner.scrollTimeInSec = 3

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        binding.banner.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        binding.banner.startAutoCycle()
    }



    private fun viewFragment(
        fragment: Fragment,
        name: String,type:String
    ) {
        var fragmentManager: FragmentManager = activity?.supportFragmentManager!!
        val fragmentTransaction =
            fragmentManager.beginTransaction()

        val bundle = Bundle()
        bundle.putString("type", type) // Put anything what you want
        fragment.setArguments(bundle)

        fragmentTransaction.replace(R.id.main_fragment, fragment)
        // 1. Know how many fragments there are in the stack
        val count = fragmentManager.backStackEntryCount
        // 2. If the fragment is **not** "home type", save it to the stack
        if (name == "FRAGMENT_OTHER") {
            fragmentTransaction.addToBackStack(name)
            fragmentTransaction.addToBackStack(name)
        }
        // Commit !


        fragmentTransaction.commit()
        // 3. After the commit, if the fragment is not an "home type" the back stack is changed, triggering the
        // OnBackStackChanged callback
        fragmentManager.addOnBackStackChangedListener(object :
            FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                // If the stack decreases it means I clicked the back button
                if (fragmentManager.backStackEntryCount <= count) {
                    // pop all the fragment and remove the listener
                    fragmentManager.popBackStack("FRAGMENT_OTHER", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    fragmentManager.removeOnBackStackChangedListener(this)
                    // set the home button selected
//                    binding.bottomBar.menu.getItem(0).setChecked(true)
                }
            }
        })
    }

    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        thiscontext=container!!.context
//
////        val textView=view.findViewById<TextView>(R.id.textView)
////        textView.setOnClickListener {
////            (requireActivity() as MainActivity).setSelectedItem(2)
////            (requireActivity() as MainActivity).removeBadge(2)
////        }
//
//
//    }
    fun setFram(fram: Fragment) {
        val fragmentManager: FragmentManager = activity?.supportFragmentManager!!
        val fragmentTransaction =
            fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fram)
        fragmentTransaction.commit()
    }
}
