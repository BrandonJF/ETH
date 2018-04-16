package com.brandonjf.etsysearch.ui.search.interfaces

import com.brandonjf.etsysearch.ui.search.Action
import io.reactivex.Observable

interface ActionObserver {
    fun bindActionObservable(actionObservable: Observable<Action>)
}