package com.yz3ro.firebasesgnup.data.datasource

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class AuthDataSource(private val activity: Activity) {
    private val db = FirebaseFirestore.getInstance()
    fun sendVerificationCode(
        phoneNumber: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            java.util.concurrent.TimeUnit.SECONDS,
            activity,
            callbacks
        )
    }

    fun verifyOtp(verificationId: String, otpCode: String, callback: (Boolean) -> Unit) {
        val credential = PhoneAuthProvider.getCredential(verificationId, otpCode)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }
    fun writeUserData(phoneNumber: String, uid: String) {
        val user = hashMapOf(
            "phoneNumber" to phoneNumber,
            "uid" to uid
        )

        db.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("AuthDataSource", "User data successfully written to Firestore")
            }
            .addOnFailureListener { e ->
                Log.e("AuthDataSource", "Error writing user data to Firestore: $e")
            }
    }
    fun checkPhoneNumberInDatabase(phoneNumber: String, onSuccess: () -> Unit, onFail: () -> Unit) {

        val usersRef = db.collection("users")
        usersRef.whereEqualTo("phoneNumber", phoneNumber)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onSuccess()
                } else {
                    onFail()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("AuthDataSource", "\n" + "Number could not be checked: $exception")
            }
    }
}