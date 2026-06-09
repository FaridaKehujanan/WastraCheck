package com.example.wastracheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastracheck.data.MotifDao
import com.example.wastracheck.data.WastraMotif
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class LibraryViewModel(private val motifDao: MotifDao) : ViewModel() {

    private val _selectedRegion = MutableStateFlow("Semua")
    val selectedRegion: StateFlow<String> = _selectedRegion

    @OptIn(ExperimentalCoroutinesApi::class)
    val savedMotifs: StateFlow<List<WastraMotif>> = _selectedRegion
        .flatMapLatest { region ->
            if (region == "Semua") {
                motifDao.getAllMotifs()
            } else {
                motifDao.getMotifsByRegion(region)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setRegion(region: String) {
        _selectedRegion.value = region
    }
}
