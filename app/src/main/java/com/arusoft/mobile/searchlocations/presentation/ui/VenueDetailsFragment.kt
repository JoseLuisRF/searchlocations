package com.arusoft.mobile.searchlocations.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.presentation.model.BaseViewModel
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.arusoft.mobile.searchlocations.presentation.viewmodel.VenueDetailsViewModel
import com.arusoft.mobile.searchlocations.util.CurrentLocation
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_venue_details.*
import javax.inject.Inject

class VenueDetailsFragment : DaggerFragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: VenueDetailsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(VenueDetailsViewModel::class.java)
        viewModel.venueDetailsLiveData.observe(requireActivity(), venueDetailsObserver)
    }

    override fun onStart() {
        super.onStart()

        val safeArguments = SecondLevelActivityArgs.fromBundle(requireActivity().intent.extras)
        viewModel.venueDetailsLiveData.postValue(safeArguments.venue)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_venue_details, container, false)

    private val venueDetailsObserver = Observer<VenueUIModel?> { response ->
        if (response != null) {
            when (response.status) {
                BaseViewModel.LOADING -> {
//                showLoader()
                }
                BaseViewModel.SUCCESS -> {
//                showLoader(false)
                    name_view?.text = response.name
                    category_name_view?.text = response.categoryName
                    venue_address_view?.text = response.address
                    venue_city_view?.text = response.city
                    venue_state_view?.text = response.state
                    venue_open_website_button?.apply {
                        visibility = if (response.url.isNotBlank()) View.VISIBLE else View.GONE
                        setOnClickListener {
                            response.url.takeIf { url -> url.isNotBlank() }?.let {
                                val intentBrowser = Intent(Intent.ACTION_VIEW)
                                intentBrowser.data = Uri.parse(it)
                                startActivity(intentBrowser)
                            }
                        }
                    }

                    viewModel.cityCenter.postValue(
                        MarkerOptions()
                            .position(
                                LatLng(
                                    CurrentLocation.CURRENT_LATITUDE,
                                    CurrentLocation.CURRENT_LONGITUDE
                                )
                            )
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .title(getString(R.string.city_center_label))
                    )

                }
                BaseViewModel.ERROR -> {
//                showLoader(false)
                }
            }
        } else {
            //TODO: Show error message
        }
    }


}