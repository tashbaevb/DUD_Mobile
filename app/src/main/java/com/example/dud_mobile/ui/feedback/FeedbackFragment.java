package com.example.dud_mobile.ui.feedback;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dud_mobile.R;
import com.example.dud_mobile.models.Feedback;
import com.example.dud_mobile.remote_data.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackFragment extends Fragment {

    private EditText inputText;
    private Button sendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        inputText = root.findViewById(R.id.textInput);
        sendButton = root.findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            String feedbackText = inputText.getText().toString();
            sendToServer(feedbackText);
        });

        return root;
    }

    private void sendToServer(String feedbackText) {
        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", null);
        Log.d("API", "Access Token in feed: " + token);

        if (token != null) {
            Feedback feedback = new Feedback(feedbackText);

            Call<String> apiCall = RetrofitClient.getInstance().getApi().sendFeedback("Bearer " + token, feedback);
            apiCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireActivity(), "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireActivity(), "Failed to send feedback", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(requireActivity(), "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireActivity(), "Token not found. Please log in.", Toast.LENGTH_SHORT).show();
        }
    }
}
