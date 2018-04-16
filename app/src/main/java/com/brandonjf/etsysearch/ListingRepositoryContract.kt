package brandonjf.com.searchsy.data.repository

import com.brandonjf.etsysearch.ActiveListing
import com.brandonjf.etsysearch.ui.search.PagedListData

/**
 * Interface defining methods for the data operations related to Listing sources.
 * Implemented by external data sources, like a remote server, local persistent DB or in-memory cache.
 */
interface ListingRepositoryContract {
    fun getListingsByPage(keywords: String, page: String? = ""): PagedListData<ActiveListing>
}
