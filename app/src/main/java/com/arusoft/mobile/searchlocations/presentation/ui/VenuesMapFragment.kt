package com.arusoft.mobile.searchlocations.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.presentation.mapper.PresentationModelMapper
import com.arusoft.mobile.searchlocations.presentation.model.BaseViewModel
import com.arusoft.mobile.searchlocations.presentation.model.VenuesUIModel
import com.arusoft.mobile.searchlocations.presentation.viewmodel.LocationsViewModel
import com.arusoft.mobile.searchlocations.util.CurrentLocation.CURRENT_LATITUDE
import com.arusoft.mobile.searchlocations.util.CurrentLocation.CURRENT_LONGITUDE
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_venues_map.*
import javax.inject.Inject

class VenuesMapFragment : DaggerFragment(), OnMapReadyCallback,
    GoogleMap.OnInfoWindowClickListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mapper: PresentationModelMapper

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
        mMap?.setOnInfoWindowClickListener(this)
        viewModel.venuesLiveData.observe(requireActivity(), venuesMapObserver)
    }

    fun showProgressLoader(visible: Boolean = true) {
        progress_loader_view?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private val venuesMapObserver = Observer<VenuesUIModel> { response ->
        when (response.status) {
            BaseViewModel.LOADING -> {
                showProgressLoader(true)
            }
            BaseViewModel.SUCCESS -> {
                showProgressLoader(false)
                mMap?.clear()
                for (venue in response.venues) {
                    val marker = mMap?.addMarker(
                        MarkerOptions()
                            .position(LatLng(venue.latitude, venue.longitude))
                            .title(venue.name)
                    )
                    marker?.tag = venue.id
                }
                val cameraPosition =
                    CameraPosition.Builder().target(LatLng(CURRENT_LATITUDE, CURRENT_LONGITUDE))
                        .zoom(DEFAULT_MAP_ZOOM)
                        .build()
                mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
            BaseViewModel.ERROR -> {
                showProgressLoader(false)
            }
        }
    }

    override fun onInfoWindowClick(marker: Marker) {
        marker.tag?.let { venueId ->
            viewModel.venuesLiveData.value?.venues?.firstOrNull { it.id == venueId }?.let {
                val action =
                    VenuesMapFragmentDirections.actionVenuesMapFragmentToSecondLevelActivity()
                action.setVenue(it)
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        const val DEFAULT_MAP_ZOOM = 17f
    }
}