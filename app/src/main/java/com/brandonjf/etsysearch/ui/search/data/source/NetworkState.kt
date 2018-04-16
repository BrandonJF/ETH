package com.brandonjf.etsysearch.ui.search.data.source

sealed class NetworkState {
    object Loading : NetworkState()
    object Loaded : NetworkState()
    class ERROR(reason: String?) : NetworkState()
}