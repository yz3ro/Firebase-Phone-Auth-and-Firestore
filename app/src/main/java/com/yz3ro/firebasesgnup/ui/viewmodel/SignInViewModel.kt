package com.yz3ro.firebasesgnup.ui.viewmodel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.yz3ro.firebasesgnup.data.repo.AuthRepository
import com.yz3ro.firebasesgnup.ui.fragments.SignInFragmentDirections
import com.yz3ro.firebasesgnup.ui.fragments.SignUpFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(var repository : AuthRepository) : ViewModel() {
    fun sendVerificationCode(fragment: Fragment, phoneNumber: String) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Otomatik doğrulama yapıldığında işlemler burada gerçekleştirilebilir
            }
            override fun onVerificationFailed(exception: FirebaseException) {
                // Doğrulama hatası olduğunda işlemler burada gerçekleştirilebilir
                Log.e("SignUpViewModel", "Verification failed: ${exception.message}")

            }
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                val action = SignInFragmentDirections.actionSignInFragmentToOtpFragment(verificationId)
                fragment.findNavController().navigate(action)
                Log.d("SignUpViewModel", "Verification code sent successfully")
            }
        }
        repository.sendVerificationCode(phoneNumber, callbacks)
    }
    fun checkPhoneNumberInDatabase(phoneNumber: String, onSuccess: () -> Unit, onFail: () -> Unit) {
        repository.checkPhoneNumberInDatabase(phoneNumber, onSuccess, onFail)
    }
}