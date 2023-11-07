package me.ibrahimsn.smoothbottombar

import androidx.fragment.app.Fragment
import me.ibrahimsn.smoothbottombar.model.OperatorPojo

class YourCallingFragment : Fragment() {

    fun onOperatorSelected(operator: OperatorPojo?) {
        // Handle the selected operator here
        if (operator != null) {
            // Do something with the selected operator
        }
    }
}
