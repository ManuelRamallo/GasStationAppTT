package com.mramallo.gasstationapp.ui.gastStations

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.mramallo.gasstationapp.R
import com.mramallo.gasstationapp.databinding.FragmentGasStationsBinding
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GasStationsFragment : Fragment() {

    private val viewModel: GasStationViewModel by viewModels()
    private lateinit var binding: FragmentGasStationsBinding
    private var categorySelected: String = ""
    private var listener: OnGasStationFragment? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGasStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        listener?.onChangeToolbarTitle(getString(R.string.main_title_toolbar), false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onCreate()
        viewModel.getCategories()
        viewModel.storesOrderByDistance()

        setupObserverHead()
        setupRecyclerAndObserverCategories()
        setupRecyclerAndObserverStores(false)
    }

    private fun setupObserverHead() {
        // Total stores to head
        viewModel.generalDataGasStationModel.observe(viewLifecycleOwner) {
            binding.tvTotalCountStores.text = it.size.toString()
        }

        viewModel.generalDataGasStationModelByDistance.observe(viewLifecycleOwner) {
            binding.tvFoundCountStores.text = it.size.toString()
        }

        // Button to reload all stores
        binding.cvAllStores.setOnClickListener {
            viewModel.onCreate()
            renderButtonsHead(false)
        }

        // Button to load stores by distance
        binding.cvStoresDistance.setOnClickListener {
            viewModel.storesOrderByDistance()
            setupRecyclerAndObserverStores(true)
            renderButtonsHead(true)
        }

    }

    private fun setupRecyclerAndObserverCategories() {
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.rvCategories.layoutManager = manager
        viewModel.categories.observe(viewLifecycleOwner) {
            binding.rvCategories.adapter = CategoriesAdapter(it) { category ->
                onCategorySelected(category)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
            binding.rvCategories.isVisible = !it
            binding.rvStores.isVisible = !it
        }
    }

    private fun onCategorySelected(category: String) {
        /** If the category is selected 2 times, reload all the stores to default*/
        if (categorySelected == category) {
            filteredByCategory("")
            categorySelected = ""
            return
        }
        filteredByCategory(category)
        categorySelected = category
    }

    private fun setupRecyclerAndObserverStores(isByDistance: Boolean) {
        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.rvStores.layoutManager = manager
        if(!isByDistance) {
            viewModel.generalDataGasStationModel.observe(viewLifecycleOwner) {
                binding.rvStores.adapter = GasStationAdapter(it) { store ->
                    onGasStationSelected(store)
                }
            }
        } else {
            viewModel.generalDataGasStationModelByDistance.observe(viewLifecycleOwner) {
                binding.rvStores.adapter = GasStationAdapter(it) { store ->
                    onGasStationSelected(store)
                }
            }
        }
    }

    private fun renderButtonsHead(isByDistanceClicked: Boolean) {
        if (!isByDistanceClicked) {
            // Background cards
            binding.cvAllStores.setCardBackgroundColor(this.view?.context?.getColor(R.color.blue_dark)!!)
            binding.cvStoresDistance.setCardBackgroundColor(this.view?.context?.getColor(R.color.white)!!)

            // Text total stores
            binding.tvTotalCountStores.setTextColor(this.view?.context?.getColor(R.color.white)!!)
            binding.tvTotalCountStoresSubtitle.setTextColor(this.view?.context?.getColor(R.color.white)!!)

            // Text founded stores
            binding.tvFoundCountStores.setTextColor(this.view?.context?.getColor(R.color.orange_dark)!!)
            binding.tvFoundCountStoresSubtitle.setTextColor(this.view?.context?.getColor(R.color.gray_light)!!)

        } else {
            // Background cards
            binding.cvStoresDistance.setCardBackgroundColor(this.view?.context?.getColor(R.color.blue_dark)!!)
            binding.cvAllStores.setCardBackgroundColor(this.view?.context?.getColor(R.color.white)!!)

            // Text total stores
            binding.tvTotalCountStores.setTextColor(this.view?.context?.getColor(R.color.orange_dark)!!)
            binding.tvTotalCountStoresSubtitle.setTextColor(this.view?.context?.getColor(R.color.gray_light)!!)

            // Text founded stores
            binding.tvFoundCountStores.setTextColor(this.view?.context?.getColor(R.color.white)!!)
            binding.tvFoundCountStoresSubtitle.setTextColor(this.view?.context?.getColor(R.color.white)!!)
        }
    }

    private fun onGasStationSelected(store: GeneralDataGasStation) {
        findNavController().navigate(
            R.id.action_gasStationsFragment_to_gasStationDetailFragment,
            bundleOf("id_store" to store.id)
        )
    }


    private fun filteredByCategory(categorySelected: String) {
        viewModel.storesFilteredByCategories(categorySelected)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.rvStores.isVisible = !it
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.listener = context as? OnGasStationFragment
    }

    interface OnGasStationFragment {
        fun onChangeToolbarTitle(title: String?, showBack: Boolean)
    }
}