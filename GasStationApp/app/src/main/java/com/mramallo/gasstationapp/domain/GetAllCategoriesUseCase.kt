package com.mramallo.gasstationapp.domain

import android.util.Log
import com.mramallo.gasstationapp.data.repository.GasStationRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(private val repository: GasStationRepository) {

    suspend operator fun invoke(): List<String>? {
        val generalData = repository.getAllDataFromApi()

        var categoryList: MutableList<String> = mutableListOf()

        generalData?.forEach { data ->
            if(!categoryList.contains(data.category)){
                data.category?.let { categoryList.add(it) }
            }
        }

        return categoryList

    }

}