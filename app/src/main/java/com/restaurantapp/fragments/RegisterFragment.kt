package com.restaurantapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
            if(binding.etFirstNameRegister.text.toString().isEmpty() || binding.etLastNameRegister.text.toString().isEmpty() || binding.etEmailRegister.text.toString().isEmpty() ||
                    binding.etUsernameRegister.text.toString().isEmpty() || binding.etPhoneNumRegister.text.isEmpty()){
                Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
            }
            else{
                val user = checkUser(binding.etEmailRegister.text.toString(), binding.etUsernameRegister.text.toString(), binding.etPhoneNumRegister.text.toString())
                if(user == 0){
                    addUserIntoDatabase(binding.etFirstNameRegister.text.toString(),
                        binding.etLastNameRegister.text.toString(),
                    binding.etEmailRegister.text.toString(),
                    binding.etUsernameRegister.text.toString(),
                    binding.etPhoneNumRegister.text.toString())
                    Toast.makeText(requireContext(), "You have been successfully registered!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                else{
                    Toast.makeText(requireContext(), "This user is already registered!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun checkUser(email: String, username: String, phoneNum: String): Int{
        return Integer.parseInt(mUserViewModel.findUser(email, username, phoneNum).toString())
    }

    private fun addUserIntoDatabase(firstName: String, lastName: String, email: String, username: String, phoneNum: String){
        val user = User(0, firstName, lastName, email, username, phoneNum)

        mUserViewModel.addUser(user)
    }
}