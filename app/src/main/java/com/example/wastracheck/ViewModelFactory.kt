package com.example.wastracheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wastracheck.data.MotifDao
import com.example.wastracheck.data.UserDao

class ViewModelFactory(
    private val userDao: UserDao? = null,
    private val motifDao: MotifDao? = null
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(userDao!!) as T
            }
            modelClass.isAssignableFrom(LibraryViewModel::class.java) -> {
                LibraryViewModel(motifDao!!) as T
            }
            modelClass.isAssignableFrom(ExploreViewModel::class.java) -> {
                ExploreViewModel(motifDao!!) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
