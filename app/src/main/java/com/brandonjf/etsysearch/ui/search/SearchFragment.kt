package com.brandonjf.etsysearch.ui.search

import android.arch.lifecycle.Observer
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
 * The main fragment which serves as a view for active listings. Architected in a MVVM pattern, the view is sub'd to
 * the viewmodel's livedata and Rx Observables.
 *
 */
class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val compositeDisposable = CompositeDisposable()
    private val listingAdapter: ListingAdapter = ListingAdapter()
    private val actionObservable: PublishSubject<Action> = PublishSubject.create()

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        FragmentSearchBinding.inflate(inflater, container, false).let { binding ->
            setupBinding(binding)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchInput()
        observeViewModelData()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun setupViewModel() {
        ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java].let { viewModel ->
            searchViewModel = viewModel
            lifecycle.addObserver(viewModel)
            (viewModel as? ActionObserver)?.bindActionObservable(actionObservable)
        }
    }

    private fun setupBinding(binding: FragmentSearchBinding) {
        if (::searchViewModel.isInitialized) binding.viewModel = searchViewModel
    }

    private fun setupRecyclerView() = with(rv_searchResults) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = listingAdapter
    }

    private fun setupSearchInput() {
        val searchInput = activity!!.findViewById<EditText>(R.id.et_searchInput)
        RxTextView.textChanges(searchInput)
            .skipInitialValue()
            .subscribeOn(Schedulers.io())
            .map { it.toString() }
            .skip(1)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .doOnNext { Timber.d(it) }
            .map {
                Action.Search(it)
            }
            .subscribe(actionObservable::onNext)
            .let(compositeDisposable::add)
    }

    private fun observeViewModelData() {
        searchViewModel.viewStateLiveData.observe(this, Observer {
            it?.let { state ->
                if (state.isEmpty) {
                    //hide the RV
                } else {
                    state.pagedListLiveData.observe(this, Observer {
                        it?.let(listingAdapter::submitList)
                            ?.also { rv_searchResults.smoothScrollToPosition(0) }
                    })
                }
            }
        })
//        searchViewModel.pagedListLiveData.observe(this, Observer {
//            it?.let(listingAdapter::submitList)
//                ?.also { rv_searchResults.smoothScrollToPosition(0) }
//        })
//        searchViewModel.dataStateLiveData.observe(this, Observer {
//            it?.let(listingAdapter::setNetworkState)
//        })
    }
}
