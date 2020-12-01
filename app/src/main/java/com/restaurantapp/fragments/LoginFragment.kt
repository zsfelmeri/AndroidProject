package com.restaurantapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.restaurantapp.R
import com.restaurantapp.data.viewmodel.UserViewModel
import com.restaurantapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
//            if (checkUser(binding.etUsername.text.toString(), binding.etPassword.text.toString())){
//                it.findNavController().navigate(R.id.action_loginFragment_to_listFragment)
//            }
//            else{
//                Toast.makeText(requireContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show()
//            }
        }

        return binding.root
    }

//    private fun checkUser(username: String, password: String): Boolean{
//        mUserViewModel.loginUser(username, password)
//        return mUserViewModel.checkLogin.value != 0
//    }
}