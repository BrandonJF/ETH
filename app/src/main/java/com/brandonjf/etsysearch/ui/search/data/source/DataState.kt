package com.brandonjf.etsysearch.ui.search.data.source

sealed class DataState {
    object Initial : DataState()
    object Loading : DataState()
    object Loaded : DataState()
    object Empty : DataState()
    class Error(reason: String?) : DataState()
}