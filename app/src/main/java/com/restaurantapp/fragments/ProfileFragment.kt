package com.restaurantapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.restaurantapp.R
import com.restaurantapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val args by navArgs<ProfileFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        binding.tvNameProfile.text = "${args.currentUser.firstName} ${args.currentUser.lastName}"
        binding.tvEmailProfile.text = args.currentUser.email
        binding.tvPhoneProfile.text = args.currentUser.phoneNumber
        binding.tvUsernameProfile.text = args.currentUser.username

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_profile -> {
                return true
            }
            R.id.action_list -> {
                val action = ProfileFragmentDirections.actionProfileFragmentToListFragment(args.currentUser)
                findNavController().navigate(action)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}