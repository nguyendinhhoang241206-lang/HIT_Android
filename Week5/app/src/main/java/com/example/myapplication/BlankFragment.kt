package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {

    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!

    // ① Inflate layout → trả về View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    // ② View đã sẵn sàng → bắt đầu thao tác UI
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCr.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentHeader, BlankFragment2())
                .addToBackStack("detail")
                .commit()
        }
    }

    // ③ Hủy binding để tránh memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}