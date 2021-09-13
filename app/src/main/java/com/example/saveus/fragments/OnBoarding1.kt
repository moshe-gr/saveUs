package com.example.saveus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.saveus.R

class OnBoarding1 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding1, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            OnBoarding1().apply {
                arguments = Bundle().apply {

                }
            }
    }
}