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
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(
    private val configurationService: ConfigurationService,
    private val mapper: @JvmSuppressWildcards BaseMapper<JsonElement, Configuration>
) : @JvmSuppressWildcards BaseRepository<Any, Configuration> {

    override fun getSingleListData(parameters: Any): Single<List<Configuration>> = Single.just(emptyList())

    override fun getObservableData(): Observable<Configuration> {
        return configurationService.getConfiguration()
            .subscribeOn(Schedulers.io())
            .map { it.getAsJsonObject("images") }
            .flatMapObservable { Observable.just(mapper.getFromElement(it)) }
    }

    override fun getCompletableData(): Completable = Completable.complete()
}