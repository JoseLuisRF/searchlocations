package com.arusoft.mobile.searchlocations.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.presentation.model.BaseViewModel
import com.arusoft.mobile.searchlocations.presentation.model.VenuesUIModel
import com.arusoft.mobile.searchlocations.presentation.viewmodel.LocationsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class VenuesMapFragment : DaggerFragment(), OnMapReadyCallback {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mMap: GoogleMap? = null
    private lateinit var viewModel: LocationsViewModel
    private var supportMapFragment: SupportMapFragment? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(LocationsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_venues_map, container, false)
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance()
        }
        supportMapFragment?.let {
            it.getMapAsync(this)
            fragmentManager?.beginTransaction()?.replace(R.id.map, it)?.commit()
        }

        return rootView
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        viewModel.venuesLiveData.observe(requireActivity(), venuesMapObserver)
    }

    private val venuesMapObserver = Observer<VenuesUIModel> { response ->
        when (response.status) {
            BaseViewModel.LOADING -> {
//                showLoader()
            }
            BaseViewModel.SUCCESS -> {
//                showLoader(false)
                mMap?.clear()
                for (venue in response.venues) {
                    val venueLocation = LatLng(venue.latitude, venue.longitude)
                    mMap?.addMarker(
                        MarkerOptions()
                            .position(venueLocation)
                            .title(venue.name)
                    )
                }

                mMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(47.6062, 122.3321)))
            }
            BaseViewModel.ERROR -> {
//                showLoader(false)
            }
        }
    }
}