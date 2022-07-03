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

    fun onCreate(id_store: String) {
        Log.d("GASDATA", "ID STORE: $id_store")
        viewModelScope.launch {
            store.postValue(getGasStationDetailUseCase(id_store))
        }
    }
}
