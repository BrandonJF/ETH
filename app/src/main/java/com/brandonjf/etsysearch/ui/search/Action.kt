package com.brandonjf.etsysearch.ui.search

sealed class Action {
    data class Search(val keywords: String) : Action()
}