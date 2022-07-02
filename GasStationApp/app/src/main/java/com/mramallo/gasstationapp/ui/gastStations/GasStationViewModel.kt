package com.mramallo.gasstationapp.ui.gastStations

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mramallo.gasstationapp.domain.GetAllCategoriesUseCase
import com.mramallo.gasstationapp.domain.GetGasStationsUseCase
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GasStationViewModel @Inject constructor(
    private val getGasStationsUseCase: GetGasStationsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
): ViewModel() {

    val generalDataGasStationModel = MutableLiveData<List<GeneralDataGasStation>>()
    val isLoading = MutableLiveData<Boolean>()
    val categories = MutableLiveData<List<String>>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val generalData = getGasStationsUseCase() ?: listOf()

            if(generalData.isNotEmpty()) {
                generalDataGasStationModel.postValue(generalData)
                isLoading.postValue(false)
            } else {
                Log.d("GASDATA", "Error al recoger los datos, la lista está vacía")
            }


        }
    }

     fun getCategories() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val categoryList = getAllCategoriesUseCase() ?: listOf()

            if (categoryList.isNotEmpty()) {
                categories.postValue(categoryList)
                isLoading.postValue(false)
            } else {
                Log.d("GASDATA", "Error al recoger las categorías, la lista está vacía")
            }
        }
    }


}