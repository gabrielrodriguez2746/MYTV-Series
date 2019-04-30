package com.mytv.series

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mytv.detail.fragments.DetailFragment
import com.mytv.home.fragments.TVSeriesFragment
import com.mytv.series.base.listeners.OnFragmentInteraction
import com.mytv.series.base.ui.navigation.setupWithNavController
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, OnFragmentInteraction {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var navController: NavController

    private val isNavControllerInitialized: Boolean get() = ::navController.isInitialized

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnHome)
        bottomNavigationView.setupWithNavController(
            listOf(R.navigation.home, R.navigation.favorites),
            supportFragmentManager,
            R.id.flNavController
        ).observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
            this.navController = navController
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (isNavControllerInitialized) {
            navController.navigateUp()
        } else {
            false
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onItemClicked(fragment: String, id: String) {
        when (fragment) {
            TVSeriesFragment.FRAGMENT_NAME -> {
                val data = bundleOf(DetailFragment.SERIES_ID to id.toInt())
                navController.navigate(R.id.action_detail, data)
            }
            else -> Unit
        }

    }

}
