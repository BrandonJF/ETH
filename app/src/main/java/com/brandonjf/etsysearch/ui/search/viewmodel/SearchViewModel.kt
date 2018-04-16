package com.brandonjf.etsysearch.ui.search.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import android.databinding.Bindable
import brandonjf.com.searchsy.data.repository.ListingRepositoryContract
import com.brandonjf.etsysearch.ActiveListing
import com.brandonjf.etsysearch.ui.common.BaseObservableViewModel
import com.brandonjf.etsysearch.ui.common.applyObservableSchedulers
import com.brandonjf.etsysearch.ui.search.Action
import com.brandonjf.etsysearch.ui.search.PagedListData
import com.brandonjf.etsysearch.ui.search.data.source.NetworkState
import com.brandonjf.etsysearch.ui.search.interfaces.ActionObserver
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by brandon on 3/27/18.
 */
//class SearchViewModeli() : @Inject ViewModel() {

class SearchViewModel @Inject constructor(var repository: ListingRepositoryContract) : BaseObservableViewModel(), LifecycleObserver, ActionObserver {

    private val compositeDisposable = CompositeDisposable()
    private var listingSubject = BehaviorSubject.create<PagedListData<ActiveListing>>()

    private var pagedListLiveData: MutableLiveData<PagedList<ActiveListing>> = MutableLiveData()

    init {
        observeListings()
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    fun onDestroy(owner: LifecycleOwner) {
//        Timber.d("ViewModel observed destroy of fragment.")
//        compositeDisposable.clear()
//    }

    override fun bindActionObservable(actionObservable: Observable<Action>) {
        actionObservable
            .map(::handleAction)
            .compose(applyObservableSchedulers())
            .subscribe()
            .let(compositeDisposable::add)
    }

    fun getListingData(): Observable<PagedList<ActiveListing>> {
        return listingSubject.switchMap { it.pagedList }
    }

    fun getNetworkState(): Observable<NetworkState> {
        return listingSubject.switchMap { it.networkState }
    }

    private fun observeListings() {
        listingSubject.switchMap { it.pagedList }
            .compose(applyObservableSchedulers())
            .subscribe (pagedListLiveData::postValue)
            .let(compositeDisposable::add)
    }

    @get:Bindable
    var pagedList: PagedList<ActiveListing>? by notifiable(null, com.android.databinding.library.baseAdapters.BR.pagedList) {}

    private inline fun <T> notifiable(initialValue: T, fieldId: Int, crossinline onChange: (newValue: T) -> Unit): ReadWriteProperty<Any?, T> {
        return object : ObservableProperty<T>(initialValue) {
            override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
                onChange(newValue)
                Timber.d("Notifying property of change.")
                notifyPropertyChanged(fieldId)
            }
        }
    }

    fun handleAction(action: Action) {
        Timber.d("Handling action ${action.javaClass.simpleName}")
        when (action) {
            is Action.Search -> search(action.keywords)
        }
    }

    private fun search(keywords: String) = repository.getListingsByPage(keywords).let(listingSubject::onNext)

    override fun onCleared() {
        super.onCleared()
        Timber.d("Clearing the ViewModel: ${this.javaClass.simpleName}")
        compositeDisposable.clear()
    }
}