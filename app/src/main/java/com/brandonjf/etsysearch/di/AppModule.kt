package com.brandonjf.etsysearch.di

import com.brandonjf.etsysearch.EtsyService
import com.brandonjf.etsysearch.ListingRepository
import com.brandonjf.etsysearch.ListingRepositoryContract
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_API_URL = "https://api.etsy.com/v2/"

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor()) //Adding Stetho so that we can monitor the network.
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create()) //Deserialize with Moshi (faster than GSON)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //convert RFit responses into observables
                .build()
    }

    @Provides
    @Singleton
    fun providesEtsyService(retrofit: Retrofit): EtsyService {
        return retrofit.create(EtsyService::class.java)
    }

    @Provides
    @Singleton
    fun providesListingRepository(service: EtsyService, compositeDisposable: CompositeDisposable): ListingRepositoryContract {
        return ListingRepository(service, compositeDisposable)
    }

    /**
     * Provides a single [CompositeDisposable] for the app, which given the limited scope of the application's networking
     * is safe. However, one must be careful to user [CompositeDisposable.clear] rather than [CompositeDisposable.dispose]
     * to allow further use of the disposable after child subscriptions have been flushed.
     */
    @Provides
    @Singleton
    fun providesCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}