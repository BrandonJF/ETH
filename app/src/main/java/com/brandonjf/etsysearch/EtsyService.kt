package com.brandonjf.etsysearch

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Describes the Etsy API interface for retrieving listings from the server. This interface is meant
 * to be implemented by Retrofit.
 */
interface EtsyService {
    /*
    * Hardcoding the API key for now as there is only one API call and proper handling of credentials
    * and auth tokens feels out of scope for this assignment.
    * Todo- Remove hardcoded API key
    * */

    @GET("listings/active/")
    fun getListingsByPage(
        @Query("keywords") keywords: String,
        @Query("page") page: Int,
        @Query("api_key") api_key: String = "ob5cp17p7q8hvw37wv4nopuf",
        @Query("limit") limit: String = "25",
        @Query("includes") includes: String = "MainImage"
    ): Observable<ActiveListingResponse>
}