package com.mramallo.gasstationapp.domain

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.mramallo.gasstationapp.data.repository.GasStationRepository
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import javax.inject.Inject

class GetGasStationsByDistanceUseCase @Inject constructor(private val repository: GasStationRepository) {

    lateinit var locationManager: LocationManager

    suspend operator fun invoke(context: Context): List<GeneralDataGasStation>? {
        val generalData = repository.getAllDataFromApi()


        // Get current location
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val hasGps = locationManager.isLocationEnabled
        var locationByGps: Location? = null

        val gpsLocationListener =
            LocationListener { location -> locationByGps = location }

        if(hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }

        val lastKnowLocationByGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnowLocationByGps?.let {
            locationByGps = lastKnowLocationByGps
        }



        // Obtain distances to list stores
        val results = FloatArray(generalData!!.size)
        var listStoresByDistance: MutableList<GeneralDataGasStation> = mutableListOf()

        generalData.forEachIndexed { index, store ->
            Location.distanceBetween(store.latitude?.toDouble()!!, store.longitude?.toDouble()!!,
                locationByGps?.latitude!!, locationByGps?.longitude!!, results)
            var distanceInMeters = results[index]
            var isWithIn1km = distanceInMeters < 1000

            if (isWithIn1km && distanceInMeters.toDouble() != 0.0) {
                listStoresByDistance.add(store)
            }
        }


        return listStoresByDistance
    }


}