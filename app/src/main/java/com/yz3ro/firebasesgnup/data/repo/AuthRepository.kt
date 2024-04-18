package com.yz3ro.firebasesgnup.data.repo

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.yz3ro.firebasesgnup.data.datasource.AuthDataSource

class AuthRepository(private var dataSource: AuthDataSource) {
    fun sendVerificationCode(phoneNumber: String, callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        dataSource.sendVerificationCode(phoneNumber, callbacks)
    }
    fun verifyOtp(verificationId: String, otpCode: String, callback: (Boolean) -> Unit) {
        dataSource.verifyOtp(verificationId, otpCode, callback)
    }
    fun writeUserData(phoneNumber: String, uid: String) {
        dataSource.writeUserData(phoneNumber,uid)
    }
    fun checkPhoneNumberInDatabase(phoneNumber: String, onSuccess: () -> Unit, onFail: () -> Unit) {
        dataSource.checkPhoneNumberInDatabase(phoneNumber, onSuccess, onFail)
    }
}
