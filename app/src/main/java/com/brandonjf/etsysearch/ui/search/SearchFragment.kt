package com.brandonjf.etsysearch.ui.search

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.brandonjf.etsysearch.R
import com.brandonjf.etsysearch.databinding.FragmentSearchBinding
import com.brandonjf.etsysearch.ui.common.applyObservableSchedulers
import com.brandonjf.etsysearch.ui.search.interfaces.ActionObserver
import com.brandonjf.etsysearch.ui.search.view.adapter.ListingAdapter
import com.brandonjf.etsysearch.ui.search.viewmodel.SearchViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var listingAdapter: ListingAdapter
    private lateinit var binding: FragmentSearchBinding

    private val actionObservable: PublishSubject<Action> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupSearchInput()
        bindActionsToObserver(searchViewModel)
        binding.viewModel = searchViewModel
    }

    private fun getViewModel(): SearchViewModel {
        return ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java].apply {
            lifecycle.addObserver(this)
        }
    }

    private fun setupAdapter() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listingAdapter = ListingAdapter()
        rv_searchResults.layoutManager = linearLayoutManager
        rv_searchResults.adapter = listingAdapter
    }

    private fun setupSearchInput() {
        val searchInput = activity!!.findViewById<EditText>(R.id.et_searchInput)
        RxTextView.textChanges(searchInput)
            .skipInitialValue()
            .subscribeOn(Schedulers.io())
            .map { it.toString() }
            .skip(1)
            .filter { it.isNotBlank() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .doOnNext { Timber.d(it) }
            .map { Action.Search(it) }
            .subscribe(actionObservable::onNext)
            .let(compositeDisposable::add)
    }

    private fun bindActionsToObserver(observer: ActionObserver) {
        observer.bindActionObservable(actionObservable)
    }

    private fun setupListingListener() {
//        searchViewModel.getListingData()
//            .compose(applyObservableSchedulers())
//            .subscribe(listingAdapter::submitList)
//            .let(compositeDisposable::add)

        searchViewModel.getNetworkState()
            .compose(applyObservableSchedulers())
            .subscribe(listingAdapter::setNetworkState)
            .let(compositeDisposable::add)
    }

    override fun onStart() {
        super.onStart()
        setupListingListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
