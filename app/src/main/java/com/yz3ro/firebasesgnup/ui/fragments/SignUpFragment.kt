package com.yz3ro.firebasesgnup.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.yz3ro.firebasesgnup.R
import com.yz3ro.firebasesgnup.data.datasource.AuthDataSource
import com.yz3ro.firebasesgnup.data.repo.AuthRepository
import com.yz3ro.firebasesgnup.databinding.FragmentSignupBinding
import com.yz3ro.firebasesgnup.ui.viewmodel.SignUpViewModel
import com.yz3ro.firebasesgnup.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding : FragmentSignupBinding
    private lateinit var viewModel : SignUpViewModel
    private var user = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_signup, container, false)
        binding.fragmentSignUp = this
        binding.signUpToolBarr = "SignUp"
        return binding.root
    }


    fun SignUp(){
        val ccp = binding.ccp.selectedCountryCode.toString()
        val number = binding.TxtNumber.text.toString()
        val phoneNumber = "+$ccp$number"
        Log.d("SignUpFragment", "Phone number: $phoneNumber")
        viewModel.checkPhoneNumberInDatabase(phoneNumber, {
            // Numara sistemde kayıtlı değilse devam etme işlemi
            viewModel.sendVerificationCode(this,phoneNumber)
        }, {
            view?.let {
                Snackbar.make(
                    it,
                    "Phone number already exist. Do you want to log in?.",
                    Snackbar.LENGTH_SHORT
                ).setAction("YES"){
                    Navigation.navigate(requireView(),R.id.action_signupFragment_to_signInFragment)
                }.show()
            }
        })
    }
    fun SignIn(){
        Navigation.navigate(requireView(),R.id.action_signupFragment_to_signInFragment)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authRepository = AuthRepository(AuthDataSource(requireActivity()))
        viewModel = SignUpViewModel(authRepository)
    }



    override fun onStart() {
        super.onStart()
        if (user != null){
            view?.let { Navigation.navigate(it,R.id.action_signupFragment_to_homeFragment) }
        }
    }


}