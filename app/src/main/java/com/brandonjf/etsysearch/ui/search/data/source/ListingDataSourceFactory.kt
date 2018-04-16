package com.brandonjf.etsysearch.ui.search.data.source

import android.arch.paging.DataSource
import com.brandonjf.etsysearch.ActiveListing
import com.brandonjf.etsysearch.EtsyService
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class ListingDataSourceFactory(
    private val service: EtsyService,
    private val keywords: String,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, ActiveListing>() {

    private val dataSourceObservable = BehaviorSubject.create<ListingDataSource>()

    fun getDataSource(): Observable<ListingDataSource> {
        return dataSourceObservable.toFlowable(BackpressureStrategy.LATEST).toObservable()
    }

    override fun create(): DataSource<Int, ActiveListing> {
        ListingDataSource(service, keywords, compositeDisposable).let { source ->
            dataSourceObservable.onNext(source)
            return source
        }
    }
}