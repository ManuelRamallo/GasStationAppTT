package com.mramallo.gasstationapp.ui.gasStationDetail

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mramallo.gasstationapp.R
import com.mramallo.gasstationapp.databinding.FragmentGasStationDetailBinding
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GasStationDetailFragment : Fragment() {

    private val viewModel: GasStationDetailViewModel by viewModels()
    private lateinit var binding: FragmentGasStationDetailBinding
    private var listener: OnGasStationDetailFragment? = null
    private lateinit var mMap: GoogleMap
    private var mapReady = false
    private lateinit var store: GeneralDataGasStation

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
        setupMapObserver()
        setupObservers()
        setButtonListener()
    }

    private fun setupObservers() {
        viewModel.store.observe(viewLifecycleOwner) { storeData ->
            if(storeData != null) {
                // Change title bar
                listener?.onChangeToolbarTitle(storeData.name, true)

                // Change
                if(store.logo != null) {
                    Glide.with(binding.ivImageStore.context).load(storeData.logo?.url).into(binding.ivImagePlaceholder)
                } else {
                    binding.ivImageStore.setImageDrawable(
                        context?.let {
                            ContextCompat.getDrawable(
                                it, R.drawable.placeholder_image)
                        })
                }

                binding.tvTitleInfo.text = store.name
                binding.tvSubtitleInfo.text = store.shortDescription
                binding.tvMoreInfo.text = store.openingHours
                binding.tvInfoCommerce.text = store.description
            }
        }


        // Loading on screen
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
            binding.llBodyInfo.isVisible = !it
        }


    }

    private fun setButtonListener() {
        /**
         * Button to open google maps with the same direction*/
        binding.tvOpenMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse("google.navigation:q=${store.latitude},${store.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }


    private fun updateMap() {
        if(mapReady) {
            val marker = LatLng(store.latitude!!.toDouble(), store.longitude!!.toDouble())
            mMap.addMarker(MarkerOptions().position(marker).title(store.name))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 6f))
        }
    }

    private fun setupMapObserver(){
        viewModel.store.observe(viewLifecycleOwner) { storeData ->
            store = storeData
            val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
            mapFragment.getMapAsync { googleMap ->
                mMap = googleMap
                mapReady = true
                updateMap()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.listener = context as? OnGasStationDetailFragment
    }

    interface OnGasStationDetailFragment {
        fun onChangeToolbarTitle(title: String?, showBack: Boolean)
    }
}
