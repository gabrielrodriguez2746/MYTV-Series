package com.mytv.home.viewModels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.mytv.data.models.TVSeries
import com.mytv.series.base.di.ViewModelKey
import com.mytv.series.base.repositories.BaseRepository
import com.mytv.series.base.ui.generics.RecyclerItem
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import kotlin.reflect.KProperty

class TVSeriesViewModel @Inject constructor(private val repository: @JvmSuppressWildcards BaseRepository<Int, TVSeries>) :
    ViewModel(), LifecycleObserver {


    private val compositeDisposable = CompositeDisposable()

    private val dataController: PageKeyedDataSource<Int, TVSeries> by DataController()
    private val dataFactory: DataSource.Factory<Int, RecyclerItem<TVSeries, String>> by DataFactory()
    private val configuration: PagedList.Config
        get() {
            return PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(30)
                .setPageSize(20)
                .build()
        }

    val itemsLiveData: LiveData<PagedList<RecyclerItem<TVSeries, String>>> by lazy {
        LivePagedListBuilder<Int, RecyclerItem<TVSeries, String>>(dataFactory, configuration).build()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        compositeDisposable.clear()
    }

    inner class DataController : PageKeyedDataSource<Int, TVSeries>() {

        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TVSeries>) {
            compositeDisposable += repository.getSingleListData(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = Throwable::printStackTrace,
                    onSuccess = { items -> callback.onResult(items, null, if (items.isEmpty()) null else 2) })
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TVSeries>) {
            compositeDisposable += repository.getSingleListData(params.key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = Throwable::printStackTrace,
                    onSuccess = { items -> callback.onResult(items, if (items.isEmpty()) null else params.key + 1) })
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TVSeries>) = Unit

        operator fun getValue(thisRef: ViewModel, property: KProperty<*>): PageKeyedDataSource<Int, TVSeries> = this

    }

    inner class DataFactory : DataSource.Factory<Int, RecyclerItem<TVSeries, String>>() {

        override fun create(): DataSource<Int, RecyclerItem<TVSeries, String>> {
            return dataController.map {
                object : RecyclerItem<TVSeries, String> {
                    override fun getType(): Int = 1
                    override fun getId(): Int = it.id
                    override fun getComparator(): Any? = null
                    override fun getContent(): TVSeries = it
                    override fun getDiffResolver(): String? = null

                }
            }
        }

        operator fun getValue(
            thisRef: ViewModel,
            property: KProperty<*>
        ): DataSource.Factory<Int, RecyclerItem<TVSeries, String>> = this
    }
}


@Module
abstract class TVSeriesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TVSeriesViewModel::class)
    abstract fun bindTVSeriesViewModel(viewModel: TVSeriesViewModel): ViewModel
}