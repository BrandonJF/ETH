package com.brandonjf.etsysearch.ui.search.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.brandonjf.etsysearch.ActiveListing
import com.brandonjf.etsysearch.ListingRepositoryContract
import com.brandonjf.etsysearch.ui.common.BaseObservableViewModel
import com.brandonjf.etsysearch.ui.common.applyObservableSchedulers
import com.brandonjf.etsysearch.ui.search.Action
import com.brandonjf.etsysearch.ui.search.PagedListData
import com.brandonjf.etsysearch.ui.search.SearchViewState
import com.brandonjf.etsysearch.ui.search.data.source.DataState
import com.brandonjf.etsysearch.ui.search.interfaces.ActionObserver
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by brandon on 3/27/18.
 */
//class SearchViewModeli() : @Inject ViewModel() {

class SearchViewModel @Inject constructor(var repository: ListingRepositoryContract) : BaseObservableViewModel(), LifecycleObserver, ActionObserver {

    private val compositeDisposable = CompositeDisposable()
    private var listingDataSubject = BehaviorSubject.create<PagedListData<ActiveListing>>()
    private var stateSubect = BehaviorSubject.create<SearchViewState>()
    var pagedListLiveData: MutableLiveData<PagedList<ActiveListing>> = MutableLiveData()
    var dataStateLiveData: MutableLiveData<DataState> = MutableLiveData()
    var viewStateLiveData: MutableLiveData<SearchViewState> = MutableLiveData()
    val emptyStateLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        observeListings()
    }

    override fun bindActionObservable(actionObservable: Observable<Action>) {
        actionObservable
            .map(::handleAction)
            .compose(applyObservableSchedulers())
            .subscribe()
            .let(compositeDisposable::add)
    }

    private fun observeListings() {
//        val listObs = listingDataSubject.switchMap {
//            it.pagedList
//        }


        val listObs = listingDataSubject.switchMap { it.dataState }
            .map {
                //handle the potential for empty data and have the UI update to display.
                emptyStateLiveData.postValue(it == DataState.Empty)
            }

//            .map(pagedListLiveData::postValue)

        val networkObs = listingDataSubject.switchMap { it.dataState }
            .map(dataStateLiveData::postValue)

        Observable.merge(listObs, networkObs)
            .compose(applyObservableSchedulers())
            .subscribe()
            .let(compositeDisposable::add)
    }

    private fun handleAction(action: Action) {
        when (action) {
            is Action.Search -> handleSearchAction(action)
            is Action.Reset -> handleResetAction()
        }
    }

    private fun handleSearchAction(action: Action.Search) = repository.getListingsByPage(action.keywords).let(listingDataSubject::onNext)

    private fun handleResetAction() {
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}