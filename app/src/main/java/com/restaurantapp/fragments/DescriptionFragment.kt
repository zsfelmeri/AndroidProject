package com.restaurantapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.restaurantapp.R
import com.restaurantapp.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDescriptionBinding
//    private val args by navArgs<DescriptionFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.id.descriptionFragment, container, false)

//        binding.tvDescription.setText(args.currentRestaurant.description)

        return binding.root
    }
}