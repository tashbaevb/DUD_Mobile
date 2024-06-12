package com.example.dud_mobile.ui.lessons.reading;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dud_mobile.R;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dud_mobile.models.lessons.ReadingQuestion;
import com.example.dud_mobile.models.lessons.ReadingLesson;
import com.example.dud_mobile.remote_data.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadingFragment extends Fragment {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private Button checkButton;
    private int lessonId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reading, container, false);

        titleTextView = root.findViewById(R.id.titleTextView);
        descriptionTextView = root.findViewById(R.id.descriptionTextView);
        checkButton = root.findViewById(R.id.checkButton);

        lessonId = getArguments() != null ? getArguments().getInt("lessonId") : 1;
        loadReadingData(lessonId);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });

        return root;
    }

    private void loadReadingData(int lessonId) {
        Call<ReadingLesson> apiCall = RetrofitClient.getInstance().getApi().getReadingByLesson(lessonId);
        apiCall.enqueue(new Callback<ReadingLesson>() {
            @Override
            public void onResponse(Call<ReadingLesson> call, Response<ReadingLesson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ReadingLesson readingLesson = response.body();
                    displayReadingLesson(readingLesson);
                } else {
                    Toast.makeText(requireContext(), "Failed to load reading lesson", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReadingLesson> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to load reading lesson", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayReadingLesson(ReadingLesson readingLesson) {
        titleTextView.setText(readingLesson.getTitle());
        descriptionTextView.setText(readingLesson.getDescription());
        List<ReadingQuestion> questions = readingLesson.getQuestions();
    }

    private void checkAnswers() {
        // Отправить запрос на сервер для проверки ответов
        // Пример:
        // Call<CheckResponse> apiCall = RetrofitClient.getInstance().getApi().checkAnswers(lessonId, selectedOptions);
        // apiCall.enqueue(new Callback<CheckResponse>() {...});
    }
}
