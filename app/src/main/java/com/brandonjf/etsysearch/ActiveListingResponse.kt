package com.brandonjf.etsysearch

import com.squareup.moshi.Json

/**
 * JSON Representative class for response from active listings endpoint
 */
data class ActiveListingResponse(
    @Json(name = "count") val count: Int,
    @Json(name = "results") val results: List<ActiveListing>, // changing param name for clarity
    @Json(name = "params") val params: Params,
    @Json(name = "type") val type: String,
    @Json(name = "pagination") val pagination: Pagination
)

data class Params(
    @Json(name = "limit") val limit: String,
    @Json(name = "offset") val offset: Int,
    @Json(name = "keywords") val keywords: String,
    @Json(name = "sort_on") val sortOn: String,
    @Json(name = "sort_order") val sortOrder: String,
    @Json(name = "color_accuracy") val colorAccuracy: Int,
    @Json(name = "geo_level") val geoLevel: String,
    @Json(name = "accepts_gift_cards") val acceptsGiftCards: String,
    @Json(name = "translate_keywords") val translateKeywords: String
)

data class ActiveListing(
    @Json(name = "listing_id") val listingId: Int,
    @Json(name = "state") val state: String,
    @Json(name = "user_id") val userId: Int,
    @Json(name = "category_id") val categoryId: Int,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "creation_tsz") val creationTsz: Int,
    @Json(name = "ending_tsz") val endingTsz: Int,
    @Json(name = "original_creation_tsz") val originalCreationTsz: Int,
    @Json(name = "last_modified_tsz") val lastModifiedTsz: Int,
    @Json(name = "price") val price: String,
    @Json(name = "currency_code") val currencyCode: String,
    @Json(name = "quantity") val quantity: Int,
    @Json(name = "tags") val tags: List<String>,
    @Json(name = "category_path") val categoryPath: List<String>,
    @Json(name = "category_path_ids") val categoryPathIds: List<Int>,
    @Json(name = "materials") val materials: List<String>,
    @Json(name = "shop_section_id") val shopSectionId: Int,
    @Json(name = "state_tsz") val stateTsz: Int,
    @Json(name = "url") val url: String,
    @Json(name = "views") val views: Int,
    @Json(name = "num_favorers") val numFavorers: Int,
    @Json(name = "shipping_template_id") val shippingTemplateId: Long,
    @Json(name = "processing_min") val processingMin: Int,
    @Json(name = "processing_max") val processingMax: Int,
    @Json(name = "who_made") val whoMade: String,
    @Json(name = "is_supply") val isSupply: String,
    @Json(name = "when_made") val whenMade: String,
    @Json(name = "item_dimensions_unit") val itemDimensionsUnit: String,
    @Json(name = "is_private") val isPrivate: Boolean,
    @Json(name = "non_taxable") val nonTaxable: Boolean,
    @Json(name = "is_customizable") val isCustomizable: Boolean,
    @Json(name = "is_digital") val isDigital: Boolean,
    @Json(name = "file_data") val fileData: String,
    @Json(name = "should_auto_renew") val shouldAutoRenew: Boolean,
    @Json(name = "language") val language: String,
    @Json(name = "has_variations") val hasVariations: Boolean,
    @Json(name = "taxonomy_id") val taxonomyId: Int,
    @Json(name = "taxonomy_path") val taxonomyPath: List<String>,
    @Json(name = "used_manufacturer") val usedManufacturer: Boolean,
    @Json(name = "sku") val sku: List<String>,
    @field:Json(name = "MainImage") val mainImage: MainImage
)

data class MainImage(
    @Json(name = "listing_image_id") val listingImageId: Int,
    @Json(name = "listing_id") val listingId: Int,
    @field:Json(name = "url_75x75") val url75x75: String,
    @field:Json(name = "url_170x135") val url170x135: String,
    @field:Json(name = "url_570xN") val url570xN: String,
    @field:Json(name = "url_fullxfull") val urlFullxfull: String
)

data class Pagination(
    @Json(name = "effective_limit") val effectiveLimit: Int,
    @Json(name = "effective_offset") val effectiveOffset: Int,
    @Json(name = "next_offset") val nextOffset: Int,
    @Json(name = "effective_page") val effectivePage: Int,
    @Json(name = "next_page") val nextPage: Int
)