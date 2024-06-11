package com.example.dud_mobile.ui.lessons;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.dud_mobile.R;
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

        // Инициализация адаптера
        adapter = new LessonsAdapter(getActivity(), null);

        // Установка LayoutManager для RecyclerView
        binding.rvCatalogM.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Установка адаптера для RecyclerView
        binding.rvCatalogM.setAdapter(adapter);

        // Получение levelId из аргументов фрагмента
        int levelId = getArguments() != null ? getArguments().getInt("levelId") : 1;

        // Загрузка данных с сервера с использованием полученного levelId
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
                    // Обновление данных в адаптере
                    adapter.setLessons(response.body());
                } else {
                    // Обработка ошибок загрузки данных
                    Toast.makeText(requireActivity(), "Failed to load lessons", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                // Обработка ошибок загрузки данных
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
