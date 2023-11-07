package me.ibrahimsn.smoothbottombar.activity.moneytransfer

import me.ibrahimsn.smoothbottombar.model.tab.MoneyTransferPojo

interface OnDataPassListener2 {
    fun onDataPassed(id: String,pos:Int , type:String,data: MoneyTransferPojo)
}
