package com.example.dud_mobile.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.dud_mobile.R;
import com.example.dud_mobile.databinding.FragmentRegistrationBinding;
import com.example.dud_mobile.models.user.User;
import com.example.dud_mobile.remote_data.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {

    FragmentRegistrationBinding binding;
    User newUser;

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host);

        binding.btnRegistration.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            if (!IsEmptyRditTextRegistration()) {
                registerToTable();
            }
        });

        binding.tvLoginNow.setOnClickListener(v -> {
            navController.navigate(R.id.action_registrationFragment_to_loginFragment);
        });
    }


    private void registerToTable() {
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        int levelId = getSelectedLevelId();

        newUser = new User(email, password, levelId);

        Call<User> callCreateUser = RetrofitClient.getInstance().getApi().registrationNewUser(newUser);
        callCreateUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(requireActivity(), "Registration is Success!", Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefLoginEdit = preferences.edit();
                    prefLoginEdit.putString("user_email", email);
                    prefLoginEdit.apply();

                    navController.navigate(R.id.action_registrationFragment_to_navigation_home);
                } else {
                    Log.e("fail", "fail");
                    Toast.makeText(requireActivity(), "Registration is not available now", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                Log.e("FAILURE", "Registration is FAILURE", throwable);
                Toast.makeText(requireActivity(), "Registration is FAILURE", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean IsEmptyRditTextRegistration() {
        if (binding.email.getText().toString().isEmpty() ||
                binding.password.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Заполните поля для регистрации!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return true;
        } else {
            return false;
        }
    }

    private int getSelectedLevelId() {
        String selectedLevel = (String) binding.levelSpinner.getSelectedItem();
        switch (selectedLevel) {
            case "A1":
                return 1;
            case "A2":
                return 2;
            case "B1":
                return 3;
            case "B2":
                return 4;
            default:
                return 1; // default to A1
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}