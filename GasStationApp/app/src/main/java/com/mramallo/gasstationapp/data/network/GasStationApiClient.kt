package com.mramallo.gasstationapp.data.network

import com.mramallo.gasstationapp.data.model.GeneralDataGasStationModel
import retrofit2.Response
import retrofit2.http.GET

interface GasStationApiClient {

    @GET("commerces/public")
    suspend fun getAllDataGasStation(): Response<List<GeneralDataGasStationModel>>
}