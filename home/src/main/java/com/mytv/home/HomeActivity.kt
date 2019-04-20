package com.mytv.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mytv.data.models.TVSeries
import com.mytv.series.base.repositories.BaseRepository
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: @JvmSuppressWildcards BaseRepository<Int, TVSeries>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val disposable = repository.getSingleListData(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { Log.d("GABRIEL", "list :: $it") }
    }
}
