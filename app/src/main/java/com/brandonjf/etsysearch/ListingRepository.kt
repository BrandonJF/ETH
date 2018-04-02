package brandonjf.com.searchsy.data.repository
import com.brandonjf.etsysearch.ActiveListingResponse
import com.brandonjf.etsysearch.Search
import io.reactivex.Observable

/**
 * Interface defining methods for the data operations related to Listing sources.
 * Implemented by external data sources, like a remote server, local persistent DB or in-memory cache.
 */
interface ListingRepository {
    fun getListingsByPage(search : Search): Observable<ActiveListingResponse>
}
