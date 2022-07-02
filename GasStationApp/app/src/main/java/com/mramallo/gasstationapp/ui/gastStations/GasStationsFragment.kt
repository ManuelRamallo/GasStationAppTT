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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GasStationsFragment : Fragment() {

    private val viewModel: GasStationViewModel by viewModels()
    private lateinit var binding: FragmentGasStationsBinding

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
                onGasStationSelected(category)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressCategory.isVisible = it
            binding.rvCategories.isVisible = !it
            binding.rvStores.isVisible = !it
        }
    }

    private fun onGasStationSelected(category: String) {
        Log.d("GASDATA", "La categoría seleccionada es: $category")
        // TODO - YA TENEMOS LA CATEGORÍA SELECCIONADA, SOLO FALTA MOSTRAR LA LISTA DE NUEVO FILTRANDO POR LA CATEGORÍA SELECCIONADA
    }


}