package me.ibrahimsn.smoothbottombar.dialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.activity.OnDataPassListener
import me.ibrahimsn.smoothbottombar.databinding.BottomSheetLayoutBinding
import me.ibrahimsn.smoothbottombar.model.CirclePojo
import me.ibrahimsn.smoothbottombar.model.OperatorPojo
import me.ibrahimsn.smoothbottombar.model.bbpsServiceModel.Datum


class BottomSheetDialog(var type:String, var opList: ArrayList<OperatorPojo>?, var list: ArrayList<CirclePojo>?
, var bbpsList: ArrayList<Datum>?) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding
    private var selectedOperator: OperatorPojo? = null
    private var selectedState: CirclePojo? = null
    private var selectedBBps: Datum? = null

    // Sample data

/*
    var stateList = listOf(
        StatePojo("AP", "Andhra Pradesh"),
        StatePojo("AR", "Arunachal Pradesh"),
        StatePojo("AS", "Assam"),
        StatePojo("BR", "Bihar"),
        StatePojo("CT", "Chhattisgarh"),
        StatePojo("GA", "Goa"),
        StatePojo("GJ", "Gujarat"),
        StatePojo("HR", "Haryana"),
        StatePojo("HP", "Himachal Pradesh"),
        StatePojo("JH", "Jharkhand"),
        StatePojo("KA", "Karnataka"),
        StatePojo("KL", "Kerala"),
        StatePojo("MP", "Madhya Pradesh"),
        StatePojo("MH", "Maharashtra"),
        StatePojo("MN", "Manipur"),
        StatePojo("ML", "Meghalaya"),
        StatePojo("MZ", "Mizoram"),
        StatePojo("NL", "Nagaland"),
        StatePojo("OD", "Odisha"),
        StatePojo("PB", "Punjab"),
        StatePojo("RJ", "Rajasthan"),
        StatePojo("SK", "Sikkim"),
        StatePojo("TN", "Tamil Nadu"),
        StatePojo("TG", "Telangana"),
        StatePojo("TR", "Tripura"),
        StatePojo("UP", "Uttar Pradesh"),
        StatePojo("UK", "Uttarakhand"),
        StatePojo("WB", "West Bengal")
    )
*/


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
//        if (list!=null) {
//            this.opList= list!!
//        }

        if (type.equals("circle",true)) {
            binding.textViewItem.setText("Select Circle")
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = StateAdapter(list!!)
            recyclerView.adapter = adapter
        } else if(opList!=null){
            binding.textViewItem.setText("Select Operator")
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = OperatorAdapter(opList!!)
            recyclerView.adapter = adapter
        } else if (bbpsList !=null) {
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = BBPSAdapter(bbpsList!!)
            recyclerView.adapter = adapter
        }

        binding.btnSearch.setOnClickListener {
            binding.btnSearch.visibility=View.GONE
            binding.lSearch.visibility=View.VISIBLE
        }
        binding.edtOperator.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                // When user changed the Text
                if (type.equals("circle", true)) {
                    // Filter the StateAdapter data based on the user's input
                    val filteredList = list?.filter { it.circle_name.contains(cs, true) } ?: emptyList()
                    val adapter = StateAdapter(filteredList)
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapter
                } else if (opList != null) {
                    // Filter the OperatorAdapter data based on the user's input
                    val filteredList = opList!!.filter { it.operator_name.contains(cs, true) }
                    val adapter = OperatorAdapter(filteredList)
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapter
                } else if (bbpsList != null) {
                    // Filter the BBPSAdapter data based on the user's input
                    val filteredList = bbpsList!!.filter { it.billerName.contains(cs, true) }
                    val adapter = BBPSAdapter(filteredList)
                    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapter
                }

            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {}
        })


        return view
    }

    private var onDataPassListener: OnDataPassListener? = null


    inner class OperatorAdapter(private val operators: List<OperatorPojo>) :
        RecyclerView.Adapter<OperatorAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textViewItem: TextView = view.findViewById(R.id.textViewItem)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val operator = operators[position]
            holder.textViewItem.text = operator.getOperator_name()
            holder.itemView.setOnClickListener {
                selectedOperator = operator
                // Handle the item click here, if needed.
                val targetFragment = targetFragment
                if (targetFragment != null) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("type", type)
                    resultIntent.putExtra("resultid", selectedOperator?.operator_id) // Customize this with your result data
                    resultIntent.putExtra("resultvalue", selectedOperator?.operator_name) // Customize this with your result data
                    targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, resultIntent)
                    dismiss()
                }

            }
        }

        override fun getItemCount(): Int {
            return operators.size
        }
    }

    inner class BBPSAdapter(private val operators: List<Datum>) :
        RecyclerView.Adapter<BBPSAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textViewItem: TextView = view.findViewById(R.id.textViewItem)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return ViewHolder(view)
        }




        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val operator = operators[position]
            holder.textViewItem.text = operator.billerName
            holder.itemView.setOnClickListener {
                selectedBBps = operator
                // Handle the item click here, if needed.
                val targetFragment = targetFragment
//                if (targetFragment != null) {
                    val resultIntent = Intent()
//                    resultIntent.putExtra("type", type)
//                    resultIntent.putExtra("biller_id", selectedBBps?.billerId)
//                    resultIntent.putExtra("billerName", selectedBBps?.billerName)
//                    resultIntent.putExtra("billerAliasName", selectedBBps?.billerAliasName)
//                    resultIntent.putExtra("is_fetch", selectedBBps?.isFetch)
//                    onActivityResult(targetRequestCode, Activity.RESULT_OK, resultIntent)
//                    startActivityForResult(resultIntent,101)
                onDataPassListener?.onDataPassed(type, selectedBBps?.billerId.toString(), selectedBBps?.billerName.toString(),
                    selectedBBps?.billerAliasName.toString(),selectedBBps?.isFetch.toString()
                    )
                    dismiss()
//                }

            }
        }

        override fun getItemCount(): Int {
            return operators.size
        }
    }

    inner class StateAdapter(private val operators: List<CirclePojo>) :
        RecyclerView.Adapter<StateAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textViewItem: TextView = view.findViewById(R.id.textViewItem)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val operator = operators[position]
            holder.textViewItem.text = operator.circle_name
            holder.itemView.setOnClickListener {
                selectedState = operator
                // Handle the item click here, if needed.
                val targetFragment = targetFragment
                if (targetFragment != null) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("type", type)
                    resultIntent.putExtra("resultid", selectedState?.circle_id)
                    resultIntent.putExtra("resultvalue", selectedState?.circle_name) // Customize this with your result data
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, resultIntent)
                    dismiss()
                }

            }
        }

        override fun getItemCount(): Int {
            return operators.size
        }
    }

/*    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

//        val parentFragment = parentFragment
//        if (parentFragment is YourCallingFragment) {
//            parentFragment.onOperatorSelected(selectedOperator)
//        }
        val targetFragment = targetFragment
        if (targetFragment != null) {
            val resultIntent = Intent()
            resultIntent.putExtra("resultid", "resultValue") // Customize this with your result data
            resultIntent.putExtra("resultvalue", "resultValue") // Customize this with your result data
            targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, resultIntent)
        }
    }*/
}
