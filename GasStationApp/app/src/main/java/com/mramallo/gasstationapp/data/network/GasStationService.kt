package com.mramallo.gasstationapp.data.network

import android.util.Log
import com.mramallo.gasstationapp.data.model.GeneralDataGasStationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GasStationService @Inject constructor( private val apiClient: GasStationApiClient){

    suspend fun getAllDataGasStation(): List<GeneralDataGasStationModel>? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getAllDataGasStation()
            response.body()
        }
    }

}