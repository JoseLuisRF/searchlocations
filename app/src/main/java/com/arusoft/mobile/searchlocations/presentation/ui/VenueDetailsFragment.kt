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
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.arusoft.mobile.searchlocations.presentation.viewmodel.LocationsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_venue_details.*
import javax.inject.Inject

class VenueDetailsFragment : DaggerFragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: LocationsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(LocationsViewModel::class.java)
        viewModel.venueDetailsLiveData.observe(requireActivity(), venueDetailsObserver)
    }

    override fun onStart() {
        super.onStart()
        val safeArguments = VenueDetailsFragmentArgs.fromBundle(arguments)
        viewModel.selectedVenue.postValue(safeArguments.venueId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_venue_details, container, false)
    }

    private val venueDetailsObserver = Observer<VenueUIModel> { response ->
        when (response.status) {
            BaseViewModel.LOADING -> {
//                showLoader()
            }
            BaseViewModel.SUCCESS -> {
//                showLoader(false)
                name_view?.text = response.name

            }
            BaseViewModel.ERROR -> {
//                showLoader(false)
            }
        }
    }
}