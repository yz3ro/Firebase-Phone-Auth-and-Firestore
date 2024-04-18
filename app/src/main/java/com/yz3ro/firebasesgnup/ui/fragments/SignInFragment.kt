package com.yz3ro.firebasesgnup.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.yz3ro.firebasesgnup.R
import com.yz3ro.firebasesgnup.data.datasource.AuthDataSource
import com.yz3ro.firebasesgnup.data.repo.AuthRepository
import com.yz3ro.firebasesgnup.databinding.FragmentSignInBinding
import com.yz3ro.firebasesgnup.ui.viewmodel.SignInViewModel
import com.yz3ro.firebasesgnup.ui.viewmodel.SignUpViewModel
import com.yz3ro.firebasesgnup.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding : FragmentSignInBinding
    private lateinit var viewModel : SignInViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in, container, false)
        binding.fragmentSignIn = this
        binding.signInToolBar = "Sign In"
        return binding.root
    }
    fun signIn(){
        val ccp = binding.ccp.selectedCountryCode.toString()
        val number = binding.TxtNumber.text.toString()
        val phoneNumber = "+$ccp$number"
        viewModel.checkPhoneNumberInDatabase(phoneNumber, {
            view?.let {
                Snackbar.make(
                    it,
                    "the number is not registered. Would you like to register?.",
                    Snackbar.LENGTH_SHORT
                ).setAction("YES"){
                    Navigation.navigate(requireView(),R.id.action_signInFragment_to_signupFragment)
                }.show()
            }
        },{
            viewModel.sendVerificationCode(this,phoneNumber)
        })
    }
    fun signUp(){
        Navigation.navigate(requireView(),R.id.action_signInFragment_to_signupFragment)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authRepository = AuthRepository(AuthDataSource(requireActivity()))
        viewModel = SignInViewModel(authRepository)
    }
}