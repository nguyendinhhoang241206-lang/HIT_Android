package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentBlank3Binding
import com.example.myapplication.databinding.FragmentBlank4Binding
import com.example.myapplication.databinding.FragmentBlank5Binding
import com.example.myapplication.databinding.FragmentBlank6Binding
import com.example.myapplication.databinding.FragmentBlank7Binding
import com.example.myapplication.databinding.FragmentBlankBinding

class BlankFragment7 : Fragment() {

    private var _binding: FragmentBlank7Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlank7Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBtl.setOnClickListener {
            parentFragmentManager.popBackStack(
                null,
                androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}