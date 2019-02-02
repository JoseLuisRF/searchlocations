package com.arusoft.mobile.searchlocations.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.arusoft.mobile.searchlocations.R
import com.arusoft.mobile.searchlocations.presentation.model.BaseViewModel
import com.arusoft.mobile.searchlocations.presentation.model.FavoriteVenueInput
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.arusoft.mobile.searchlocations.presentation.model.VenueSearchInput
import com.arusoft.mobile.searchlocations.presentation.model.VenuesUIModel
import com.arusoft.mobile.searchlocations.presentation.ui.adapter.VenuesAdapter
import com.arusoft.mobile.searchlocations.presentation.viewmodel.LocationsViewModel
import com.arusoft.mobile.searchlocations.presentation.viewmodel.OnFavoriteViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_locations_list.*
import javax.inject.Inject

class VenuesHomeFragment : DaggerFragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LocationsViewModel
    private lateinit var favoriteViewModel: OnFavoriteViewModel

    private lateinit var adapter: VenuesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(LocationsViewModel::class.java)
        favoriteViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(OnFavoriteViewModel::class.java)

        viewModel.venuesLiveData.observe(requireActivity(), venuesObserver)
        favoriteViewModel.onVenueFavorite.observe(requireActivity(), onFavoriteObserver)
        viewModel.currentLocation.postValue(VenueSearchInput())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_locations_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter =
            VenuesAdapter(
                clickListener = onItemClickListener,
                favoriteClickListener = favoriteClickListener
            )
        locations_recycler_view.adapter = adapter
        open_map_button.setOnClickListener {
            findNavController().navigate(R.id.venuesMapFragment)
        }
    }

    private val venuesObserver = Observer<VenuesUIModel> { response ->
        when (response.status) {
            BaseViewModel.LOADING -> {
                showLoader()
            }
            BaseViewModel.SUCCESS -> {
                showLoader(false)
                adapter.addAll(response.venues)
            }
            BaseViewModel.ERROR -> {
                showLoader(false)
            }
        }
    }

    private fun showLoader(visible: Boolean = true) {
        progress_loader_view?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private val onItemClickListener: (VenueUIModel, Int) -> Unit = { item, _ ->
        val intent = Intent(requireContext(), SecondLevelActivity::class.java)
        intent.putExtra("venue", item)
        startActivityForResult(intent, 1)
    }

    private val favoriteClickListener: (VenueUIModel) -> Unit = { item ->
        favoriteViewModel.favoriteVenue.postValue(FavoriteVenueInput(item, item.favorite.not()))
    }


    private val onFavoriteObserver = Observer<VenueUIModel?> { response ->
        response?.takeIf { it.status == BaseViewModel.SUCCESS }?.let {
            adapter.updateItem(response)
            favoriteViewModel.favoriteVenue.postValue(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.getParcelableExtra<VenueUIModel>("venue")?.let {
            adapter.updateItem(it)
        }
    }
}