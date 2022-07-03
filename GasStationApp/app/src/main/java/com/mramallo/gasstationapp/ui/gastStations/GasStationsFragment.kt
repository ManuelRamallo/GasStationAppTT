package com.mramallo.gasstationapp.ui.gastStations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mramallo.gasstationapp.databinding.FragmentGasStationsBinding
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GasStationsFragment : Fragment() {

    private val viewModel: GasStationViewModel by viewModels()
    private lateinit var binding: FragmentGasStationsBinding
    private var categorySelected: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGasStationsBinding.inflate(inflater, container, false)
        return binding.root
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
            binding.progressCategory.isVisible = it
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
        Log.d("GASDATA", "La store seleccionada es: ${store.name}")
        // TODO - AQUÍ LO QUE TENEMOS QUE HACER ES IRNOS A LA PANTALLA DE INFO DEL COMERCIO SELECCIONADO
    }


    private fun filteredByCategory(categorySelected: String) {
        viewModel.storesFilteredByCategories(categorySelected)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.rvStores.isVisible = !it
        }
    }

}