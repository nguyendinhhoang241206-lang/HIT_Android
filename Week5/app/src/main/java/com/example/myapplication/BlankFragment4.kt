package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentBlank3Binding
import com.example.myapplication.databinding.FragmentBlank4Binding
import com.example.myapplication.databinding.FragmentBlankBinding

class BlankFragment4 : Fragment() {

    private var _binding: FragmentBlank4Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlank4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSc.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentHeader, BlankFragment5())
                .addToBackStack("detail")
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}