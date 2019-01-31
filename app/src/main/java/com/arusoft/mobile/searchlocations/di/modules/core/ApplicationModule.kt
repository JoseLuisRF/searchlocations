package com.arusoft.mobile.searchlocations.di.modules.core

import android.app.Application
import android.content.Context
import com.arusoft.mobile.searchlocations.data.datasource.LocationsService
import com.arusoft.mobile.searchlocations.util.NetworkUtil
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
class ApplicationModule {

    @Provides
    fun providesApplicationContext(application: Application): Context =
        application.applicationContext

    @Provides
    fun providesHttpUrl(): HttpUrl =
        HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .build()


    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(MAX_READ_TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
        .connectTimeout(MAX_CONNECTION_TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
        .build()


    @Provides
    fun providesRetrofit(httpUrl: HttpUrl, httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(httpUrl)
        .client(httpClient)
        .build()


    @Provides
    fun providesRetrofitService(retrofit: Retrofit): LocationsService =
        retrofit.create(LocationsService::class.java)

    @Provides
    fun providesNetworkUtil(context: Context) = NetworkUtil(context)


    companion object {
        const val MAX_READ_TIME_OUT_SECONDS = 60
        const val MAX_CONNECTION_TIME_OUT_SECONDS = 60
        const val SCHEME = "https"
        const val HOST = "api.foursquare.com"
    }
}