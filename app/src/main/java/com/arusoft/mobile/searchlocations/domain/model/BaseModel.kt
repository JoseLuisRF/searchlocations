package com.arusoft.mobile.searchlocations.domain.model

open class BaseModel constructor(
    var errorCode: Int = NO_VALUE,
    var error: Boolean = false,
    var status: String = LOADING) {

    fun setError(errorCode: Int) {
        this.errorCode = errorCode
        this.error = true
        this.status = ERROR
    }

    companion object {
        const val LOADING = "LOADING"
        const val SUCCESS = "SUCCESS"
        const val ERROR = "ERROR"
        const val EMPTY_STRING = ""
        const val NO_VALUE = 0
    }
}
