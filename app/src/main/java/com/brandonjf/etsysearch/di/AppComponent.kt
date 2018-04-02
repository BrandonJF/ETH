package com.brandonjf.etsysearch.di

import com.brandonjf.etsysearch.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Dagger app component for DI
 */

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (ApiModule::class), (ActivitiesModule::class), (FragmentsModule::class)])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()


}