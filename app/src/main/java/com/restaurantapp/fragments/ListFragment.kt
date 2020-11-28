package com.restaurantapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurantapp.R
import com.restaurantapp.data.repository.RestaurantRepository
import com.restaurantapp.data.viewmodel.RestaurantViewModel
import com.restaurantapp.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var mRestaurantViewModel: RestaurantRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        val adapter = ListAdapter()
        val recyclerView = binding.recycleView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mRestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        mRestaurantViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant ->
            adapter.setData(restaurant)
        })

        return binding.root
    }
}