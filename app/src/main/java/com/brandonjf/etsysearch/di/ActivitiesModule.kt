package com.brandonjf.etsysearch.di

import com.brandonjf.etsysearch.ui.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by brandon on 3/27/18.
 */

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchActivityInjector(): SearchActivity
}
