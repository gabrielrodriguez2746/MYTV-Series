package com.mytv.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mytv.series.base.ui.navigation.setupWithNavController
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var navigationMap: @JvmSuppressWildcards Map<Int, Int>

    private lateinit var navController: NavController

    private val isNavControllerInitialized: Boolean get() = ::navController.isInitialized

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navigationList: List<Int> = List(navigationMap.size) {
            navigationMap.getValue(it)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnHome)
        bottomNavigationView.setupWithNavController(
            navigationList,
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

}
