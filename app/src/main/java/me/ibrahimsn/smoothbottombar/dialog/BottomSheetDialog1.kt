package me.ibrahimsn.smoothbottombar.dialog

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.ibrahimsn.smoothbottombar.R
import me.ibrahimsn.smoothbottombar.databinding.BottomSheetLayoutBinding
import me.ibrahimsn.smoothbottombar.model.OperatorPojo
import android.widget.TextView
import me.ibrahimsn.smoothbottombar.activity.OnDataPassListener
import me.ibrahimsn.smoothbottombar.model.CirclePojo
import me.ibrahimsn.smoothbottombar.model.bbpsServiceModel.Datum

class BottomSheetDialog1(var type:String, var bbpsList: ArrayList<Datum>?,var onDataPassListener: OnDataPassListener,
    var service_name:String) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding
    private var selectedOperator: OperatorPojo? = null
    private var selectedState: CirclePojo? = null
    private var selectedBBps: Datum? = null

    // Sample data



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
//        if (list!=null) {
//            this.opList= list!!
//        }

        binding.textViewItem.text=service_name + " "+"Operators"
        if (bbpsList !=null) {
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

                // Filter the StateAdapter data based on the user's input
                val filteredList = bbpsList?.filter { it.billerName.contains(cs, true) } ?: emptyList()
                val adapter = BBPSAdapter(filteredList)
                val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter = adapter


            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {}
        })

        return view
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
