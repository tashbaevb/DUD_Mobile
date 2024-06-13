package com.example.dud_mobile.ui.lessons.listening;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;

import com.example.dud_mobile.R;
import com.example.dud_mobile.constant.ConstantAPI;
import com.example.dud_mobile.models.lessons.CheckAnswer;
import com.example.dud_mobile.models.lessons.listening.ListeningLesson;
import com.example.dud_mobile.models.lessons.listening.ListeningQuestion;
import com.example.dud_mobile.remote_data.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeningFragment extends Fragment {

    private TextView titleTextView, descriptionTextView;
    private Button playButton;
    private Button pauseButton;
    private Button checkButton;
    private SeekBar seekBar;
    private int lessonId;
    private ListeningLesson listeningLesson;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    private LinearLayout optionsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listening, container, false);

        titleTextView = root.findViewById(R.id.titleTextView);
        descriptionTextView = root.findViewById(R.id.descriptionTextView);
        playButton = root.findViewById(R.id.playButton);
        pauseButton = root.findViewById(R.id.pauseButton);
        checkButton = root.findViewById(R.id.checkButton);
        seekBar = root.findViewById(R.id.seekBar);
        optionsContainer = root.findViewById(R.id.optionsContainer);

        lessonId = getArguments() != null ? getArguments().getInt("lessonId") : 1;
        loadListeningData(lessonId);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio(ConstantAPI.BASE_URL + listeningLesson.getMp3File());
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAudio();
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

    private void loadListeningData(int lessonId) {
        Call<ListeningLesson> apiCall = RetrofitClient.getInstance().getApi().getListeningByLesson(lessonId);
        apiCall.enqueue(new Callback<ListeningLesson>() {
            @Override
            public void onResponse(Call<ListeningLesson> call, Response<ListeningLesson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listeningLesson = response.body();
                    displayListeningLesson(listeningLesson);
                } else {
                    Toast.makeText(requireContext(), "Failed to load listening lesson", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListeningLesson> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to load listening lesson", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayListeningLesson(ListeningLesson listeningLesson) {
        titleTextView.setText(listeningLesson.getTitle());
        descriptionTextView.setText(listeningLesson.getDescription());

        optionsContainer.removeAllViews();

        // Создать радиокнопки для каждого вопроса и его вариантов
        for (ListeningQuestion question : listeningLesson.getQuestions()) {
            RadioGroup questionRadioGroup = new RadioGroup(requireContext());
            questionRadioGroup.setOrientation(LinearLayout.VERTICAL);
            questionRadioGroup.setTag(String.valueOf(question.getId()));

            TextView questionTextView = new TextView(requireContext());
            questionTextView.setText(question.getQuestion());
            questionRadioGroup.addView(questionTextView);

            addOptionRadioButtons(question, questionRadioGroup);

            optionsContainer.addView(questionRadioGroup);
        }
    }

    private void addOptionRadioButtons(ListeningQuestion question, RadioGroup questionRadioGroup) {
        addOptionRadioButton(questionRadioGroup, question.getOption1(), 1);
        addOptionRadioButton(questionRadioGroup, question.getOption2(), 2);
        addOptionRadioButton(questionRadioGroup, question.getOption3(),  3);
    }

    private void addOptionRadioButton(RadioGroup radioGroup, String optionText, int optionId) {
        RadioButton optionRadioButton = new RadioButton(requireContext());
        optionRadioButton.setText(optionText);
        optionRadioButton.setId(optionId);

        radioGroup.addView(optionRadioButton);
    }


    private void playAudio(String audioUrl) {
        releaseMediaPlayer();
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build());

            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync(); // Асинхронная подготовка проигрывателя
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    setupSeekBar();
                }
            });
        } catch (IOException e) {
            Log.e("MediaPlayer", "Error playing audio: " + e.getMessage());
            Toast.makeText(requireContext(), "Failed to play audio", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSeekBar() {
        // Устанавливаем максимальное значение SeekBar в соответствии с длительностью аудио
        seekBar.setMax(mediaPlayer.getDuration());

        // Обновление позиции SeekBar каждые 100 миллисекунд
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                }
                handler.postDelayed(this, 100);
            }
        });

        // Обработчик для перемотки аудио при перемещении ползунка SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Пустой метод
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Пустой метод
            }
        });
    }

    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void checkAnswers() {
        Log.d("ListeningLesson", String.valueOf(listeningLesson));

        if (listeningLesson != null) {
            // Создаем список для хранения выбранных ответов
            List<CheckAnswer> selectedOptions = new ArrayList<>();

            for (ListeningQuestion question : listeningLesson.getQuestions()) {
                // Находим группу радиокнопок для текущего вопроса
                RadioGroup radioGroup = findRadioGroupForQuestion(question);
                if (radioGroup == null) {
                    Log.e("Error", "RadioGroup not found for question: " + question.getId());
                    Toast.makeText(requireContext(), "Failed to find radio group for question", Toast.LENGTH_SHORT).show();
                    return;
                }

                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == -1) {
                    // Если пользователь не выбрал ответ на текущий вопрос, выходим из метода
                    Toast.makeText(requireContext(), "Please select an answer for all questions", Toast.LENGTH_SHORT).show();
                    return;
                }

                selectedOptions.add(new CheckAnswer(checkedRadioButtonId));

                Log.d("Selected option", "Question ID: " + question.getId() + ", Selected Option ID: " + checkedRadioButtonId);
            }

            Call<Integer> apiCall = RetrofitClient.getInstance().getApi().checkListeningAnswers(listeningLesson.getId(), selectedOptions);
            apiCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int correctAnswersCount = response.body();
                        Toast.makeText(requireContext(), "Correct answers: " + correctAnswersCount, Toast.LENGTH_SHORT).show();
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

    private RadioGroup findRadioGroupForQuestion(ListeningQuestion question) {
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

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
