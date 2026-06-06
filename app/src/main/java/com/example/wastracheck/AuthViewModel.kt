package com.example.wastracheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastracheck.data.UserDao
import com.example.wastracheck.data.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val userDao: UserDao) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val user = userDao.login(email, password)
                if (user != null) {
                    _currentUser.value = user
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error("Email atau password salah")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val existingUser = userDao.getUserByEmail(email)
                if (existingUser != null) {
                    _authState.value = AuthState.Error("Email sudah terdaftar")
                    return@launch
                }
                val newUser = UserEntity(email, name, password)
                userDao.register(newUser)
                _currentUser.value = newUser
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Gagal mendaftar: ${e.message}")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
