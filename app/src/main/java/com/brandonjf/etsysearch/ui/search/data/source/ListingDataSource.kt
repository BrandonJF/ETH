package com.brandonjf.etsysearch.ui.search.data.source

import android.arch.paging.PageKeyedDataSource
import com.brandonjf.etsysearch.ActiveListing
import com.brandonjf.etsysearch.ActiveListingResponse
import com.brandonjf.etsysearch.EtsyService
import com.brandonjf.etsysearch.ui.common.applyObservableSchedulers
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class ListingDataSource @Inject constructor(
    private val service: EtsyService,
    private val keywords: String,
    private var compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, ActiveListing>() {

    private val networkState = BehaviorSubject.create<NetworkState>()

    fun getNetworkState(): Observable<NetworkState>? {
        return networkState
            .toFlowable(BackpressureStrategy.LATEST)
            .toObservable()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ActiveListing>) {
        service.getListingsByPage(keywords, INITIAL_PAGE_KEY)
            .compose(applyObservableSchedulers())
            .compose(applyNetworkState())
            .doOnError(this::handleLoadError)
            .subscribe({ response: ActiveListingResponse ->
                onLoadInitialSuccess(callback, response )},
                this::handleLoadError)
            .let(compositeDisposable::add)
    }

    fun onLoadInitialSuccess(callback:LoadInitialCallback<Int,ActiveListing>, response: ActiveListingResponse) {
        callback.onResult(
            response.results,
            response.pagination.effectiveOffset,
            response.count,
            null,
            response.pagination.nextPage
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ActiveListing>) {
        service.getListingsByPage(keywords, params.key)
            .compose(applyObservableSchedulers())
            .compose(applyNetworkState())
            .subscribe({ response ->
            with(response) {
                callback.onResult(results, pagination.nextPage)
            }
        }, this::handleLoadError)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ActiveListing>) {
        Timber.e("LoadBefore called on DataSource")
    }

    private fun handleLoadError(t: Throwable?) {
        Timber.e("Could not load listings from API because: $t")
    }

    fun <T> applyNetworkState(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream
                .doOnSubscribe{networkState.onNext(NetworkState.Loading)}
                .doFinally{networkState.onNext(NetworkState.Loaded)}
        }
    }
}

private const val INITIAL_PAGE_KEY = 1