package com.arusoft.mobile.searchlocations.util

import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources
import com.arusoft.mobile.searchlocations.R
import javax.inject.Inject

class VenuesUtil @Inject constructor() {


    fun setFavoriteAsset(
        imageButton: ImageButton,
        favorite: Boolean
    ) {
        imageButton.apply {
            setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    if (favorite) R.drawable.ic_fav_heart_filled else R.drawable.ic_fav_heart_unfilled
                )
            )
        }
    }
}