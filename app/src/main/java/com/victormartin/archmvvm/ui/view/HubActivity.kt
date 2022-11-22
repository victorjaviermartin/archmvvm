package com.victormartin.archmvvm.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.victormartin.archmvvm.R
import com.victormartin.archmvvm.databinding.ActivityHubBinding
import com.victormartin.archmvvm.ui.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class HubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHubBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    val dataViewModel: DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onResume() {
        super.onResume()

        navigateToLoading()

        runBlocking {
            dataViewModel.getEastWestIcons(this@HubActivity)
            dataViewModel.getAllPlayers(0)
        }

    }

    fun navigateToLoading(){
        navController.navigate(R.id.navloadingFragment)
    }

    fun navigateToListPlayers(){
        navController.navigate(R.id.navListItemsFragment)
    }

    fun navigateToDetailPlayer(){
        navController.navigate(R.id.navDetailFragment)
    }

}