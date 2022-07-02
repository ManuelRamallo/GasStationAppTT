package com.mramallo.gasstationapp.data.repository

import com.mramallo.gasstationapp.data.network.GasStationService
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import com.mramallo.gasstationapp.domain.model.toDomain
import javax.inject.Inject

class GasStationRepository @Inject constructor(
    private val service: GasStationService
) {

    suspend fun getAllDataFromApi(): List<GeneralDataGasStation>? {
        val response = service.getAllDataGasStation()
        return response?.map { it.toDomain() }
    }

}