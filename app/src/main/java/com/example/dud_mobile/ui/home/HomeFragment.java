package com.example.dud_mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.dud_mobile.databinding.FragmentHomeBinding;
import com.example.dud_mobile.ui.lessons.LessonsFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.dud_mobile.R;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.cardA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLessonsFragment(1);
            }
        });

        binding.cardA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLessonsFragment(2);
            }
        });

        binding.cardB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLessonsFragment(3);
            }
        });

        binding.cardMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_navigation_home_to_navigation_dashboard);
            }
        });

        binding.cardBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_navigation_home_to_navigation_notifications);
            }
        });

        return root;
    }

    private void navigateToLessonsFragment(int levelId) {
        Bundle bundle = new Bundle();
        bundle.putInt("levelId", levelId);

        Navigation.findNavController(requireActivity(), R.id.nav_host)
                .navigate(R.id.action_navigation_home_to_lessonsFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


