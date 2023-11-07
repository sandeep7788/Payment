package me.ibrahimsn.smoothbottombar.frag

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.background
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.gson.JsonObject
import me.ibrahimsn.smoothbottombar.MainActivity
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.activity.tab.SelectRecharge_Palne_Activity
import me.ibrahimsn.smoothbottombar.databinding.RechargeLayoutBinding
import me.ibrahimsn.smoothbottombar.dialog.BottomSheetDialog
import me.ibrahimsn.smoothbottombar.helper.ApiContants
import me.ibrahimsn.smoothbottombar.helper.ApiInterface
import me.ibrahimsn.smoothbottombar.helper.MyApplication
import me.ibrahimsn.smoothbottombar.helper.RetrofitManager
import me.ibrahimsn.smoothbottombar.helper.Utility
import me.ibrahimsn.smoothbottombar.model.CirclePojo
import me.ibrahimsn.smoothbottombar.model.OperatorPojo
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class RechargeFragment : Fragment(R.layout.recharge_layout) {

    lateinit var thiscontext: Context
    lateinit var binding : RechargeLayoutBinding
    var TAG="@@tag"
    var type=""

    fun setLayout() {
     binding.layDthNumber.visibility = View.VISIBLE
     binding.layCircle.visibility = View.GONE
     binding.layRecharge.visibility = View.GONE
     binding.layRecharge.visibility = View.GONE
     binding.txtBrowesPlane.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        (requireActivity() as MainActivity).setBadge(2)
//        (requireActivity() as MainActivity).setBadge(0)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.recharge_layout, container, false
        )

        thiscontext=container!!.context


        val bundle = this.arguments

        Log.e(TAG, bundle.toString())
        Log.e(TAG, ""+bundle!!.getString("type") )
        if (bundle != null) {
            type = bundle.getString("type").toString()
        }


        if (type!=null && type == "mobile") {
            binding.toolbarLayout1.texttitle.setText("Mobile Recharge")
        } else if (type!=null && type == "dth") {
            binding.toolbarLayout1.texttitle.setText("DTH Recharge")
            binding.image.setImageResource(R.drawable.dth)
            setLayout()
        } else if (type!=null && type == "postpaid") {
            binding.toolbarLayout1.texttitle.setText("Postpaid Recharge")
        }

        binding.toolbarLayout1.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.edtOperator.setOnClickListener {
            val bottomSheet = BottomSheetDialog("operator",opList,null,null)
            bottomSheet.setTargetFragment(this, 101)
            bottomSheet.show(parentFragmentManager, "tag")

        }
        binding.edtCircle.setOnClickListener {
            val bottomSheet = BottomSheetDialog("circle",null,circleList,null)
            bottomSheet.setTargetFragment(this, 102)
            bottomSheet.show(parentFragmentManager, "tag")

        }
        binding.txtBrowesPlane.setOnClickListener {

            var intent = Intent(context, SelectRecharge_Palne_Activity::class.java);
            intent.putExtra("circleId",circleId)
            intent.putExtra("operatorId",operatorId)
            startActivity(intent)
        }

        binding.btnProcess.setOnClickListener {
            binding.l1.setBackgroundResource(R.drawable.border)
            binding.l2.setBackgroundResource(R.drawable.border)
            binding.l3.setBackgroundResource(R.drawable.border)
            binding.l4.setBackgroundResource(R.drawable.border)
            binding.l5.setBackgroundResource(R.drawable.border)
            binding.l1DthNumber.setBackgroundResource(R.drawable.border)

            when {
                (type == "mobile") && binding.edtNumber.text.isEmpty() -> {
                    Utility.showSnackBar(requireActivity(), "Please enter your number")
                    binding.l1.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(requireActivity())
                }

                (type == "mobile" || type == "dth") && binding.edtOperator.text.isEmpty() -> {
                    Utility.showSnackBar(requireActivity(), "Please select your operator")
                    binding.l2.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(requireActivity())
                }
                (type == "dth") && binding.edtDthNumber.text.isEmpty() -> {
                    Utility.showSnackBar(requireActivity(), "Please enter your number")
                    binding.l1DthNumber.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(requireActivity())
                }
                (type == "mobile") && binding.edtCircle.text.isEmpty() -> {
                    Utility.showSnackBar(requireActivity(), "Please select your circle")
                    binding.l3.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(requireActivity())
                }
                (type == "mobile" || type == "dth") && binding.edtAmount.text.isEmpty() -> {
                    Utility.showSnackBar(requireActivity(), "Please enter your amount")
                    binding.l4.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(requireActivity())
                }
                (type == "mobile" || type == "dth") && ((binding.edtAmount.text.toString().trim()).toInt() == 0) -> {
                    Utility.showSnackBar(requireActivity(), "Please enter valid amount")
                    binding.l4.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(requireActivity())
                }
                (type == "mobile" || type == "dth") && binding.edtKey.text.isEmpty() -> {
                    Utility.showSnackBar(requireActivity(), "Please enter your transaction pin")
                    binding.l5.setBackgroundResource(R.drawable.edit_txt_error)
                    Utility.hideKeyboard(requireActivity())
                }
                else -> {
                    Utility.hideKeyboard(requireActivity())

                    rechargeAuth()
                }
            }
        }

//        binding.edtNumber.doOnTextChanged { text, start, count, after ->
//            if (binding.edtNumber.text.toString().length == 10) {
//                getOperatorId()
//            }
//        }

        binding.edtNumber.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (binding.edtNumber.text.toString().length == 10) {
                    getOperatorId()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })




        if (type == "mobile") {
            getOperator("Prepaid")
            getCircleList()
        }else if (type == "dth") {
            getOperator("dth")
        }


        return binding.root
    }

    var opList = ArrayList<OperatorPojo>();
    var circleList = ArrayList<CirclePojo>();

    fun rechargeAuth()     {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnProcess.visibility = View.GONE

        var apiInterface: ApiInterface = context?.let { RetrofitManager().instance }!!.create(ApiInterface::class.java)

        var amount="";
        if (binding.edtAmount.text.toString().contains("₹")) {
            amount = (binding.edtAmount.text.toString().replace("₹", "")).trim()
        } else {
amount=            binding.edtAmount.text.toString().trim()
        }
        
        Log.e(TAG, "rechargeAuth: "+
                MyApplication.getUserId()+" "+
            binding.edtNumber.text.toString()+" "+
            operatorId+" "+
                amount+" "+
                circleId+" "+
                binding.edtKey.text.toString().trim());



        apiInterface.rechargeAuth(
            MyApplication.getUserId()?.trim(),
            binding.edtNumber.text.toString().trim(),
            operatorId.trim(),
            amount,
            circleId,
            binding.edtKey.text.toString().trim()
            ).
        enqueue(object : Callback<JsonObject>
        {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context," "+t.message.toString(), Toast.LENGTH_LONG).show()
                binding.mainProgress.visibility = View.GONE
                binding.btnProcess.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                binding.mainProgress.visibility = View.GONE
                binding.btnProcess.visibility = View.VISIBLE

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

                        AwesomeDialog.build(activity!!)
                            .title("Congratulations")
                            .body(msg)
                            .icon(R.drawable.ic_congrts)
//                            .background(R.drawable.green_button_background)
                            .onPositive("Go To Home") {
                                Log.d("TAG", "positive ")
                                activity?.startActivity(Intent(thiscontext,MainActivity::class.java))
                            }

                    } else {
//                        msg = "Bad Response when get balance.";
                        AwesomeDialog.build(activity!!)
                            .title("Something Wrong")
                            .body(msg)
//                            .background(R.drawable.edit_txt_error)
//                            .icon(R.drawable.baseline_error_outline_24)
                            .onPositive("Retry") {
                                Log.d("TAG", "positive ")
                            }
                    }
                }else{

                    Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                }

            }

        })
    }

    fun getOperator(type:String)     {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnProcess.visibility = View.GONE

        var apiInterface: ApiInterface = context?.let { RetrofitManager().instance }!!.create(ApiInterface::class.java)

        apiInterface.getOperator(type).
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
                                opList.add(OperatorPojo(json_Array.getJSONObject(i).getString("operator_id"),
                                    json_Array.getJSONObject(i).getString("operator_name")));
                            }

                        }else{
//                            Toast.makeText(context,"Bad Response when get balance.", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
//                        Toast.makeText(context,"Bad Response when get balance.", Toast.LENGTH_LONG).show()
                    }
                }else{

                    Toast.makeText(context,"Bad Response when get balance.", Toast.LENGTH_LONG).show()
                }

            }

        })
    }



    fun getOperatorId()     {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnProcess.visibility = View.GONE

        var apiInterface: ApiInterface = context?.let { RetrofitManager().instance }!!.create(ApiInterface::class.java)

        apiInterface.getOperatorId(binding.edtNumber.text.toString()).
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

                        operatorId = jsonObject.getInt("operator_id").toString()
                        circleId = jsonObject.getInt("circle_id").toString()
                        setOperationData()

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

    fun setOperationData() {
        for (i in 0 until circleList.size) {
            if (circleList.get(i).circle_id.toString().equals(circleId.toString().trim(),true)) {
                binding.edtCircle.setText(circleList.get(i).circle_name)
                break
            }
        }
        for (i in 0 until opList.size) {
            if (opList.get(i).operator_id.toString().equals(operatorId.toString().trim(),true)) {
                binding.edtOperator.setText(opList.get(i).operator_name)
                break
            }
        }
    }

    fun getCircleList()     {
        binding.mainProgress.visibility = View.VISIBLE
        binding.btnProcess.visibility = View.GONE

        var apiInterface: ApiInterface = context?.let { RetrofitManager().instance }!!.create(ApiInterface::class.java)

        apiInterface.getCircleList().
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
                                circleList.add(
                                    CirclePojo(json_Array.getJSONObject(i).getString("circle_id"),
                                    json_Array.getJSONObject(i).getString("circle_name"))
                                );
                            }

                        }else{
//                            Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
//                        Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                    }
                }else{

                    Toast.makeText(context,"Bad Response", Toast.LENGTH_LONG).show()
                }

            }

        })
    }

    override fun onResume() {
        super.onResume()
        binding.edtAmount.setText(MyApplication.ReadStringPreferences(ApiContants.PREF_TAB))
    }

    var operatorId = "0";
    var circleId = "1";

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            operatorId = data?.getStringExtra("resultid").toString()
            val resultValue = data?.getStringExtra("resultvalue")
            binding.edtOperator.text = resultValue
        } else if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            circleId = data?.getStringExtra("resultid").toString()
            val resultValue = data?.getStringExtra("resultvalue")
            binding.edtCircle.text = resultValue
        }
    }

}
