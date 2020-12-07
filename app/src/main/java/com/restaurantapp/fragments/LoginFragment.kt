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
    companion object {
        var USER_LOGIN = false
    }

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        USER_LOGIN = false

        binding.btnRegister.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            if (checkUser(binding.etUsername.text.toString(), binding.etPassword.text.toString())){
                val user = mUserViewModel.getUser(binding.etUsername.text.toString(), binding.etPassword.text.toString())
                val bundle = Bundle()
                bundle.putString("NAME", "${user.firstName} ${user.lastName}");
                bundle.putString("PHONE", user.phoneNumber)
                bundle.putString("EMAIL", user.email)
                bundle.putString("USERNAME", user.username);

                USER_LOGIN = true
                it.findNavController().navigate(R.id.action_loginFragment_to_listFragment, bundle)
            }
            else{
                Toast.makeText(requireContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun checkUser(username: String, password: String): Boolean{
        return mUserViewModel.loginUser(username, password) == 1
    }
}