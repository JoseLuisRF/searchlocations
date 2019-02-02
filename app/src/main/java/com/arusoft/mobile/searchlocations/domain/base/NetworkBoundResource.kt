package com.arusoft.mobile.searchlocations.domain.base

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.arusoft.mobile.searchlocations.domain.model.BaseModel
import com.arusoft.mobile.searchlocations.util.AppExecutors

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture Guide] (https://developer.android.com/arch).
 *
 * @param <ModelType> Object return type
 * */
abstract class NetworkBoundResource<ModelType : BaseModel, T> constructor(private val appExecutors: AppExecutors) {

    private lateinit var result: MediatorLiveData<ModelType>

    fun execute(params: T): LiveData<ModelType> {
        result = MediatorLiveData()
        result.postValue(getLoadingObject())
        appExecutors.getDiskExecutor().execute {
            val dbSource = loadFromDb(params)
            appExecutors.getMainThreadExecutor().execute {
                result.addSource(dbSource) { data ->
                    result.removeSource(dbSource)
                    if (shouldFetch(data)) {
                        fetchFromNetwork(dbSource, params)
                    } else {
                        appExecutors.getMainThreadExecutor().execute {
                            data?.let { setValue(it) }
                        }
                    }
                }
            }
        }
        return result
    }

    @MainThread
    private fun setValue(value: ModelType) {
        result.postValue(value)
    }

    private fun fetchFromNetwork(dbSource: LiveData<ModelType>, params: T) {
        appExecutors.getNetworkExecutor().execute {
            val apiResponse = createCall(params)
            appExecutors.getMainThreadExecutor().execute {
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)
                    response?.let { setValue(it) }
                }
            }
        }
    }

    protected fun onFetchFailed() {}

    @MainThread
    abstract fun shouldFetch(data: ModelType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(params: T): LiveData<ModelType>

    @MainThread
    protected abstract fun createCall(params: T): LiveData<ModelType>

    abstract fun getLoadingObject(): ModelType

}