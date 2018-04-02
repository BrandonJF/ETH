package com.brandonjf.etsysearch

/**
 * A class to encapsulate common search variables.
 */
data class Search(val term: String = "", val page: Int = 0, val resultCount: Int = 0, val hasNextPage: Boolean = false)