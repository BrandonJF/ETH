package com.brandonjf.etsysearch.ui.common

import android.arch.paging.PagedList
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.brandonjf.etsysearch.ActiveListing
import com.brandonjf.etsysearch.ui.search.view.adapter.ListingAdapter
import timber.log.Timber

@BindingAdapter("pagedList")
fun setPagedList(recyclerView: RecyclerView, pagedList: PagedList<ActiveListing>?) {
    Timber.d("Binding PagedList: ${pagedList}")
    pagedList?.let {
        (recyclerView.adapter as? ListingAdapter)?.submitList(it)
    }
}