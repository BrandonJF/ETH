package com.brandonjf.etsysearch.ui.search.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.brandonjf.etsysearch.ActiveListing
import com.brandonjf.etsysearch.R
import com.brandonjf.etsysearch.databinding.ListingItemBinding
import com.brandonjf.etsysearch.ui.search.data.source.DataState

/**
 * This [ListingAdapter] manages the display of [ActiveListing]s to the user. The two viewtypes that it is primarily
 * concerned with are the Listing Item and the Loading Item, the latter of which is used for the display of the
 * current network status as set by [setNetworkState].
 */
class ListingAdapter : PagedListAdapter<ActiveListing, RecyclerView.ViewHolder>(ListingDiffCallback) {
    private val loadingType = R.layout.loading_item
    private val listingType = R.layout.listing_item
    private val emptyType = R.layout.listing_item
    private var dataState: DataState = DataState.Initial

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            listingType -> ListingItemBinding.inflate(inflater, parent, false).let(::ListingViewHolder)
            loadingType -> inflater.inflate(loadingType, parent, false).let(::LoadingViewHolder)
            else -> throw IllegalArgumentException("ViewType Unknown: $viewType")
        }
    }

    /**
     * Currently only accounting for the one viewtype within the limited scope of this app.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ListingViewHolder)?.bind(getItem(position))
    }

    override fun getItemViewType(position: Int) = when {
        shouldShowLoader() && position == itemCount - 1 -> loadingType
        shouldShowEmpty() && position == itemCount - 1 -> emptyType
        else -> listingType
    }

    /**
     * Whether or not we should show a load progress indicator based on the current [dataState]
     */
    private fun shouldShowLoader() = when (dataState) {
        DataState.Initial, DataState.Loaded -> false
        else -> true
    }

    private fun shouldShowEmpty(): Boolean {
        return dataState == DataState.Loaded && super.getItemCount() == 0
    }

    /**
     * Count accounts for the potential presence of a loading indicator.
     */
    override fun getItemCount() = super.getItemCount() + if (shouldShowLoader()) 1 else 0

    /**
     * If the network state is such that an extra row should be shown for the loading progress indicator
     * we handle that here.
     */
    fun setNetworkState(newDataState: DataState) {
        val previousState = this.dataState
        val wasShowingLoader = shouldShowLoader()
        this.dataState = newDataState
        val shouldShowLoader = shouldShowLoader()
        if (wasShowingLoader != shouldShowLoader) {
            super.getItemCount().let(if (wasShowingLoader) ::notifyItemRemoved else ::notifyItemInserted)
        } else if (shouldShowLoader && previousState != newDataState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val ListingDiffCallback = object : DiffUtil.ItemCallback<ActiveListing>() {
            override fun areItemsTheSame(oldItem: ActiveListing, newItem: ActiveListing) = oldItem.listingId == newItem.listingId
            override fun areContentsTheSame(oldItem: ActiveListing, newItem: ActiveListing) = oldItem == newItem
        }
    }
}