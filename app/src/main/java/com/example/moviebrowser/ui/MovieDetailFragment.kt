package com.example.moviebrowser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.moviebrowser.databinding.FragmentMovieDetailBinding
import com.example.moviebrowser.model.Movie
import com.example.moviebrowser.network.RetrofitInstance

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.moviebrowser.R

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val movieId: Int by lazy {
        MovieDetailFragmentArgs.fromBundle(requireArguments()).movieId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadMovieDetails()
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_detail)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadMovieDetails() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getMovieDetails(movieId, "SUA_API_KEY_AQUI")
                }
                updateUI(response)
            } catch (e: Exception) {
                // Trate o erro aqui
            }
        }
    }

    private fun updateUI(movie: Movie) {
        binding.tvTitle.text = movie.title
        binding.tvOverview.text = movie.overview
        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
        binding.imgPoster.load(posterUrl) {
            crossfade(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
