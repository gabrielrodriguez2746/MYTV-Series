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
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class TopSeriesRepository @Inject constructor(
    private val service: TopSeriesService,
    private val mapper: @JvmSuppressWildcards BaseMapper<JsonElement, TVSeries>
) : @JvmSuppressWildcards BaseRepository<Int, TVSeries> {

    private val _cachedResults = mutableMapOf<Int, List<TVSeries>>()
    private val cachedResults: Map<Int, List<TVSeries>> get() = _cachedResults
    private val dataSubject = BehaviorSubject.createDefault<List<TVSeries>>(emptyList())

    override fun getSingleListData(parameters: Int): Single<List<TVSeries>> {
        return if (cachedResults.containsKey(parameters)) {
            val pageResults = cachedResults.getValue(parameters)
            if (pageResults.isEmpty()) {
                getSingleResultsArray(parameters)
                    .flatMap { resultsArray -> processResponseItems(resultsArray).toList() }
                    .doOnSuccess {
                        _cachedResults[parameters] = it
                        dataSubject.onNext(cachedResults.flatMap { results -> results.value })
                    }
            } else {
                Single.just(pageResults)
            }
        } else {
            getSingleResultsArray(parameters)
                .flatMap { resultsArray -> processResponseItems(resultsArray).toList() }
                .doOnSuccess { _cachedResults[parameters] = it }
        }

    }

    override fun getObservableData(): Observable<TVSeries> {
        return dataSubject.distinctUntilChanged()
            .subscribeOn(Schedulers.computation())
            .flatMap { Observable.fromIterable(it) }
    }

    override fun getCompletableData(): Completable {
        return Completable.complete()
    }

    override fun getSyncData(): TVSeries {
        TODO("unused")
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