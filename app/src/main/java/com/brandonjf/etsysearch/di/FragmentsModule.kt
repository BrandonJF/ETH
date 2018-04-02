package com.brandonjf.etsysearch.di

import com.brandonjf.etsysearch.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by brandon on 3/27/18.
 */

@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchFragmentInjector(): SearchFragment
}

