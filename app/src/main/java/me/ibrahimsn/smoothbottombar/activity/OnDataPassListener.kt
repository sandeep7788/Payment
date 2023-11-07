package me.ibrahimsn.smoothbottombar.activity

interface OnDataPassListener {
    fun onDataPassed(type: String,
                     biller_id: String,
                     billerName: String,
                     billerAliasName: String,
                     is_fetch: String)
}
