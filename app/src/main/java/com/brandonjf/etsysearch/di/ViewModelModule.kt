package com.brandonjf.etsysearch.di

import com.brandonjf.etsysearch.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module

/**
 * Created by brandon on 3/27/18.
 */

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): SearchViewModel
}