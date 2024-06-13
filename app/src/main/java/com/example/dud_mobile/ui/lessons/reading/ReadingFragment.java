package com.example.dud_mobile.ui.lessons.reading;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;

import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import com.example.dud_mobile.R;
import com.example.dud_mobile.models.lessons.CheckAnswer;
import com.example.dud_mobile.models.lessons.reading.ReadingLesson;
import com.example.dud_mobile.models.lessons.reading.ReadingQuestion;
import com.example.dud_mobile.remote_data.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadingFragment extends Fragment {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private Button checkButton;
    private int lessonId;
    private ReadingLesson readingLesson;

    private LinearLayout optionsContainer; // Изменено с RadioGroup на LinearLayout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reading, container, false);

        titleTextView = root.findViewById(R.id.titleTextView);
        descriptionTextView = root.findViewById(R.id.descriptionTextView);
        checkButton = root.findViewById(R.id.checkButton);
        optionsContainer = root.findViewById(R.id.optionsContainer); // Инициализация нового контейнера

        lessonId = getArguments() != null ? getArguments().getInt("lessonId") : 1;
        loadReadingData(lessonId);


        Button nextButton = root.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_readingFragment_to_listeningFragment);
            }
        });

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
                    readingLesson = response.body(); // Присваиваем значение переменной readingLesson
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

        // Очистить существующие виды в контейнере
        optionsContainer.removeAllViews();

        // Создать радиокнопки для каждого вопроса и его вариантов
        for (ReadingQuestion question : questions) {
            // Создать новую группу радиокнопок для каждого вопроса
            RadioGroup questionRadioGroup = new RadioGroup(requireContext());
            questionRadioGroup.setOrientation(LinearLayout.VERTICAL);
            questionRadioGroup.setTag(String.valueOf(question.getId())); // Установить тег для идентификации группы

            // Создать текстовое представление для вопроса и добавить его в группу радиокнопок
            TextView questionTextView = new TextView(requireContext());
            questionTextView.setText(question.getQuestion());
            questionRadioGroup.addView(questionTextView);

            // Создать радиокнопки для каждого варианта и добавить их в группу радиокнопок
            addOptionRadioButtons(question, questionRadioGroup);

            // Добавить группу радиокнопок для этого вопроса в контейнер
            optionsContainer.addView(questionRadioGroup);
        }
    }

    private void addOptionRadioButtons(ReadingQuestion question, RadioGroup questionRadioGroup) {
        // Создать радиокнопки для каждого варианта и добавить их в группу радиокнопок
        addOptionRadioButton(questionRadioGroup, question.getOption1(), 1);
        addOptionRadioButton(questionRadioGroup, question.getOption2(), 2);
        addOptionRadioButton(questionRadioGroup, question.getOption3(), 3);
    }

    private void addOptionRadioButton(RadioGroup radioGroup, String optionText, int optionId) {
        // Создать новую радиокнопку для варианта
        RadioButton optionRadioButton = new RadioButton(requireContext());
        optionRadioButton.setText(optionText);
        optionRadioButton.setId(optionId); // Установить уникальный ID для варианта ответа

        // Добавить радиокнопку в группу радиокнопок
        radioGroup.addView(optionRadioButton);
    }

    private void checkAnswers() {
        Log.d("ReadingLesson", String.valueOf(readingLesson));

        if (readingLesson != null) {
            List<CheckAnswer> selectedOptions = new ArrayList<>(); // Создаем список для хранения выбранных ответов

            // Проходимся по всем вопросам из урока
            for (ReadingQuestion question : readingLesson.getQuestions()) {
                RadioGroup radioGroup = findRadioGroupForQuestion(question); // Находим группу радиокнопок для текущего вопроса
                if (radioGroup == null) {
                    Log.e("Error", "RadioGroup not found for question: " + question.getId());
                    Toast.makeText(requireContext(), "Failed to find radio group for question", Toast.LENGTH_SHORT).show();
                    return;
                }

                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId(); // Получаем ID выбранной радиокнопки
                if (checkedRadioButtonId == -1) {
                    // Если пользователь не выбрал ответ на текущий вопрос, выходим из метода
                    Toast.makeText(requireContext(), "Please select an answer for all questions", Toast.LENGTH_SHORT).show();
                    return;
                }

                selectedOptions.add(new CheckAnswer(checkedRadioButtonId));

                Log.d("Selected option", "Question ID: " + question.getId() + ", Selected Option ID: " + checkedRadioButtonId);
            }

            // Отправляем запрос на сервер для проверки ответов
            Call<Integer> apiCall = RetrofitClient.getInstance().getApi().checkReadingAnswers(readingLesson.getId(), selectedOptions);
            apiCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int correctAnswersCount = response.body();
                        // Отображение результата проверки ответов
                        Toast.makeText(requireContext(), "Correct answers: " + correctAnswersCount, Toast.LENGTH_SHORT).show();

                        // Вывод списка ID выбранных вариантов
                        System.out.println("Selected options:");
                        for (CheckAnswer answer : selectedOptions) {
                            System.out.println(answer.getSelectedOption());
                        }
                    } else {
                        Toast.makeText(requireContext(), "Failed to check answers", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(requireContext(), "Failed to check answers", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireContext(), "Failed to get Reading Lesson", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для нахождения группы радиокнопок для заданного вопроса
    private RadioGroup findRadioGroupForQuestion(ReadingQuestion question) {
        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            View childView = optionsContainer.getChildAt(i);
            if (childView instanceof RadioGroup) {
                RadioGroup questionRadioGroup = (RadioGroup) childView;
                if (questionRadioGroup.getTag() != null && questionRadioGroup.getTag().equals(String.valueOf(question.getId()))) {
                    return questionRadioGroup;
                }
            }
        }
        return null;
    }
}
