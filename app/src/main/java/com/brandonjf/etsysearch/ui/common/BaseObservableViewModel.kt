package com.brandonjf.etsysearch.ui.common

import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry

/**
 * A convenience class that implements [android.databinding.Observable] interface and provides
 * [.notifyPropertyChanged] and [.notifyChange] methods.
 */
open class BaseObservableViewModel : ViewModel(), Observable {

    private val mCallbacks by lazy { PropertyChangeRegistry()}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        mCallbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        mCallbacks.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        mCallbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        mCallbacks.notifyCallbacks(this, fieldId, null)
    }
}