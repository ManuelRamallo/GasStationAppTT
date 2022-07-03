package com.mramallo.gasstationapp.domain

import com.mramallo.gasstationapp.data.repository.GasStationRepository
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import javax.inject.Inject

class GetGasStationDetailUseCase @Inject constructor(private val repository: GasStationRepository){


    suspend operator fun invoke(idStore: String): GeneralDataGasStation?{
        val dataList = repository.getAllDataFromApi()
        var storeSelected: GeneralDataGasStation? = null

        dataList?.forEach { store ->
            if(store.id == idStore) {
                storeSelected = store
            }
        }

        return storeSelected
    }

}
