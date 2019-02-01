package com.arusoft.mobile.searchlocations.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.arusoft.mobile.searchlocations.presentation.ui.viewholder.VenuesViewHolder
import kotlinx.android.synthetic.main.place_item.view.*

class VenuesAdapter constructor(
    val data: MutableList<VenueUIModel> = mutableListOf(),
    val clickListener: (VenueUIModel, Int) -> Unit
) :
    RecyclerView.Adapter<VenuesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenuesViewHolder {
        return VenuesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.place_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VenuesViewHolder, position: Int) {
        val venue = data[position]
        holder.itemView.venue_name?.text = venue.name

        holder.itemView.venue_category?.apply {
            text = venue.categoryName
            visibility = if (venue.categoryName.isBlank()) View.GONE else View.VISIBLE
        }

        holder.itemView.venue_address?.apply {
            text = venue.address
            visibility = if (venue.address.isBlank()) View.GONE else View.VISIBLE
        }

        holder.itemView.venue_distance?.apply {
            text =
                if (venue.distance == 0) context.getString(R.string.item_distance_close) else context.getString(
                    R.string.item_distance,
                    venue.distance
                )
        }

        holder.itemView.card_container_view?.setOnClickListener {
            clickListener.invoke(venue, position)
        }
    }

    override fun getItemCount(): Int = data.size

    fun addAll(venues: List<VenueUIModel>) {
        data.clear()
        data.addAll(venues)
        notifyDataSetChanged()
    }
}