package com.arusoft.mobile.searchlocations.data.network

import com.arusoft.mobile.searchlocations.data.network.HttpErrorCodes.NOT_FOUND_ERROR


/**
 * Common class used by API responses.
 *
 * @param <R></R>, E>
 */
class ApiResponse<R, E> {
    val body: R?
    var errorBody: E? = null
    var exception: Throwable? = null
    var code: Int = 0

    val isSuccessful: Boolean
        get() = code >= HttpErrorCodes.SUCCESS && code < HttpErrorCodes.REDIRECTION

    constructor(error: Throwable) {
        code = NOT_FOUND_ERROR
        body = null
        errorBody = null
        exception = error
    }

    constructor(response: Response<R, E>) {
        code = response.code()
        body = response.body()
        exception = null

        if (isSuccessful) {
            errorBody = null
        } else {
            try {
                errorBody = response.error()
            } catch (ex: Exception) {
                errorBody = null
                exception = ex
            }

        }
    }
}

