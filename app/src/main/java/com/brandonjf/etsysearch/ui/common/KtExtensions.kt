package com.brandonjf.etsysearch.ui.common

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> applyObservableSchedulers(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
        upstream
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> applyFlowableSchedulers(): FlowableTransformer<T, T> {
    return FlowableTransformer { upstream ->
        upstream
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}