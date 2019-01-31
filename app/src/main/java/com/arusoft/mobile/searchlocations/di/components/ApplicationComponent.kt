package com.arusoft.mobile.searchlocations.di.components

import android.app.Application
import android.content.Context
import com.arusoft.mobile.searchlocations.CustomApplication
import com.arusoft.mobile.searchlocations.di.modules.ActivityBindingModule
import com.arusoft.mobile.searchlocations.di.modules.core.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidInjectionModule::class,
        ActivityBindingModule::class]
)
interface ApplicationComponent : AndroidInjector<CustomApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun context(): Context

}