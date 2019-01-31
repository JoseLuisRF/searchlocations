package com.arusoft.mobile.searchlocations.presentation.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.arusoft.mobile.searchlocations.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
    }
}
