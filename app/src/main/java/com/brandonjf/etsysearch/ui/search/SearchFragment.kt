package com.brandonjf.etsysearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import brandonjf.com.searchsy.data.repository.ListingRepository
import com.brandonjf.etsysearch.R
import com.brandonjf.etsysearch.Search
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var api : ListingRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        api.getListingsByPage(Search("longboard"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                Timber.d("$it")
            }, {
                Timber.e("$it")
            })
    }


}
