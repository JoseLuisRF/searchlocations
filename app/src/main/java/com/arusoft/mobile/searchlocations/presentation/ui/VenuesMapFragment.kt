package com.arusoft.mobile.searchlocations.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arusoft.mobile.searchlocations.R
import dagger.android.support.DaggerFragment

class VenuesMapFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_venues_map, container, false)
    }
}