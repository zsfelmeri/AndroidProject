package com.restaurantapp.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.restaurantapp.R
import com.restaurantapp.data.model.Restaurant
import com.restaurantapp.data.viewmodel.RestaurantViewModel
import com.restaurantapp.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDescriptionBinding
    private lateinit var mRestaurantViewModel: RestaurantViewModel
    private val args by navArgs<DescriptionFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_description, container, false)
        mRestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        binding.tvDescription.text = args.currentRestaurant.description

        if(args.currentRestaurant.favorite){
            binding.ivFavoriteSet.setImageResource(R.drawable.filled_start)
        }
        else{
            binding.ivFavoriteSet.setImageResource(R.drawable.empty_star)
        }

        binding.btnOpenMaps.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${args.currentRestaurant.lat},${args.currentRestaurant.lng}"))
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        binding.btnCall.setOnClickListener {
            requestPermissions()
            if(hasCallPhonePermission()) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${args.currentRestaurant.phone}"))
                startActivity(intent)
            }
        }

        binding.ivFavoriteSet.setOnClickListener {
            if(!args.currentRestaurant.favorite) {
                updateRestaurant(true)
                binding.ivFavoriteSet.setImageResource(R.drawable.filled_start)
            }
            else{
                updateRestaurant(false)
                binding.ivFavoriteSet.setImageResource(R.drawable.empty_star)
            }
        }

        return binding.root
    }

    private fun updateRestaurant(fav: Boolean){
        val restaurant = Restaurant(
                args.currentRestaurant.id,
                args.currentRestaurant.name,
                args.currentRestaurant.address,
                args.currentRestaurant.city,
                args.currentRestaurant.state,
                args.currentRestaurant.area,
                args.currentRestaurant.postal_code,
                args.currentRestaurant.country,
                args.currentRestaurant.phone,
                args.currentRestaurant.lat,
                args.currentRestaurant.lng,
                args.currentRestaurant.price,
                args.currentRestaurant.reserve_url,
                args.currentRestaurant.mobile_reserve_url,
                args.currentRestaurant.image_url,
                fav,
                args.currentRestaurant.description
        )
        mRestaurantViewModel.updateFavorites(restaurant)
    }

    private fun hasCallPhonePermission() = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

    private fun hasReadExternalStoragePermission() =
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions(){
        val permissionsToRequest = mutableListOf<String>()
        if(!hasCallPhonePermission()){
            permissionsToRequest.add(Manifest.permission.CALL_PHONE)
        }
        if(!hasReadExternalStoragePermission()){
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if(permissionsToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(requireActivity(), permissionsToRequest.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()){
            for(i in grantResults.indices){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("Permissions", "${permissions[i]} granted")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_profile -> {
                val action = DescriptionFragmentDirections.actionDescriptionFragmentToProfileFragment(args.currentUser)
                findNavController().navigate(action)
            }
            R.id.action_list -> {
                findNavController().navigate(R.id.action_descriptionFragment_to_listFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}