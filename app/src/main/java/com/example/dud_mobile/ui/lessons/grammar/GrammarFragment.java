package com.example.dud_mobile.ui.lessons.grammar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.dud_mobile.R;
import com.example.dud_mobile.constant.ConstantAPI;
import com.example.dud_mobile.databinding.FragmentGrammarBinding;
import com.example.dud_mobile.models.lessons.GrammarLesson;
import com.example.dud_mobile.remote_data.RetrofitClient;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrammarFragment extends Fragment {

    private FragmentGrammarBinding binding;
    private TextView grammarTitle;
    private TextView grammarDescription;
    private ImageView grammarImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grammar, container, false);

        grammarTitle = root.findViewById(R.id.grammarTitle);
        grammarDescription = root.findViewById(R.id.grammarDescription);
        grammarImage = root.findViewById(R.id.grammarImage);

        int lessonId = getArguments() != null ? getArguments().getInt("lessonId") : 1;

        loadGrammarData(lessonId);

        Button nextButton = root.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_grammarFragment_to_readingFragment);
            }
        });

        return root;
    }


    private void loadGrammarData(int lessonId) {
        Call<GrammarLesson> apiCall = RetrofitClient.getInstance().getApi().getGrammarByLesson(lessonId);
        apiCall.enqueue(new Callback<GrammarLesson>() {
            @Override
            public void onResponse(Call<GrammarLesson> call, Response<GrammarLesson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GrammarLesson grammarLesson = response.body();

                    grammarTitle.setText(grammarLesson.getTitle());
                    grammarDescription.setText(grammarLesson.getDescription());

                    String imageURL = ConstantAPI.BASE_URL + grammarLesson.getGrammarImage();
                    Picasso.get().load(imageURL).into(grammarImage);
                } else {
                    Toast.makeText(requireActivity(), "Failed to load grammar lesson", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GrammarLesson> call, Throwable t) {
                Toast.makeText(requireActivity(), "Failed to load grammar lesson", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
