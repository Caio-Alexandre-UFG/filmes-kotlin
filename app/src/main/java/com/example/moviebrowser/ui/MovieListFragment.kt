package com.example.moviebrowser.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviebrowser.databinding.FragmentMovieListBinding
import com.example.moviebrowser.model.Movie
import com.example.moviebrowser.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val movieAdapter = MovieAdapter(emptyList()) { movie ->
        val action = MovieListFragmentDirections.actionMoviesToDetail(movie.id)
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }

        fetchMovies()
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getPopularMovies()
                }
                updateUI(response.results)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Erro ao carregar filmes" + e.message, Toast.LENGTH_SHORT).show()
                Log.e( null, e.message.toString())
            }
        }
    }

    private fun updateUI(movies: List<Movie>) {
        binding.rvMovies.adapter = MovieAdapter(movies) { movie ->
            val action = MovieListFragmentDirections.actionMoviesToDetail(movie.id)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}