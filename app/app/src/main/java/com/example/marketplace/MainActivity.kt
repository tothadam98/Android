package com.example.marketplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.marketplace.fragments.TimelineFragment
import com.example.marketplace.fragments.OngoingSalesFragment
import com.example.marketplace.fragments.MyMarketFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.bottom_navigation

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    private val myMarketFragment = MyMarketFragment()
    private val timelineFragment = TimelineFragment()
    private val myFaresFragment = OngoingSalesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        var navView : BottomNavigationView = findViewById(R.id.bottom_navigation)

        navView.visibility = View.GONE

        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.timeline -> {
                    navController.navigate(R.id.listFragment)
                }
                R.id.myMarket -> {
                    navController.navigate(R.id.myMarketFragment)
                }
                R.id.MyFares ->{
                    navController.navigate(R.id.myFaresFragment)
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}