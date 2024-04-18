package com.yz3ro.firebasesgnup.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.yz3ro.firebasesgnup.R
import com.yz3ro.firebasesgnup.data.datasource.AuthDataSource
import com.yz3ro.firebasesgnup.data.repo.AuthRepository
import com.yz3ro.firebasesgnup.databinding.FragmentOtpBinding
import com.yz3ro.firebasesgnup.ui.viewmodel.OtpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    private lateinit var viewModel: OtpViewModel
    private lateinit var verificationId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp, container, false)
        binding.fragmentOtp = this
        binding.otpToolBar = "Check OTP"
        arguments?.let {
            verificationId = OtpFragmentArgs.fromBundle(it).verificationId
        }
        viewModel.verificationResult.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                Navigation.findNavController(requireView()).navigate(R.id.action_otpFragment_to_homeFragment)
            } else {
                view?.let { Snackbar.make(it,"Wrong Otp!",Snackbar.LENGTH_SHORT).show() }
            }
        })
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authRepository = AuthRepository(AuthDataSource(requireActivity()))
        viewModel = OtpViewModel(authRepository)
    }
    fun otpBtn(){
        val otpCode = binding.TxtOtp.text.toString()
        viewModel.verifyOtp(verificationId, otpCode)
    }

}