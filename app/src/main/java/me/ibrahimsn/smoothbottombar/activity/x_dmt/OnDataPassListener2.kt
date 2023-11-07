package me.ibrahimsn.smoothbottombar.activity.x_dmt

import me.ibrahimsn.smoothbottombar.model.tab.MoneyTransferPojo

interface OnDataPassListener2 {
    fun onDataPassed(id: String,pos:Int , type:String,data: MoneyTransferPojo)
}
