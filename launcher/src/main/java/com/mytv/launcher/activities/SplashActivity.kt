package com.mytv.launcher.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mytv.series.base.keys.ActivityClassIntent
import com.mytv.series.base.keys.HOME_ACTIVITY_KEY
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var nextActivityClass: @JvmSuppressWildcards Map<String, ActivityClassIntent>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, nextActivityClass.getValue(HOME_ACTIVITY_KEY).getClassIntent().java))
        finish()
    }

}
