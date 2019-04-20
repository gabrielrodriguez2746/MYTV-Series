package com.mytv.top.series.repository

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mytv.data.models.TVSeries
import com.mytv.series.base.mappers.BaseMapper
import com.mytv.series.base.repositories.BaseRepository
import com.mytv.top.series.helpers.getDefaultInvalidTVSeries
import com.mytv.top.series.rest.TopSeriesService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopSeriesRepository @Inject constructor(
    private val service: TopSeriesService,
    private val mapper: @JvmSuppressWildcards BaseMapper<JsonElement, TVSeries>
) :
    @JvmSuppressWildcards BaseRepository<Int, TVSeries> {

    override fun getSingleListData(parameters: Int): Single<List<TVSeries>> {
        return getSingleResultsArray(parameters).flatMap { resultsArray -> processResponseItems(resultsArray).toList() }
    }

    override fun getObservableData(parameters: Int): Observable<TVSeries> {
        return getSingleResultsArray(parameters).flatMapObservable(::processResponseItems)
    }

    override fun getCompletableData(): Completable {
        return Completable.complete()
    }

    internal fun processResponseItems(resultsArray: JsonArray): Observable<TVSeries> {
        return Observable.fromIterable(resultsArray)
            .map(mapper::getFromElement)
            .onErrorReturnItem(getDefaultInvalidTVSeries())
            .filter { it.id != -1 }
    }

    internal fun getSingleResultsArray(parameters: Int): Single<JsonArray> {
        return service.getPopularTvSeries(parameters)
            .onErrorReturnItem(JsonObject())
            .subscribeOn(Schedulers.io())
            .map { it.getAsJsonArray("results") }
    }

}