package com.brandonjf.etsysearch

import com.squareup.moshi.Json

/**
 * JSON Representative class for response from active listings endpoint
 */
data class ActiveListingResponse(
    @Json(name = "count") var count: Int,
    @Json(name = "results") var results: List<ActiveListing>, // changing param name for clarity
    @Json(name = "params") var params: Params,
    @Json(name = "type") var type: String,
    @field:Json(name = "pagination") var pagination: Pagination
)

data class Params(
    @Json(name = "limit") var limit: String,
    @Json(name = "offset") var offset: Int,
    @Json(name = "keywords") var keywords: String
)

data class ActiveListing(
    @Json(name = "listing_id") var listingId: Int,
    @Json(name = "title") var title: String,
    @Json(name = "description") var description: String,
    @Json(name = "price") var price: String,
    @Json(name = "url") var url: String,
    @field:Json(name = "MainImage") var mainImage: MainImage
)

data class MainImage(
    @Json(name = "listing_image_id") var listingImageId: Int,
    @Json(name = "listing_id") var listingId: Int,
    @field:Json(name = "url_75x75") var url75x75: String,
    @field:Json(name = "url_170x135") var url170x135: String,
    @field:Json(name = "url_570xN") var url570xN: String,
    @field:Json(name = "url_fullxfull") var urlFullxfull: String
)

data class Pagination(
    @field:Json(name = "effective_limit") var effectiveLimit: Int?,
    @field:Json(name = "effective_offset") var effectiveOffset: Int = 0,
    @field:Json(name = "next_offset") var nextOffset: Int?,
    @field:Json(name = "effective_page") var effectivePage: String?, //Deserializing pages as strings mitigates a few issues.
    @field:Json(name = "next_page") var nextPage: Int?
)