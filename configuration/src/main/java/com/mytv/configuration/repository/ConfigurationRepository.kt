package com.mytv.configuration.repository

import com.google.gson.JsonElement
import com.mytv.configuration.rest.ConfigurationService
import com.mytv.data.models.Configuration
import com.mytv.series.base.mappers.BaseMapper
import com.mytv.series.base.repositories.BaseRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(
    private val configurationService: ConfigurationService,
    private val mapper: @JvmSuppressWildcards BaseMapper<JsonElement, Configuration>
) : @JvmSuppressWildcards BaseRepository<Any, Configuration> {

    private lateinit var cachedValue : Configuration
    private val cachedSubject = BehaviorSubject.create<Configuration>()

    override fun getSingleListData(parameters: Any): Single<List<Configuration>> = Single.just(emptyList())

    override fun getObservableData(): Observable<Configuration> {
        return cachedSubject
    }

    override fun getCompletableData(): Completable {
        return configurationService.getConfiguration()
            .subscribeOn(Schedulers.io())
            .map { mapper.getFromElement(it.getAsJsonObject("images")) }
            .flatMapCompletable { configuration ->
                Completable.fromAction {
                    cachedValue = configuration
                }
            }.doOnComplete {
                cachedSubject.onNext(cachedValue)
            }
    }

    override fun getSyncData(): Configuration {
        return cachedValue
    }
}