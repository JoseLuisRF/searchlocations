package com.arusoft.mobile.searchlocations.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkUtil @Inject constructor(val context: Context) {

    /**
     * Determines network connection state.
     *
     * @return true for connection/connecting, false for no connection.
     */
    fun isConnected(): Boolean {
        val cm =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}