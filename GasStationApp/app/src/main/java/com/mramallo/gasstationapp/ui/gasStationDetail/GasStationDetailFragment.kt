package com.mramallo.gasstationapp.ui.gasStationDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mramallo.gasstationapp.databinding.FragmentGasStationDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GasStationDetailFragment : Fragment() {

    private val viewModel: GasStationDetailViewModel by viewModels()
    private lateinit var binding: FragmentGasStationDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGasStationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("id_store")?.let { viewModel.onCreate(it) }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.store.observe(viewLifecycleOwner) { storeData ->
            if(storeData != null) {
                binding.tvNameStore.text = storeData.name
                binding.tvDescriptionStore.text = storeData.description
            }
        }
    }
}
