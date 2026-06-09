package com.example.wastracheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastracheck.data.MotifDao
import com.example.wastracheck.data.WastraMotif
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ExploreViewModel(private val motifDao: MotifDao) : ViewModel() {

    // Mengambil semua data motif dari Room Database secara real-time
    val motifs: StateFlow<List<WastraMotif>> = motifDao.getAllMotifs()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Alias untuk allMotifs sesuai kebutuhan ScanScreen
    val allMotifs: StateFlow<List<WastraMotif>> = motifs

    // State dummy untuk loading dan error agar ExploreScreen tidak break
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
}
