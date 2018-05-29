package com.brandonjf.etsysearch.ui.search.view.adapter

import android.support.v7.widget.RecyclerView
import com.brandonjf.etsysearch.ActiveListing
import com.brandonjf.etsysearch.databinding.ListingItemBinding
import com.brandonjf.etsysearch.ui.search.viewmodel.ListingViewModel

class ListingViewHolder(val binding: ListingItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(listing: ActiveListing?) {
        binding.apply {
            viewModel = ListingViewModel(listing?.title, listing?.mainImage?.url75x75)
            executePendingBindings()
        }
    }
}
