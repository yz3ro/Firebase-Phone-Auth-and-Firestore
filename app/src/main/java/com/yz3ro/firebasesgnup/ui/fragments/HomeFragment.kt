package com.yz3ro.firebasesgnup.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.yz3ro.firebasesgnup.R
import com.yz3ro.firebasesgnup.databinding.FragmentHomeBinding
import com.yz3ro.firebasesgnup.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        binding.fragmentHome = this
        return binding.root
    }
    fun exit(){
        auth.signOut()
        view?.let { Navigation.navigate(it,R.id.action_homeFragment_to_signupFragment) }
    }
}