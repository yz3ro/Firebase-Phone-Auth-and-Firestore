package com.yz3ro.firebasesgnup.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.yz3ro.firebasesgnup.R
import com.yz3ro.firebasesgnup.data.datasource.AuthDataSource
import com.yz3ro.firebasesgnup.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(private var repository: AuthRepository) : ViewModel() {

    private val _verificationResult = MutableLiveData<Boolean>()
    val verificationResult: LiveData<Boolean>
        get() = _verificationResult

    fun verifyOtp(verificationId: String, otpCode: String) {
        repository.verifyOtp(verificationId, otpCode){ success ->
            _verificationResult.postValue(success)
        }

    }
}