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
    @Json(name = "keywords") var keywords: String,
    @Json(name = "sort_on") var sortOn: String,
    @Json(name = "sort_order") var sortOrder: String,
    @Json(name = "color_accuracy") var colorAccuracy: Int,
    @Json(name = "geo_level") var geoLevel: String,
    @Json(name = "accepts_gift_cards") var acceptsGiftCards: String,
    @Json(name = "translate_keywords") var translateKeywords: String
)

data class ActiveListing(
    @Json(name = "listing_id") var listingId: Int,
    @Json(name = "state") var state: String,
    @Json(name = "user_id") var userId: Int,
    @Json(name = "category_id") var categoryId: Int,
    @Json(name = "title") var title: String,
    @Json(name = "description") var description: String,
    @Json(name = "creation_tsz") var creationTsz: Int,
    @Json(name = "ending_tsz") var endingTsz: Int,
    @Json(name = "original_creation_tsz") var originalCreationTsz: Int,
    @Json(name = "last_modified_tsz") var lastModifiedTsz: Int,
    @Json(name = "price") var price: String,
    @Json(name = "currency_code") var currencyCode: String,
    @Json(name = "quantity") var quantity: Int,
    @Json(name = "tags") var tags: List<String>,
    @Json(name = "category_path") var categoryPath: List<String>,
    @Json(name = "category_path_ids") var categoryPathIds: List<Int>,
    @Json(name = "materials") var materials: List<String>,
    @Json(name = "shop_section_id") var shopSectionId: Int,
    @Json(name = "state_tsz") var stateTsz: Int,
    @Json(name = "url") var url: String,
    @Json(name = "views") var views: Int,
    @Json(name = "num_favorers") var numFavorers: Int,
    @Json(name = "shipping_template_id") var shippingTemplateId: Long,
    @Json(name = "processing_min") var processingMin: Int,
    @Json(name = "processing_max") var processingMax: Int,
    @Json(name = "who_made") var whoMade: String,
    @Json(name = "is_supply") var isSupply: String,
    @Json(name = "when_made") var whenMade: String,
    @Json(name = "item_dimensions_unit") var itemDimensionsUnit: String,
    @Json(name = "is_private") var isPrivate: Boolean,
    @Json(name = "non_taxable") var nonTaxable: Boolean,
    @Json(name = "is_customizable") var isCustomizable: Boolean,
    @Json(name = "is_digital") var isDigital: Boolean,
    @Json(name = "file_data") var fileData: String,
    @Json(name = "should_auto_renew") var shouldAutoRenew: Boolean,
    @Json(name = "language") var language: String,
    @Json(name = "has_variations") var hasVariations: Boolean,
    @Json(name = "taxonomy_id") var taxonomyId: Int,
    @Json(name = "taxonomy_path") var taxonomyPath: List<String>,
    @Json(name = "used_manufacturer") var usedManufacturer: Boolean,
    @Json(name = "sku") var sku: List<String>,
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