package com.example.dud_mobile.ui.dashboard;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import androidx.fragment.app.Fragment;
import com.example.dud_mobile.constant.ConstantAPI;
import com.example.dud_mobile.databinding.FragmentMovieDetailBinding;
import com.example.dud_mobile.models.content.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment {

    private FragmentMovieDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            Movie movie = getArguments().getParcelable("movie");
            if (movie != null) {
                displayMovieDetails(movie);
            } else {
                Log.e("MovieDetailFragment", "Movie data is null");
            }
        } else {
            Log.e("MovieDetailFragment", "Arguments are null");
        }

        return root;
    }

    private void displayMovieDetails(Movie movie) {
        binding.titleMovieDetail.setText(movie.getTitle());
        binding.countryMovieDetail.setText(movie.getCountry());
        binding.descriptionMovieDetail.setText(movie.getDescription());

        Picasso.get().load(ConstantAPI.BASE_URL + movie.getImgPath()).into(binding.imgMovieDetail);

        String videoPath = ConstantAPI.BASE_URL + movie.getFilePath();
        Uri videoUri = Uri.parse(videoPath);
        binding.videoViewMovie.setVideoURI(videoUri);
        Log.d("MovieDetailFragment", "Video URL: " + videoPath);

        MediaController mediaController = new MediaController(requireContext());
        mediaController.setAnchorView(binding.videoViewMovie);
        binding.videoViewMovie.setMediaController(mediaController);

        binding.videoViewMovie.setOnPreparedListener(mp -> {
            mp.start();
            Log.d("MovieDetailFragment", "Video started");
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
