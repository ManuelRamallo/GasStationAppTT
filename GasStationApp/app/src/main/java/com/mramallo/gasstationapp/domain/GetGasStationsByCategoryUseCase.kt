package com.mramallo.gasstationapp.domain

import android.util.Log
import com.mramallo.gasstationapp.data.repository.GasStationRepository
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import javax.inject.Inject

class GetGasStationsByCategoryUseCase @Inject constructor(private val repository: GasStationRepository) {


    suspend operator fun invoke(categorySelected: String): MutableList<GeneralDataGasStation> {
        val generalData = repository.getAllDataFromApi()

        var storesByCategory: MutableList<GeneralDataGasStation> = mutableListOf()

        generalData?.forEach { data ->
            if (data.category.equals(categorySelected)) {
                storesByCategory.add(data)
            }
        }

        if (categorySelected == "") return generalData as MutableList<GeneralDataGasStation>


        return storesByCategory
    }

}