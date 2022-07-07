package com.mramallo.gasstationapp.ui.gastStations

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mramallo.gasstationapp.domain.GetAllCategoriesUseCase
import com.mramallo.gasstationapp.domain.GetGasStationsByCategoryUseCase
import com.mramallo.gasstationapp.domain.GetGasStationsByDistanceUseCase
import com.mramallo.gasstationapp.domain.GetGasStationsUseCase
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GasStationViewModel @Inject constructor(
    private val getGasStationsUseCase: GetGasStationsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getGasStationsByCategoryUseCase: GetGasStationsByCategoryUseCase,
    private val getGasStationsByDistanceUseCase: GetGasStationsByDistanceUseCase,
    application: Application
): AndroidViewModel(application) {

    val generalDataGasStationModel = MutableLiveData<List<GeneralDataGasStation>>()
    val isLoading = MutableLiveData<Boolean>()
    val categories = MutableLiveData<List<String>>()
    val generalDataGasStationModelByDistance = MutableLiveData<List<GeneralDataGasStation>>()
    @SuppressLint("StaticFieldLeak")
    val context: Context = getApplication<Application>().applicationContext


    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val generalData = getGasStationsUseCase() ?: listOf()

            if(generalData.isNotEmpty()) {
                generalDataGasStationModel.postValue(generalData)
                isLoading.postValue(false)
            } else {
                Log.d("GASDATA", "Error al recoger los datos, la lista está vacía")
                isLoading.postValue(false)
            }


        }
    }

     fun getCategories() {
        viewModelScope.launch {
            //isLoading.postValue(true)
            val categoryList = getAllCategoriesUseCase() ?: listOf()

            if (categoryList.isNotEmpty()) {
                categories.postValue(categoryList)
                //isLoading.postValue(false)
            } else {
                Log.d("GASDATA", "Error al recoger las categorías, la lista está vacía")
            }
        }
    }

    fun storesFilteredByCategories(category: String) {
        viewModelScope.launch {
            isLoading.postValue(true)

            val storesByCategory = getGasStationsByCategoryUseCase(category)

            if(storesByCategory.isNotEmpty()) {
                generalDataGasStationModel.postValue(storesByCategory)
                isLoading.postValue(false)
            } else {
                Log.d("GASDATA", "Los comercios recogidas por categoría está vacío")
                isLoading.postValue(false)
            }
        }
    }

    fun storesOrderByDistance(){
        viewModelScope.launch {
            isLoading.postValue(true)

            val storesByDistance = getGasStationsByDistanceUseCase(context)

            if(!storesByDistance.isNullOrEmpty()) {
                generalDataGasStationModelByDistance.postValue(storesByDistance)
                isLoading.postValue(false)
            } else {
                Log.d("GASDATA", "Los comercios recogidos por distancia está vacío")
                isLoading.postValue(false)
            }


        }
    }


}