package com.arusoft.mobile.searchlocations.util

import androidx.annotation.StringRes
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.util.ErrorConstants.TECHNICAL_DIFFICULTIES_EXCEPTION
import javax.inject.Inject

class AppErrorFactoryImpl @Inject constructor() : AppErrorFactory {

    @StringRes
    override fun createErrorMessage(errorCode: Int): Int {
        return when (errorCode) {
            TECHNICAL_DIFFICULTIES_EXCEPTION -> R.string.error_technical_difficulties
            else -> R.string.error_technical_difficulties
        }
    }
}