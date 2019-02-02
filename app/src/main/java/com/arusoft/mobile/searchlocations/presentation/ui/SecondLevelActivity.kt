package com.arusoft.mobile.searchlocations.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.presentation.model.BaseViewModel.Companion.SUCCESS
import com.arusoft.mobile.searchlocations.presentation.model.FavoriteVenueInput
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.arusoft.mobile.searchlocations.presentation.viewmodel.OnFavoriteViewModel
import com.arusoft.mobile.searchlocations.presentation.viewmodel.VenueDetailsViewModel
import com.arusoft.mobile.searchlocations.util.CurrentLocation.CURRENT_LATITUDE
import com.arusoft.mobile.searchlocations.util.CurrentLocation.CURRENT_LONGITUDE
import com.arusoft.mobile.searchlocations.util.VenuesUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_second_level.*
import javax.inject.Inject

class SecondLevelActivity : DaggerAppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var util: VenuesUtil

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: VenueDetailsViewModel
    private lateinit var favoriteViewModel: OnFavoriteViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_level)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(VenueDetailsViewModel::class.java)

        favoriteViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(OnFavoriteViewModel::class.java)


        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        supportFragmentManager.findFragmentById(R.id.venue_map)?.let {
            (it as SupportMapFragment).getMapAsync(this)
        }
        viewModel.markerLocationLiveData.observe(this, markerObserver)
        favoriteViewModel.onVenueFavorite.observe(this, favoriteObserver)
        viewModel.venueDetailsLiveData.observe(this, Observer { response ->
            response?.takeIf { it.status == SUCCESS }?.let { venue ->
                venue_details_favorite_button?.let {
                    util.setFavoriteAsset(it, venue.favorite)
                }
            }
        })

        venue_details_favorite_button?.setOnClickListener {
            viewModel.venueDetailsLiveData.value?.let {
                favoriteViewModel.favoriteVenue.postValue(FavoriteVenueInput(it, it.favorite.not()))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val cameraPosition =
            CameraPosition.Builder().target(LatLng(CURRENT_LATITUDE, CURRENT_LONGITUDE))
                .zoom(VenuesMapFragment.DEFAULT_MAP_ZOOM)
                .build()
        mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private val favoriteObserver = Observer<VenueUIModel?> { response ->
        response?.takeIf { it.status == SUCCESS }?.let { venue ->
            venue_details_favorite_button?.let {
                util.setFavoriteAsset(it, venue.favorite)
            }

            favoriteViewModel.favoriteVenue.postValue(null)
            viewModel.venueDetailsLiveData.postValue(venue)
            setResult(1, Intent().putExtra("venue", venue))
        }
    }

    private val markerObserver = Observer<MarkerOptions> { response ->
        mMap?.let {
            it.addMarker(response)
        }
    }

}