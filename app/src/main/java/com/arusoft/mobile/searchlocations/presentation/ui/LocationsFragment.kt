package com.arusoft.mobile.searchlocations.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.presentation.viewmodel.LocationsViewModel
import com.arusoft.mobile.searchlocations.util.NetworkUtil
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LocationsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: LocationsViewModel

    @Inject
    lateinit var networkUtil: NetworkUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_locations_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        if (networkUtil.isConnected()) {
            Toast.makeText(requireContext(), "Si hay internet", Toast.LENGTH_SHORT).show()
        }
    }
}