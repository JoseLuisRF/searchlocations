package com.arusoft.mobile.searchlocations.util

import androidx.annotation.StringRes


interface AppErrorFactory {

    /**
     * Returns the error message related to an error Id
     *
     * @param errorCode Integer value for the error Id
     * @return [Int] Error message string resource id
     */
    @StringRes
    fun createErrorMessage(errorCode: Int): Int

}