package com.arusoft.mobile.searchlocations.presentation.model

import android.os.Parcel
import android.os.Parcelable
import com.arusoft.mobile.searchlocations.util.CurrentLocation.CURRENT_LATITUDE
import com.arusoft.mobile.searchlocations.util.CurrentLocation.CURRENT_LONGITUDE

data class VenueSearchInput constructor(
    val latitude: Double = CURRENT_LATITUDE,
    val longitude: Double = CURRENT_LONGITUDE,
    val query: String = ""
)

data class VenuesUIModel constructor(val venues: List<VenueUIModel>) : BaseViewModel()

data class VenueUIModel constructor(
    val id: String,
    val distance: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val state: String,
    val country: String,
    val categoryName: String,
    val address: String,
    val formattedAddress: List<String>,
    val url: String
) : BaseViewModel(), Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readDouble(),
        source.readDouble(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.createStringArrayList(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeInt(distance)
        writeString(name)
        writeDouble(latitude)
        writeDouble(longitude)
        writeString(city)
        writeString(state)
        writeString(country)
        writeString(categoryName)
        writeString(address)
        writeStringList(formattedAddress)
        writeString(url)
        writeString(status)
        writeInt(if (error) 1 else 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VenueUIModel> = object : Parcelable.Creator<VenueUIModel> {
            override fun createFromParcel(source: Parcel): VenueUIModel {
                val model = VenueUIModel(source)
                model.status = source.readString()
                model.error = source.readInt() == 1
                return model
            }

            override fun newArray(size: Int): Array<VenueUIModel?> = arrayOfNulls(size)
        }
    }
}