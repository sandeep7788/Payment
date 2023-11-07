package me.ibrahimsn.smoothbottombar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.smoothbottombar.databinding.ActivityMainBinding
import me.ibrahimsn.smoothbottombar.frag.RechargeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        navController = findNavController(R.id.main_fragment)
//        setupActionBarWithNavController(navController)
//        setupSmoothBottomMenu()
        viewFragment(FirstFragment(),"");

        binding.bottomBar.setOnItemSelectedListener {
            if (it == 0) {
                viewFragment(FirstFragment(),"");
            } else if (it == 1) {
                viewFragment(SecondFragment(),"FRAGMENT_OTHER");
            } else if (it == 2) {
                viewFragment(ThirdFragment(),"FRAGMENT_OTHER");
            } else if (it == 3) {
                viewFragment(FourthFragment(),"FRAGMENT_OTHER");
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.another_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.another_item_1 -> {
                showToast("Another Menu Item 1 Selected")
            }

            R.id.another_item_2 -> {
                showToast("Another Menu Item 2 Selected")
            }

            R.id.another_item_3 -> {
                showToast("Another Menu Item 3 Selected")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //set an active fragment programmatically
    public fun setSelectedItem(pos:Int){
        binding.bottomBar.setSelectedItem(pos)
    }
    public fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater);
    }
    //set badge indicator
    fun setBadge(pos:Int){
        binding.bottomBar.setBadge(pos)
    }
    //remove badge indicator
    fun removeBadge(pos:Int){
        binding.bottomBar.removeBadge(pos)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun setFram(fram: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fram)
        fragmentTransaction.commit()
    }
    private fun viewFragment(
        fragment: Fragment,
        name: String
    ) {
        var fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fragment)
        // 1. Know how many fragments there are in the stack
        val count = fragmentManager.backStackEntryCount
        // 2. If the fragment is **not** "home type", save it to the stack
        if (name == "FRAGMENT_OTHER") {
            fragmentTransaction.addToBackStack(name)
        } else {
            binding.bottomBar.menu.getItem(0).setChecked(true)
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

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu_bottom)
        val menu = popupMenu.menu
        //binding.bottomBar.setupWithNavController(menu, navController)
        binding.bottomBar.setupWithNavController( navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
