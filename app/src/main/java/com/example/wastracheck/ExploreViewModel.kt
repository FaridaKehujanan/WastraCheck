package com.example.wastracheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastracheck.data.MotifDao
import com.example.wastracheck.data.WastraMotif
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class ExploreViewModel(private val motifDao: MotifDao) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Mengambil data motif secara real-time berdasarkan query pencarian
    @OptIn(ExperimentalCoroutinesApi::class)
    val motifs: StateFlow<List<WastraMotif>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                motifDao.getAllMotifs()
            } else {
                motifDao.searchMotifs(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Alias untuk allMotifs sesuai kebutuhan ScanScreen
    val allMotifs: StateFlow<List<WastraMotif>> = motifs

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
