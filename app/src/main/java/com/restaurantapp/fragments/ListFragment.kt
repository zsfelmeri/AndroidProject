package com.restaurantapp.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.restaurantapp.MainActivity
import com.restaurantapp.MainActivity.Companion.CITIES
import com.restaurantapp.R
import com.restaurantapp.data.model.Restaurant
import com.restaurantapp.data.viewmodel.RestaurantViewModel
import com.restaurantapp.databinding.FragmentListBinding
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment()/*, NavigationView.OnNavigationItemSelectedListener*/ {
    private lateinit var binding: FragmentListBinding
    private lateinit var mRestaurantViewModel: RestaurantViewModel
    private val args by navArgs<ListFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        val spinner = binding.spinnerFilterByCity
        val spinnerAdapter = activity?.let {
            ArrayAdapter(it, android.R.layout.simple_spinner_item, CITIES)
        }
        spinner.adapter = spinnerAdapter
        spinner.setSelection(0)

        val listAdapter = ListAdapter(args.currentUser)
        val recyclerView = binding.recycleView
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mRestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when {
                    spinner.getItemAtPosition(position).toString().compareTo("Favorites") == 0 -> {
                        mRestaurantViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant ->
                            var filterList = emptyList<Restaurant>()
                            for(r in restaurant){
                                if(r.favorite){
                                    filterList = filterList.plus(r)
                                }
                            }
                            listAdapter.setData(filterList)
                        })
                    }
                    spinner.getItemAtPosition(position).toString().compareTo("All") == 0 -> {
                        mRestaurantViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant ->
                            listAdapter.setData(restaurant)
                        })
                    }
                    else -> {
                        mRestaurantViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant ->
                            var filterList = emptyList<Restaurant>()
                            for(r in restaurant){
                                if(r.city.compareTo(spinner.getItemAtPosition(position).toString()) == 0){
                                    filterList = filterList.plus(r)
                                }
                            }
                            listAdapter.setData(filterList)
                        })
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Nothing selected!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.etFilterByCity.addTextChangedListener {
            spinner.setSelection(0)
            mRestaurantViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant ->
                var filterList = emptyList<Restaurant>()
                for(r in restaurant){
                    if(r.city.toLowerCase().contains(binding.etFilterByCity.text.toString().toLowerCase())){
                        filterList = filterList.plus(r)
                    }
                }
                listAdapter.setData(filterList)
            })
        }

        recyclerView.setHasFixedSize(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_profile -> {
                val action = ListFragmentDirections.actionListFragmentToProfileFragment(args.currentUser)
                findNavController().navigate(action)
            }
            R.id.action_list -> {
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}