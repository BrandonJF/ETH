package com.brandonjf.etsysearch.di

import brandonjf.com.searchsy.data.remote.EtsyRemoteDataSource
import brandonjf.com.searchsy.data.repository.ListingRepository
import dagger.Module
import dagger.Provides

/**
 * Created by brandon on 3/27/18.
 */

@Module
class ApiModule {
    @Provides
    fun providesApi(): ListingRepository = EtsyRemoteDataSource()
}