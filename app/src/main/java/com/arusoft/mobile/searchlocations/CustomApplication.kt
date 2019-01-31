package com.arusoft.mobile.searchlocations

import com.arusoft.mobile.searchlocations.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class CustomApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }
}