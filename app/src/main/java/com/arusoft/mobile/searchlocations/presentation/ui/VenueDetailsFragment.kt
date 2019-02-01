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
import com.arusoft.mobile.searchlocations.presentation.viewmodel.VenueDetailsViewModel
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