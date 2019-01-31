package com.arusoft.mobile.searchlocations.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

const val THREAD_COUNT = 5

/**
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
open class AppExecutorsImpl @Inject constructor() : AppExecutors {

    private val diskIO: Executor = DiskIOThreadExecutor()
    private val networkIOThread: Executor = Executors.newFixedThreadPool(THREAD_COUNT)
    private val mainThread: Executor = MainThreadExecutor()


    override fun getDiskExecutor(): Executor = diskIO

    override fun getNetworkExecutor(): Executor = networkIOThread

    override fun getMainThreadExecutor(): Executor = mainThread
}

interface AppExecutors {

    fun getDiskExecutor(): Executor

    fun getNetworkExecutor(): Executor

    fun getMainThreadExecutor(): Executor
}

class MainThreadExecutor @Inject constructor() : Executor {

    override fun execute(command: Runnable) {
        Handler(Looper.getMainLooper()).post(command)
    }
}

class DiskIOThreadExecutor @Inject constructor() : Executor {

    override fun execute(command: Runnable) {
        Executors.newSingleThreadExecutor().execute(command)
    }
}