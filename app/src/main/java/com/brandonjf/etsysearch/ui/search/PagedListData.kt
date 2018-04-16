package com.brandonjf.etsysearch.ui.search

import android.arch.paging.PagedList
import com.brandonjf.etsysearch.ui.search.data.source.NetworkState
import io.reactivex.Observable

data class PagedListData<T>(val pagedList: Observable<PagedList<T>>, val networkState: Observable<NetworkState>)