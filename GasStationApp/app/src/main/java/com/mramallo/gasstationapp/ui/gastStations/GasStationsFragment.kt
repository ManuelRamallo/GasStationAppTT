package com.mramallo.gasstationapp.ui.gastStations

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mramallo.gasstationapp.R
import com.mramallo.gasstationapp.databinding.FragmentGasStationsBinding
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import com.mramallo.gasstationapp.ui.gasStationDetail.GasStationDetailFragment
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

        setupObserverHead()
        setupRecyclerAndObserverCategories()
        setupRecyclerAndObserverStores()
    }

    private fun setupObserverHead(){
        // Total stores to head
        viewModel.generalDataGasStationModel.observe(viewLifecycleOwner) {
            binding.tvTotalCountStores.text = it.size.toString()
        }

        // TODO - CONTINUE WITH FILTER BY DISTANCE WITH THE USER
        // Total found stores to head
        /*viewModel.generalDataGasStationModel.observe(viewLifecycleOwner) {
            var results: FloatArray = FloatArray(1)
            Location.distanceBetween(it[0].latitude?.toDouble()!!, it[0].longitude?.toDouble()!!, it[0].latitude?.toDouble()!!, it[0].longitude?.toDouble()!!, results)
            var distanceInmeters = results[0]
            var isWithin1km = distanceInmeters < 1000

            Log.d("GASDATA", "location -> $isWithin1km")
        }*/
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
        if(categorySelected == category) {
            filteredByCategory("")
            categorySelected = ""
            return
        }
        filteredByCategory(category)
        categorySelected = category
    }

    private fun setupRecyclerAndObserverStores() {
        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.rvStores.layoutManager = manager
        viewModel.generalDataGasStationModel.observe(viewLifecycleOwner) {
            binding.rvStores.adapter = GasStationAdapter(it) { store ->
                onGasStationSelected(store)
            }
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