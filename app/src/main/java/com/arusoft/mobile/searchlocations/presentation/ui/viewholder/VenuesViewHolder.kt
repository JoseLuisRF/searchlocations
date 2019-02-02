package com.arusoft.mobile.searchlocations.presentation.ui.viewholder

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import kotlinx.android.synthetic.main.place_item.view.*

class VenuesViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(venue: VenueUIModel) {
        itemView.venue_name?.text = venue.name

        itemView.venue_category?.apply {
            text = venue.categoryName
            visibility = if (venue.categoryName.isBlank()) View.GONE else View.VISIBLE
        }

        itemView.venue_address?.apply {
            text = venue.address
            visibility = if (venue.address.isBlank()) View.GONE else View.VISIBLE
        }

        itemView.venue_distance?.apply {
            text =
                if (venue.distance == 0) context.getString(R.string.item_distance_close) else context.getString(
                    R.string.item_distance,
                    venue.distance
                )
        }

        itemView.favorite_venue_button?.apply {
            setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    if (venue.favorite) R.drawable.ic_fav_heart_filled else R.drawable.ic_fav_heart_unfilled
                )
            )
        }
    }

}