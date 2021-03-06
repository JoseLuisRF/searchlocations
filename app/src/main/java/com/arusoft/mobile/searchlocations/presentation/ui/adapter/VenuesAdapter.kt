package com.arusoft.mobile.searchlocations.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.arusoft.mobile.searchlocations.presentation.ui.viewholder.VenuesViewHolder
import kotlinx.android.synthetic.main.place_item.view.*

class VenuesAdapter constructor(
    val data: MutableList<VenueUIModel> = mutableListOf(),
    val clickListener: (VenueUIModel, Int) -> Unit,
    val favoriteClickListener: (VenueUIModel) -> Unit
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
        holder.bind(venue)
        holder.itemView.card_container_view?.setOnClickListener {
            clickListener.invoke(venue, position)
        }

        holder.itemView.favorite_venue_button?.setOnClickListener {
            favoriteClickListener.invoke(venue)
        }
    }

    fun updateItem(venue: VenueUIModel) {
        data.firstOrNull { it.id == venue.id }?.let {
            val position = data.indexOf(it)
            data[position].favorite = venue.favorite
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int = data.size

    fun addAll(venues: List<VenueUIModel>) {
        data.clear()
        data.addAll(venues)
        notifyDataSetChanged()
    }
}