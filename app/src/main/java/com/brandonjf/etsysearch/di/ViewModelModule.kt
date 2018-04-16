package com.brandonjf.etsysearch.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.brandonjf.etsysearch.ui.search.viewmodel.SearchViewModel
import com.brandonjf.etsysearch.ui.search.viewmodel.SearchViewModelFactory
import com.brandonjf.etsysearch.ui.search.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by brandon on 3/27/18.
 */

@Module(includes = [(AppModule::class)])
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel( searchViewModel: SearchViewModel ): ViewModel

    @Binds
    abstract fun bindViewModelFactory( factory: SearchViewModelFactory): ViewModelProvider.Factory
}