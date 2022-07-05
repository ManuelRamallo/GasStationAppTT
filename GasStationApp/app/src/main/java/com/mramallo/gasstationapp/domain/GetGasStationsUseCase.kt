package com.mramallo.gasstationapp.domain

import android.util.Log
import com.mramallo.gasstationapp.data.repository.GasStationRepository
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import javax.inject.Inject

class GetGasStationsUseCase @Inject constructor(private val repository: GasStationRepository){

    suspend operator fun invoke(): List<GeneralDataGasStation>? {
        val generalData = repository.getAllDataFromApi()

        return if(generalData != null) {
            generalData
        } else {
            Log.d("GASDATA", "Entra en el else porque el general data falla")
            null
        }
    }


}