package com.brandonjf.etsysearch.ui.search.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.brandonjf.etsysearch.ActiveListing
import com.brandonjf.etsysearch.R
import com.brandonjf.etsysearch.databinding.ListingItemBinding
import com.brandonjf.etsysearch.ui.search.data.source.NetworkState

class ListingAdapter : PagedListAdapter<ActiveListing, RecyclerView.ViewHolder>(ListingDiffCallback) {
    val loadingType = R.layout.loading_item
    val listingType = R.layout.listing_item

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.listing_item -> {
                ListingItemBinding.inflate(inflater, parent, false)
                    .let(::ListingViewHolder)
            }
            R.layout.loading_item -> {
                inflater.inflate(R.layout.loading_item, parent, false)
                    .let(::LoadingViewHolder)
            }
            else -> throw IllegalArgumentException("ViewType Unknown: $viewType")

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListingViewHolder -> getItem(position)?.let(holder::bind)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (shouldShowLoader() && position == itemCount - 1) loadingType else listingType
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val wasShowingLoader = shouldShowLoader()
        this.networkState = newNetworkState
        val shouldShowLoader = shouldShowLoader()
        if (wasShowingLoader != shouldShowLoader) {
            if (wasShowingLoader) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (shouldShowLoader && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun shouldShowLoader() = networkState != null && networkState != NetworkState.Loaded

    override fun getItemCount(): Int {
        return super.getItemCount() + if (shouldShowLoader()) 1 else 0
    }

    companion object {
        val ListingDiffCallback = object : DiffCallback<ActiveListing>() {
            override fun areItemsTheSame(oldItem: ActiveListing, newItem: ActiveListing): Boolean {
                return oldItem.listingId == newItem.listingId
            }

            override fun areContentsTheSame(oldItem: ActiveListing, newItem: ActiveListing): Boolean {
                return oldItem == newItem
            }
        }
    }
}