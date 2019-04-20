package com.mytv.series.base.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * ase class for repositories, this for keep modules independent TODO Improve a lot, there will be unused methods
 */
interface BaseRepository<in T, R> {

    fun getSingleListData(parameters: T): Single<List<R>>

    fun getObservableData(): Observable<R>

    fun getCompletableData() : Completable
}