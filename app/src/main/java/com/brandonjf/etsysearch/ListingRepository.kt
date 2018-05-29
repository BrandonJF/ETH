package com.brandonjf.etsysearch

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.brandonjf.etsysearch.ui.search.PagedListData
import com.brandonjf.etsysearch.ui.search.data.source.ListingDataSourceFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListingRepository(var service: EtsyService, var compositeDisposable: CompositeDisposable) : ListingRepositoryContract {

    override fun getListingsByPage(keywords: String, page: String?): PagedListData<ActiveListing> {

        val factory = createListingDataSourceFactory(keywords)
        val pagedList = createPagedListObservableFromFactory(factory)
        val dataSourceNetworkState = factory.getDataSource().switchMap { it.getNetworkState() }

        return PagedListData(pagedList, dataSourceNetworkState )

    }

    private fun createPagedListObservableFromFactory(factory: ListingDataSourceFactory): Observable<PagedList<ActiveListing>> {
        val config = createPagedListConfig()
        return RxPagedListBuilder(factory, config)
            .setFetchScheduler(Schedulers.io())
            .setNotifyScheduler(AndroidSchedulers.mainThread())
            .buildObservable()
    }

    private fun createListingDataSourceFactory(keywords: String) = ListingDataSourceFactory(service, keywords, compositeDisposable)

    private fun createPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(PAGED_LIST_ENABLE_PLACEHOLDERS)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setPageSize(LISTING_REQUEST_LIMIT).build()
    }
}

private const val LISTING_REQUEST_LIMIT = 80
private const val PREFETCH_DISTANCE = 12
private const val PAGED_LIST_ENABLE_PLACEHOLDERS = false
