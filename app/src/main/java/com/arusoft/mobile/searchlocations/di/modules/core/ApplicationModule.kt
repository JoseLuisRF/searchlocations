package com.arusoft.mobile.searchlocations.di.modules.core

import android.app.Application
import android.content.Context
import com.arusoft.mobile.searchlocations.data.datasource.LocationsService
import com.arusoft.mobile.searchlocations.data.network.LiveDataCallAdapterFactory
import com.arusoft.mobile.searchlocations.util.AppExecutors
import com.arusoft.mobile.searchlocations.util.AppExecutorsImpl
import com.arusoft.mobile.searchlocations.util.NetworkUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

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
    @Named("retrofit_gson")
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideJsonConverterFactory(@Named("retrofit_gson") gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun providesRetrofit(
        httpUrl: HttpUrl,
        httpClient: OkHttpClient,
        liveDataCallAdapterFactory: LiveDataCallAdapterFactory,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(httpUrl)
        .client(httpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(liveDataCallAdapterFactory)
        .build()

    @Provides
    fun providesRetrofitService(retrofit: Retrofit): LocationsService =
        retrofit.create(LocationsService::class.java)

    @Provides
    fun providesNetworkUtil(context: Context) = NetworkUtil(context)

    @Provides
    fun providesAppExecutors(appExecutors: AppExecutorsImpl): AppExecutors = appExecutors

    companion object {
        const val MAX_READ_TIME_OUT_SECONDS = 60
        const val MAX_CONNECTION_TIME_OUT_SECONDS = 60
        const val SCHEME = "https"
        const val HOST = "api.foursquare.com"
    }
}