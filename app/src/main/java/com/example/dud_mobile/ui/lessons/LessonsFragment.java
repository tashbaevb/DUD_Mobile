package com.example.dud_mobile.ui.lessons;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.dud_mobile.databinding.FragmentLessonsBinding;
import com.example.dud_mobile.models.Lesson;
import com.example.dud_mobile.remote_data.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonsFragment extends Fragment {

    private FragmentLessonsBinding binding;
    private LessonsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLessonsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new LessonsAdapter(getActivity(), null);

        binding.rvCatalogM.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.rvCatalogM.setAdapter(adapter);

        int levelId = getArguments() != null ? getArguments().getInt("levelId") : 1;

        loadLessonsData(levelId);

        return root;
    }

    private void loadLessonsData(int levelId) {
        // Получение списка уроков с сервера для заданного уровня
        Call<List<Lesson>> apiCall = RetrofitClient.getInstance().getApi().getLessonsByLevel(levelId);
        apiCall.enqueue(new Callback<List<Lesson>>() {
            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setLessons(response.body());
                } else {
                    Toast.makeText(requireActivity(), "Failed to load lessons", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                Log.e("LessonsFragment", "Failed to fetch lessons: " + t.getMessage());
                Toast.makeText(requireActivity(), "Failed to load lessons", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

