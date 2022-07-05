package com.mramallo.gasstationapp.ui.gasStationDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mramallo.gasstationapp.domain.GetGasStationDetailUseCase
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GasStationDetailViewModel @Inject constructor(
    private val getGasStationDetailUseCase: GetGasStationDetailUseCase
): ViewModel() {

    val store = MutableLiveData<GeneralDataGasStation>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(id_store: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val storeData = getGasStationDetailUseCase(id_store)

            if(storeData != null) {
                store.postValue(storeData)
                isLoading.postValue(false)
            }

        }
    }
}
