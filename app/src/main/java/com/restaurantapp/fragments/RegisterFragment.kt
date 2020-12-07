package com.restaurantapp.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.restaurantapp.R
import com.restaurantapp.data.model.User
import com.restaurantapp.data.viewmodel.UserViewModel
import com.restaurantapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnRegisterRegister.setOnClickListener {
            if(inputCheck(binding.etFirstNameRegister.text.toString(), binding.etLastNameRegister.text.toString(), binding.etEmailRegister.text.toString(), binding.etUsernameRegister.text.toString(),
                            binding.etPhoneNumRegister.text.toString(), binding.etPasswordRegister.text.toString())){
                Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
            }
            else{
                if(checkUser(binding.etEmailRegister.text.toString(), binding.etUsernameRegister.text.toString(), binding.etPhoneNumRegister.text.toString())){
                    addUserIntoDatabase(binding.etFirstNameRegister.text.toString(),
                        binding.etLastNameRegister.text.toString(),
                        binding.etEmailRegister.text.toString(),
                        binding.etUsernameRegister.text.toString(),
                        binding.etPhoneNumRegister.text.toString(),
                        binding.etPasswordRegister.text.toString())
                    Toast.makeText(requireContext(), "You have been successfully registered!", Toast.LENGTH_SHORT).show()
                    it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                else{
                    Toast.makeText(requireContext(), "This user is already registered!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun checkUser(email: String, username: String, phoneNum: String): Boolean{
        val user = mUserViewModel.findUser(email, username, phoneNum)
        return user == 0
    }

    private fun addUserIntoDatabase(firstName: String, lastName: String, email: String, username: String, phoneNum: String, password: String){
        if(inputCheck(firstName, lastName, email, username, phoneNum, password)) {
            val user = User(firstName, lastName, email, username, phoneNum, password)
            mUserViewModel.addUser(user)
        }
        else{
            Toast.makeText(requireContext(), "Please fill all fields to register!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, email: String, username: String, phoneNum: String, password: String): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(email) && TextUtils.isEmpty(username) && TextUtils.isEmpty(phoneNum) && TextUtils.isEmpty(password))
    }
}