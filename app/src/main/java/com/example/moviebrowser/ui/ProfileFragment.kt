package com.example.moviebrowser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviebrowser.R
import com.example.moviebrowser.databinding.FragmentProfileBinding
import com.example.moviebrowser.model.Profile

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profiles = listOf(
        Profile("Alice", R.drawable.ic_avatar_placeholder),
        Profile("Bob", R.drawable.ic_avatar_placeholder),
        Profile("Carol", R.drawable.ic_avatar_placeholder),
        Profile("Dave", R.drawable.ic_avatar_placeholder)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvProfiles.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ProfileAdapter(profiles) { profile ->
                val action = ProfileFragmentDirections.actionProfileToMovies()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
