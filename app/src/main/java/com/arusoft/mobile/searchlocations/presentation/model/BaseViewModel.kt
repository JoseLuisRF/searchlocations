package com.arusoft.mobile.searchlocations.presentation.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.StringRes
import com.arusoft.mobile.searchlocations.util.ErrorConstants.COMMON_ERROR

open class BaseViewModel(
    @StringRes var messageId: Int = 0,
    var error: Boolean = false,
    var status: String = LOADING,
    var errorCode: Int = 0
) : Parcelable {

    fun setError(@StringRes messageId: Int, errorCode: Int = COMMON_ERROR) {
        this.messageId = messageId
        this.error = true
        this.status = ERROR
        this.errorCode = errorCode
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        1 == source.readInt(),
        source.readString(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(messageId)
        writeInt((if (error) 1 else 0))
        writeString(status)
        writeInt(errorCode)
    }

    companion object {
        const val SUCCESS = "SUCCESS"

        const val ERROR = "ERROR"

        const val LOADING = "LOADING"

        @JvmField
        val CREATOR: Parcelable.Creator<BaseViewModel> =
            object : Parcelable.Creator<BaseViewModel> {
                override fun createFromParcel(source: Parcel): BaseViewModel = BaseViewModel(source)
                override fun newArray(size: Int): Array<BaseViewModel?> = arrayOfNulls(size)
            }
    }
}

/**
 * Shallow copy of base class [BaseViewModel]
 *
 * @param [BaseViewModel] base class
 * @return [T] sub class
 */
fun <T : BaseViewModel> T.baseCopy(base: BaseViewModel): T {
    status = base.status
    error = base.error
    errorCode = base.errorCode
    return this
}