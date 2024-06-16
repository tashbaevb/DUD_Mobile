package com.example.dud_mobile.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.dud_mobile.R;
import com.example.dud_mobile.databinding.FragmentMovieBinding;
import com.example.dud_mobile.models.content.Movie;
import com.example.dud_mobile.remote_data.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MovieFragment extends Fragment {

    private FragmentMovieBinding binding;
    private MovieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new MovieAdapter(getActivity(), null);
        binding.rvCatalogM.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvCatalogM.setAdapter(adapter);

        loadMoviesData();

        return root;
    }

    private void loadMoviesData() {
        Call<List<Movie>> apiCall = RetrofitClient.getInstance().getApi().getAllMovies();
        apiCall.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setMovies(response.body());
                    adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Movie selectedMovie = adapter.getMovies().get(position);
                            navigateToMovieDetail(selectedMovie);
                        }
                    });
                } else {
                    Toast.makeText(requireActivity(), "Failed to load movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e("MovieFragment", "Failed to fetch movies: " + t.getMessage());
                Toast.makeText(requireActivity(), "Failed to load movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMovieDetail(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        Navigation.findNavController(requireView()).navigate(R.id.action_navigation_movie_to_movieDetailFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
