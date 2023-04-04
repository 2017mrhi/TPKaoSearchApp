package com.mrhi2023.tpkaosearchapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mrhi2023.tpkaosearchapp.databinding.FragmentPlaceListBinding
import com.mrhi2023.tpkaosearchapp.databinding.FragmentPlaceMapBinding

class PlaceMapFragment : Fragment() {

    lateinit var binding: FragmentPlaceMapBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPlaceMapBinding.inflate(inflater, container, false)
        return binding.root
    }
}